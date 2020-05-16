package com.abhay.cleanarchitecture.ui

import androidx.lifecycle.ViewModel
import com.abhay.cleanarchitecture.dagger.DaggerViewModelInjector
import com.abhay.cleanarchitecture.dagger.ViewModelInjector
import com.abhay.cleanarchitecture.network.NetworkModule

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()


        init{
            inject()
        }

    private fun inject() {
        when(this){
            is NewsListViewModel -> injector.inject(this)
        }
    }
}