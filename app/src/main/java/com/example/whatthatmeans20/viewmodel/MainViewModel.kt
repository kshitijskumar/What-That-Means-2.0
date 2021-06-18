package com.example.whatthatmeans20.viewmodel

import androidx.lifecycle.*
import com.example.whatthatmeans20.data.model.WordModel
import com.example.whatthatmeans20.utils.Resources
import com.example.whatthatmeans20.utils.UtilFunctions.handleScanResult
import com.google.mlkit.vision.text.Text

@Suppress("UNCHECKED_CAST")
class MainViewModel : ViewModel() {

    private val _wordsList = MutableLiveData<Resources<List<WordModel>>>()
    val wordsList: LiveData<Resources<List<WordModel>>> = _wordsList

    private val _words = MutableLiveData<List<WordModel>>()
    val words: LiveData<List<WordModel>> = _words

    fun getWordsListFromScannedText(text: Resources<Text>) {
        val wordsListResource = handleScanResult(text)
        _wordsList.postValue(wordsListResource)
        if (wordsListResource is Resources.Success) {
            _words.postValue(wordsListResource.data!!)
        }
    }


    companion object {
        private class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }

        fun getMainViewModel(owner: ViewModelStoreOwner): MainViewModel {
            return ViewModelProvider(owner, MainViewModelFactory())[MainViewModel::class.java]
        }
    }
}