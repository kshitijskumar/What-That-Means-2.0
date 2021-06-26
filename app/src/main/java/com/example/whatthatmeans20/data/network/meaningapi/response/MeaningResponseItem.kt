package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class MeaningResponseItem(
    @SerializedName("meanings")
    var meanings: List<Meaning>,
    @SerializedName("phonetics")
    var phonetics: List<Phonetic>,
    @SerializedName("word")
    var word: String
)