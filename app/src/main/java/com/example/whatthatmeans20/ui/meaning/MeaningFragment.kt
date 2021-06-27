package com.example.whatthatmeans20.ui.meaning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.whatthatmeans20.R
import com.example.whatthatmeans20.data.network.meaningapi.response.MeaningResponse
import com.example.whatthatmeans20.databinding.FragmentMeaningBinding
import com.example.whatthatmeans20.ui.words.WordsFragment.Companion.LANGUAGE_CODE
import com.example.whatthatmeans20.ui.words.WordsFragment.Companion.WORD
import com.example.whatthatmeans20.utils.Resources
import com.example.whatthatmeans20.viewmodel.TranslateViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MeaningFragment : Fragment() {

    private var _binding: FragmentMeaningBinding? = null
    private val binding: FragmentMeaningBinding get() = _binding!!

    private lateinit var meaningAdapter: MeaningViewpagerAdapter

    private val viewModel by navGraphViewModels<TranslateViewModel>(R.id.meaningDestination) {
        TranslateViewModel.provideTranslateViewModelFactory()
    }

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
//        setupViewPager(3)
        observeValues()
    }

    private fun searchMeaning() {
        val word = arguments?.getString(WORD) ?: "Hello"
        viewModel.getWordMeaning(word)
    }

    private fun setupViewPager(totalTabs: Int) {
        meaningAdapter = MeaningViewpagerAdapter(this, totalTabs)
        binding.viewPager.adapter = meaningAdapter
        val tabLayoutMediator = TabLayoutMediator(binding.meaningTabLayout, binding.viewPager) { tab, pos ->
            if(viewModel.meanings.value is Resources.Success) {
                tab.text = (viewModel.meanings.value as Resources.Success<MeaningResponse>).data[0].meanings[pos].partOfSpeech
            }
        }
        //we should only link these two if they are not already linked, so check before attaching
        if(!tabLayoutMediator.isAttached) {
            tabLayoutMediator.attach()
        }
    }

    private fun observeValues() {
        viewModel.meanings.observe(viewLifecycleOwner) {
            when(it) {
                is Resources.Success -> {
                    binding.tvWord.text = arguments?.getString(WORD)
                    binding.tvLanguageCode.text = arguments?.getString(LANGUAGE_CODE)
                    setupViewPager(it.data[0].meanings.size)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}