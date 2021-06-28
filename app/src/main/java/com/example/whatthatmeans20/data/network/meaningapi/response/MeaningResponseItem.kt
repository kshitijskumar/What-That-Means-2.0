package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class MeaningResponseItem(
    @SerializedName("meanings")
    var meanings: List<Meaning> = listOf(),
    @SerializedName("phonetics")
    var phonetics: List<Phonetic> = listOf(),
    @SerializedName("word")
    var word: String? = null
)