package com.example.whatthatmeans20.ui.words

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.whatthatmeans20.R
import com.example.whatthatmeans20.databinding.FragmentWordsBinding
import com.example.whatthatmeans20.viewmodel.MainViewModel

class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    private val binding: FragmentWordsBinding get() = _binding!!

    private lateinit var wordAdapter: ScannedWordsAdapter

    private val viewModel by lazy {
        MainViewModel.getMainViewModel(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeValues()
    }

    private fun setupViews() {
        wordAdapter = ScannedWordsAdapter {
            Log.d("WordFragment", "Word clicked is: $it")
            findNavController().navigate(
                R.id.action_wordsFragment_to_meaningFragment,
                bundleOf(
                    WORD to it.word,
                    LANGUAGE_CODE to it.language
                )
            )
        }
        binding.rvWords.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            adapter = wordAdapter
        }
    }

    private fun observeValues() {
        viewModel.words.observe(viewLifecycleOwner) {
            wordAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val WORD = "word"
        const val LANGUAGE_CODE = "lang_code"
    }
}