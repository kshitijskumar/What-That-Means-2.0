package com.example.whatthatmeans20.ui.meaning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.whatthatmeans20.R
import com.example.whatthatmeans20.data.network.meaningapi.response.Meaning
import com.example.whatthatmeans20.databinding.FragmentSingleMeaningBinding
import com.example.whatthatmeans20.utils.Resources
import com.example.whatthatmeans20.utils.UtilFunctions.conditionToResponse
import com.example.whatthatmeans20.utils.UtilFunctions.stringListToString
import com.example.whatthatmeans20.viewmodel.TranslateViewModel

class SingleMeaningFragment : Fragment() {

    private var _binding: FragmentSingleMeaningBinding? = null
    private val binding: FragmentSingleMeaningBinding get() = _binding!!

    private var meaningIndex = 0

    private val viewModel by navGraphViewModels<TranslateViewModel>(R.id.meaningDestination) {
        TranslateViewModel.provideTranslateViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleMeaningBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("SingleMeaningFrag", "item num is: ${arguments?.getInt(MEANING_INDEX)}")
        meaningIndex = arguments?.getInt(MEANING_INDEX) ?: 0
        observeValues()
    }

    private fun observeValues() {
        viewModel.meanings.observe(viewLifecycleOwner) {
            when (it) {
                is Resources.Success -> {
                    displayInformation(it.data[0].meanings[meaningIndex])
                }
            }
        }
    }

    private fun displayInformation(meaning: Meaning) {
        if (meaning.definitions.isEmpty()) return
        val definition = meaning.definitions[0]
        binding.tvDefinition.text = definition.definition
        binding.tvExample.text = definition.example
        binding.tvSynonyms.text = conditionToResponse(
            definition.synonyms.isEmpty(),
            "---No relevant synonyms found---",
            stringListToString(definition.synonyms)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MEANING_INDEX = "meaning_index"

        fun getFragmentInstance(index: Int): SingleMeaningFragment {
            val fragment = SingleMeaningFragment()
            fragment.arguments = bundleOf(
                MEANING_INDEX to index
            )
            return fragment
        }
    }
}