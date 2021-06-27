package com.example.whatthatmeans20.ui.meaning

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MeaningViewpagerAdapter(fragment: Fragment, private val totalTabs: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = totalTabs

    override fun createFragment(position: Int): Fragment {
        return SingleMeaningFragment.getFragmentInstance(position)
    }
}