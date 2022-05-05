package com.cookit

import com.cookit.service.IRecipeService
import com.cookit.service.KtorRecipeService
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    single<IRecipeService> { KtorRecipeService(androidApplication()) }
}