package com.example.whatthatmeans20.data.network.translateapi.api

import com.example.whatthatmeans20.BuildConfig
import com.example.whatthatmeans20.data.network.translateapi.model.TranslateTextModel
import com.example.whatthatmeans20.data.network.translateapi.response.TranslateResponse
import com.example.whatthatmeans20.utils.Constants.API_VERSION
import com.example.whatthatmeans20.utils.Constants.TRANSLATE_API_BASE_URL
import com.example.whatthatmeans20.utils.Constants.TRANSLATE_CONTENT_TYPE_HEADER
import com.example.whatthatmeans20.utils.Constants.TRANSLATE_KEY_HEADER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslateApi {

    @Headers(
        TRANSLATE_CONTENT_TYPE_HEADER,
        TRANSLATE_KEY_HEADER,
        BuildConfig.RAPID_API_KEY
    )
    @POST("translate?api-version=$API_VERSION&textType=plain&profanityAction=NoAction")
    suspend fun translateWordToEnglish(
        @Query("to") to: String,
        @Body textBody: List<TranslateTextModel>
    ): Response<List<TranslateResponse>>

    companion object {

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(TRANSLATE_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getTranslateApi(): TranslateApi = retrofit.create(TranslateApi::class.java)
    }
}