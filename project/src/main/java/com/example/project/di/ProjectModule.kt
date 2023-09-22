package com.example.project.di

import com.example.project.api.ProjectApi
import com.example.project.repo.ProjectRepo
import com.example.project.viewmodel.ProjectViewModel
import network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ProjectModule = module {
    single { RetrofitManager.getService(ProjectApi::class.java) }
    single { ProjectRepo(get()) }
    viewModel { ProjectViewModel(get()) }
}