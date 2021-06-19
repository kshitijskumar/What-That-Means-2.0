package com.example.whatthatmeans20.utils

import com.example.whatthatmeans20.data.network.translateapi.api.TranslateApi
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptions


class Injector private constructor() {

    private var recognizer: TextRecognizer? = null
    private var translateApi: TranslateApi? = null

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