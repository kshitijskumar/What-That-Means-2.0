package com.example.whatthatmeans20.ui.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.whatthatmeans20.data.model.WordModel
import com.example.whatthatmeans20.databinding.HolderWordBinding


class ScannedWordsAdapter(
    private val wordClicked: (word: WordModel) -> Unit
) : ListAdapter<WordModel, ScannedWordsAdapter.WordsViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WordModel>() {
            override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderWordBinding.inflate(inflater)
        return WordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        val word = getItem(position)
        holder.setupWord(word, wordClicked)
    }

    class WordsViewHolder(private val binding: HolderWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setupWord(word: WordModel, wordClicked: (word: WordModel) -> Unit) {
            binding.tvWord.text = word.word
            binding.tvLanguage.text = word.language
            binding.root.setOnClickListener {
                wordClicked.invoke(word)
            }
        }
    }
}