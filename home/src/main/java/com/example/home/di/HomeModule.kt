package com.example.home.di

import com.example.home.api.HomeApi
import com.example.home.repo.HomeRepo
import com.example.home.viewmodel.HomeViewModel
import network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val homeModule = module {
    single { RetrofitManager.getService(HomeApi::class.java) }
    single { HomeRepo(get()) }
    viewModel { HomeViewModel(get()) }
}