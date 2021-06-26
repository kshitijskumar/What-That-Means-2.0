package com.example.whatthatmeans20.data.repository

import com.example.whatthatmeans20.data.network.meaningapi.api.MeaningApi
import com.example.whatthatmeans20.data.network.meaningapi.response.MeaningResponse
import com.example.whatthatmeans20.data.network.translateapi.api.TranslateApi
import com.example.whatthatmeans20.data.network.translateapi.model.TranslateTextModel
import com.example.whatthatmeans20.data.network.translateapi.response.TranslateResponse
import com.example.whatthatmeans20.utils.Injector
import com.example.whatthatmeans20.utils.Resources

class TranslateRepository(
    private val translateApi: TranslateApi = Injector.getInjector().providesTranslateApi(),
    private val meaningApi: MeaningApi = Injector.getInjector().providesMeaningApi()
) : BaseRepository() {

    suspend fun translateWord(word: String) : Resources<List<TranslateResponse>> {
        return safeApi {
            translateApi.translateWordToEnglish("en", listOf(TranslateTextModel(word)))
        }
    }

    suspend fun getMeaning(word: String) : Resources<MeaningResponse> {
        return when(val translation = translateWord(word)) {
            is Resources.Error -> Resources.Error(translation.errorMsg)
            is Resources.Success -> {
                if(translation.data.isEmpty() || translation.data[0].translations.isEmpty()) {
                    Resources.Error("No such word found!")
                }else {
                    val translatedWord = translation.data[0].translations[0].text ?: "Hello"
                    safeApi { meaningApi.getMeaning(translatedWord) }
                }
            }
            else -> Resources.Error("Something went wrong")
        }
    }
}