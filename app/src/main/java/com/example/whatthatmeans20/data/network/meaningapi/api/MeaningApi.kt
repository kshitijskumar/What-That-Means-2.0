package com.example.whatthatmeans20.data.network.meaningapi.api

import com.example.whatthatmeans20.data.network.meaningapi.response.MeaningResponse
import com.example.whatthatmeans20.utils.Constants.MEANING_API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MeaningApi {

    @GET("{word}")
    suspend fun getMeaning(
        @Path("word") word: String
    ) : Response<MeaningResponse>


    companion object {
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(MEANING_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun createMeaningApi(): MeaningApi = retrofit.create(MeaningApi::class.java)
    }
}