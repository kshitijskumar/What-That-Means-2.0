package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class Phonetic(
    @SerializedName("audio")
    var audio: String? = null,
    @SerializedName("text")
    var text: String? = null
)