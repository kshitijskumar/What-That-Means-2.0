package com.example.whatthatmeans20.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.whatthatmeans20.data.repository.TranslateRepository
import com.example.whatthatmeans20.utils.Injector
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val repo: TranslateRepository
) : ViewModel() {


    fun getWordMeaning(word: String) = viewModelScope.launch {
        val res = repo.translateWord(word)
        Log.d("TranslateViewModel", "$res")
    }


    private class TranslateViewModelFactory: ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TranslateViewModel(Injector.getInjector().providesTranslateRepository()) as T
        }
    }

    companion object {
        fun provideTranslateViewModel(owner: ViewModelStoreOwner) : TranslateViewModel {
            return ViewModelProvider(owner, TranslateViewModelFactory())[TranslateViewModel::class.java]
        }
    }
}