package com.example.whatthatmeans20.data.network.translateapi.response

data class SingleTranslationResponse(
    val translations: List<SingleTranslationText> = listOf()
)

data class SingleTranslationText(
    val text: String? = null,
    val to: String? = null
)
