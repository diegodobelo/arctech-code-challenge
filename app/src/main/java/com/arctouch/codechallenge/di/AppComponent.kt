package com.arctouch.codechallenge.di

import android.content.Context
import com.arctouch.codechallenge.home.HomeFragment
import com.arctouch.codechallenge.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(searchFragment: SearchFragment)
}