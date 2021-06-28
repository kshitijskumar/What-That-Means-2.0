package com.example.whatthatmeans20.data.network.meaningapi.response


import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("definition")
    var definition: String = "Sorry, we didn't find any relevant definition.",
    @SerializedName("example")
    var example: String = "No relevant example found",
    @SerializedName("synonyms")
    var synonyms: List<String> = listOf()
)