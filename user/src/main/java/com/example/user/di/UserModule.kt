package com.example.user.di

import com.example.user.api.UserApi
import com.example.user.repo.LoginRepo
import com.example.user.viewmodel.LoginViewModel
import network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val userModule = module{

    single { RetrofitManager.getService(UserApi::class.java) }
    single {LoginRepo(get())}
    viewModel {LoginViewModel(get())}

}