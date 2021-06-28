package com.example.whatthatmeans20.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.whatthatmeans20.data.model.WordModel
import com.google.mlkit.vision.text.Text
import java.util.concurrent.locks.Condition


object UtilFunctions {

    private fun mlTextToWordsList(text: Text): List<WordModel> {
        val wordsList = mutableListOf<WordModel>()
        for (block in text.textBlocks) {
            for (paragraph in block.lines) {
                for (word in paragraph.elements) {
                    Log.d("AnalysedWords", "Words are: ${word.text} and ${word.recognizedLanguage}")
                    wordsList.add(
                        WordModel(
                            word.text,
                            word.recognizedLanguage
                        )
                    )
                }
            }
        }

        return wordsList
    }

    fun handleScanResult(result: Resources<Text>): Resources<List<WordModel>> {
        Log.d("ScanFragmentResult", "The value is :$result")
        return when (result) {
            is Resources.Success -> {
                val wordsList = mlTextToWordsList(result.data)
                Resources.Success(wordsList)
            }
            is Resources.Error -> result
            is Resources.Loading -> result
        }
    }

    fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, msg, duration).show()
    }

    fun wordsListToString(wordsList: List<WordModel>): String {
        val result = StringBuilder()
        wordsList.forEach {
            val word = it.word
            result.append("$word, ")
        }

        return result.toString()
    }

    fun stringListToString(words: List<String>) : String {
        val result = StringBuilder()
        words.forEach {
            result.append("$it, ")
        }
        return if(result.isNotEmpty()) {
            result.substring(0, result.length-2)
        }else {
            result.toString()
        }
    }

    fun conditionToResponse(condition: Boolean, trueCase: String, falseCase: String) : String {
        return if (condition) {
            trueCase
        }else {
            falseCase
        }
    }
}