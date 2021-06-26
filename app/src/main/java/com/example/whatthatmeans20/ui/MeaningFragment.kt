package com.example.whatthatmeans20.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatthatmeans20.databinding.FragmentMeaningBinding
import com.example.whatthatmeans20.ui.words.WordsFragment.Companion.WORD
import com.example.whatthatmeans20.viewmodel.TranslateViewModel

class MeaningFragment : Fragment() {

    private var _binding: FragmentMeaningBinding? = null
    private val binding: FragmentMeaningBinding get() = _binding!!

    private val viewModel by lazy {
        TranslateViewModel.provideTranslateViewModel(this)
    }

    private var textToSearch: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeaningBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMeaning()
    }

    private fun searchMeaning() {
        val word = arguments?.getString(WORD) ?: "Hello"
        viewModel.getWordMeaning(word)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}