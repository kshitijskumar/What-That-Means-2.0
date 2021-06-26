package com.example.whatthatmeans20.data.repository

import com.example.whatthatmeans20.data.network.translateapi.api.TranslateApi
import com.example.whatthatmeans20.data.network.translateapi.model.TranslateTextModel
import com.example.whatthatmeans20.data.network.translateapi.response.TranslateResponse
import com.example.whatthatmeans20.utils.Injector
import com.example.whatthatmeans20.utils.Resources

class TranslateRepository(
    private val translateApi: TranslateApi = Injector.getInjector().providesTranslateApi()
) : BaseRepository() {

    suspend fun translateWord(word: String) : Resources<List<TranslateResponse>> {
        return safeApi {
            translateApi.translateWordToEnglish("en", listOf(TranslateTextModel(word)))
        }
    }
}