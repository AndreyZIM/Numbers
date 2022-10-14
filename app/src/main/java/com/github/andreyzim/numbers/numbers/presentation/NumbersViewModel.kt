package com.github.andreyzim.numbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val communications: NumbersCommunications,
    private val manageResources: ManageResources,
    private val interactor: NumbersInteractor
) : ViewModel(), ObserveNumbers, FetchNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            handleResult.handle(viewModelScope) {
                interactor.init()
            }
    }

    override fun fetchRandomNumbersFact() = handleResult.handle(viewModelScope) {
        interactor.factAboutRandomNumber()
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        else
            handleResult.handle(viewModelScope) {
                interactor.factAboutNumber(number)
            }

    }


}

interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumbersFact()

    fun fetchNumberFact(number: String)
}