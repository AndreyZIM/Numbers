package com.github.andreyzim.numbers.numbers.sl

import com.github.andreyzim.numbers.main.sl.Core
import com.github.andreyzim.numbers.main.sl.Module
import com.github.andreyzim.numbers.numbers.data.BaseNumbersRepository
import com.github.andreyzim.numbers.numbers.data.HandleDataRequest
import com.github.andreyzim.numbers.numbers.data.HandleDomainError
import com.github.andreyzim.numbers.numbers.data.NumberDataToDomain
import com.github.andreyzim.numbers.numbers.data.cache.NumbersCacheDataSource
import com.github.andreyzim.numbers.numbers.data.cache.NumbersDataToCache
import com.github.andreyzim.numbers.numbers.data.cloud.NumbersCloudDataSource
import com.github.andreyzim.numbers.numbers.data.cloud.NumbersService
import com.github.andreyzim.numbers.numbers.domain.HandleError
import com.github.andreyzim.numbers.numbers.domain.HandleRequest
import com.github.andreyzim.numbers.numbers.domain.NumberUiMapper
import com.github.andreyzim.numbers.numbers.domain.NumbersInteractor
import com.github.andreyzim.numbers.numbers.presentation.*

class NumbersModule(private val core: Core) : Module<NumbersViewModel> {

    override fun viewModel(): NumbersViewModel {
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunication.Base(),
            NumbersListCommunication.Base()
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            core.provideDataBase().numbersDao(),
            NumbersDataToCache()
        )
        val repository = BaseNumbersRepository(
            NumbersCloudDataSource.Base(
                core.service(NumbersService::class.java)
            ),
            cacheDataSource,
            HandleDataRequest.Base(
                cacheDataSource,
                NumberDataToDomain(),
                HandleDomainError()
            ),
            NumberDataToDomain()
        )
        return NumbersViewModel(
            HandleNumbersRequest.Base(
                core.provideDispatchers(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper())
            ),
            communications,
            core,
            NumbersInteractor.Base(
                repository,
                HandleRequest.Base(
                    HandleError.Base(core),
                    repository
                )
            )
        )
    }
}