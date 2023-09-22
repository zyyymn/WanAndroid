package com.example.project.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProVPAdapter(
    context: Fragment,
    private val f : MutableList<Fragment>
) : FragmentStateAdapter(context){
    override fun getItemCount(): Int= f.size

    override fun createFragment(position: Int): Fragment {
        return f[position]
    }
}