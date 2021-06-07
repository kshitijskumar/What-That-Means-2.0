package com.example.whatthatmeans20.utils

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptions
import javax.inject.Inject


class Injector private constructor() {

    private var recognizer: TextRecognizer? = null

    fun providesRecognizer() : TextRecognizer {
        if(recognizer == null) {
            recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        }

        return recognizer!!
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