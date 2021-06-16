package com.example.whatthatmeans20.viewmodel

import androidx.lifecycle.*
import com.example.whatthatmeans20.data.model.WordModel
import com.example.whatthatmeans20.utils.Resources

@Suppress("UNCHECKED_CAST")
class MainViewModel : ViewModel() {

    private val _wordsList = MutableLiveData<Resources<List<WordModel>>>()
    val wordsList: LiveData<Resources<List<WordModel>>> = _wordsList

    fun updateWordsList(words: Resources<List<WordModel>>) {
        _wordsList.postValue(words)
    }


    companion object {
        private class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }

        fun getMainViewModel(owner: ViewModelStoreOwner) : MainViewModel {
            return ViewModelProvider(owner, MainViewModelFactory())[MainViewModel::class.java]
        }
    }
}