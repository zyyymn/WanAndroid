package com.example.home.ui

import base.BaseFragment
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<FragmentHomeBinding>(){

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }
}