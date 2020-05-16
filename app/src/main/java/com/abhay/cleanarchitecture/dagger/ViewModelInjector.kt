package com.abhay.cleanarchitecture.dagger

import com.abhay.cleanarchitecture.network.NetworkModule
import com.abhay.cleanarchitecture.ui.NewsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(newsListViewModel: NewsListViewModel)


    @Component.Builder
    interface Builder{
        fun build() : ViewModelInjector

        fun networkModule(networkModule : NetworkModule) : Builder

    }
}