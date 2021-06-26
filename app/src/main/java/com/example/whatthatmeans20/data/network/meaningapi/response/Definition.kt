package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("definition")
    var definition: String,
    @SerializedName("example")
    var example: String,
    @SerializedName("synonyms")
    var synonyms: List<String>
)