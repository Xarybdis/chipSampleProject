package com.example.android_chipsampleapp

import android.app.Application
import com.example.android_chipsampleapp.di.networkModule
import com.example.android_chipsampleapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(networkModule, viewModelModule)
        }

            Timber.plant(Timber.DebugTree())

    }
}