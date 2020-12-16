package com.savenews.api

import com.savenews.AppModule
import com.savenews.repository.RetrofitRepository
import com.savenews.ui.RetroFragment
import com.savenews.viewmodel.RetroViewModel
import com.savenews.viewmodel.RetroViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,APIModule::class])
interface APIComponent {
    fun inject(retrofitRepository: RetrofitRepository)
    fun inject(retroViewModel: RetroViewModel)
    fun inject(retroFragment: RetroFragment)
    fun inject(retroViewModelFactory:RetroViewModelFactory)
}