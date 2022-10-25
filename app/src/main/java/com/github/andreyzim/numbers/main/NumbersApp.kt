package com.github.andreyzim.numbers.main

import android.app.Application
import com.github.andreyzim.numbers.BuildConfig
import com.github.andreyzim.numbers.numbers.data.CloudModule

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}