package com.example.whatthatmeans20.utils

sealed class Resources<out T> {

    data class Success<T>(val data: T) : Resources<T>()
    object Loading : Resources<Nothing>()
    data class Error(val errorMsg: String) : Resources<Nothing>()
}