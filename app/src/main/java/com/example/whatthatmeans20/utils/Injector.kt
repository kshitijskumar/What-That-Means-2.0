package com.example.whatthatmeans20.utils

import com.example.whatthatmeans20.data.network.meaningapi.api.MeaningApi
import com.example.whatthatmeans20.data.network.translateapi.api.TranslateApi
import com.example.whatthatmeans20.data.repository.TranslateRepository
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptions


class Injector private constructor() {

    private var recognizer: TextRecognizer? = null
    private var translateApi: TranslateApi? = null
    private var meaningApi: MeaningApi? = null

    private var translateRepo: TranslateRepository? = null

    fun providesRecognizer() : TextRecognizer {
        if(recognizer == null) {
            recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        }

        return recognizer!!
    }

    fun providesTranslateApi() : TranslateApi {
        if(translateApi == null) {
            translateApi = TranslateApi.getTranslateApi()
        }

        return translateApi!!
    }

    fun providesTranslateRepository(): TranslateRepository {
        if(translateRepo == null) {
            translateRepo = TranslateRepository()
        }
        return translateRepo!!
    }

    fun providesMeaningApi(): MeaningApi {
        if(meaningApi == null) {
            meaningApi = MeaningApi.createMeaningApi()
        }
        return meaningApi!!
    }


    companion object {
        private var INSTANCE: Injector? = null

        fun getInjector(): Injector {
            if (INSTANCE == null) {
                INSTANCE = Injector()
            }

            return INSTANCE!!
        }
    }
}