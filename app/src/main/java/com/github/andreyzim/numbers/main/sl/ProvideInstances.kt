package com.github.andreyzim.numbers.main.sl

import android.content.Context
import com.github.andreyzim.numbers.numbers.data.cache.CacheModule
import com.github.andreyzim.numbers.numbers.data.cloud.CloudModule

interface ProvideInstances {

    fun provideCloudModule() : CloudModule
    fun provideCacheModule() : CacheModule

    class Release(private val context: Context) : ProvideInstances {
        override fun provideCloudModule(): CloudModule = CloudModule.Base()
        override fun provideCacheModule(): CacheModule = CacheModule.Base(context)
    }

    class Mock(private val context: Context) : ProvideInstances {
        override fun provideCloudModule(): CloudModule = CloudModule.Mock()
        override fun provideCacheModule(): CacheModule = CacheModule.Mock(context)
    }
}