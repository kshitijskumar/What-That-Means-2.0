package com.example.whatthatmeans20.data.network.translateapi.model

import com.google.gson.annotations.SerializedName

data class TranslateTextModel(
    @SerializedName("Text")
    val text: String
)
