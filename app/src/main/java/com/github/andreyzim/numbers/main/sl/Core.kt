package com.github.andreyzim.numbers.main.sl

import android.content.Context
import com.github.andreyzim.numbers.numbers.data.cache.CacheModule
import com.github.andreyzim.numbers.numbers.data.cache.NumbersDataBase
import com.github.andreyzim.numbers.numbers.data.cloud.CloudModule
import com.github.andreyzim.numbers.numbers.presentation.DispatchersList
import com.github.andreyzim.numbers.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val isRelease: Boolean
    ) : Core {

        private val manageResources: ManageResources = ManageResources.Base(context)

        private val dispatchersList by lazy {
            DispatchersList.Base()
        }

        private val cloudModule by lazy {
            if (isRelease)
                CloudModule.Release()
            else
                CloudModule.Debug()
        }

        private val cacheModule by lazy {
            if (isRelease)
                CacheModule.Base(context)
            else
                CacheModule.Mock(context)
        }

        override fun provideDispatchers(): DispatchersList = dispatchersList

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)

        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = manageResources.string(id)

    }
}