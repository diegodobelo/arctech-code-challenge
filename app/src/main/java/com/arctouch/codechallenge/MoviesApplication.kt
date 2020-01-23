package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.AppComponent
import com.arctouch.codechallenge.di.DaggerAppComponent

class MoviesApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}