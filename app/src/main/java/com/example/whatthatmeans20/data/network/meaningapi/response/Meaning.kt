package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class Meaning(
    @SerializedName("definitions")
    var definitions: List<Definition> = listOf(),
    @SerializedName("partOfSpeech")
    var partOfSpeech: String? = null
)