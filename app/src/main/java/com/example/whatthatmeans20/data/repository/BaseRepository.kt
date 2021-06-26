package com.example.whatthatmeans20.data.repository

import com.example.whatthatmeans20.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepository {

    suspend fun <T>safeApi(call: suspend () -> Response<T>) : Resources<T> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = call.invoke()
                when(resp.code()) {
                    200 -> {
                        if(resp.body() == null) Resources.Error("Call succesfull but empty response")
                        else Resources.Success(resp.body()!!)
                    }
                    else -> Resources.Error("Something unexpected went wrong")
                }
            }catch (e: Exception) {
                Resources.Error("Please check your internet connection!")
            }
        }
    }
}