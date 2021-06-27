package com.example.whatthatmeans20.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.whatthatmeans20.data.network.meaningapi.response.MeaningResponse
import com.example.whatthatmeans20.data.repository.TranslateRepository
import com.example.whatthatmeans20.utils.Injector
import com.example.whatthatmeans20.utils.Resources
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val repo: TranslateRepository
) : ViewModel() {

    private val _meanings = MutableLiveData<Resources<MeaningResponse>>()
    val meanings: LiveData<Resources<MeaningResponse>> get() = _meanings

    fun getWordMeaning(word: String) = viewModelScope.launch {
        _meanings.postValue(Resources.Loading)
        val res = repo.getMeaning(word)
        Log.d("TranslateViewModel", "$res")
        _meanings.postValue(res)
    }


    class TranslateViewModelFactory: ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TranslateViewModel(Injector.getInjector().providesTranslateRepository()) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TranslateViewModel", "Clearing the viewmodel")
    }

    companion object {
        fun provideTranslateViewModelFactory() : TranslateViewModelFactory {
//            return ViewModelProvider(owner, TranslateViewModelFactory())[TranslateViewModel::class.java]
            return TranslateViewModelFactory()
        }
    }
}