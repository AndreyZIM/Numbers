package com.github.andreyzim.numbers.numbers.presentation

import android.view.View
import com.github.andreyzim.numbers.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNumbersRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> NumbersResult
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communication: NumbersCommunications,
        private val numbersResultMapper: NumbersResult.Mapper<Unit>
    ) : HandleNumbersRequest {

        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend () -> NumbersResult
        ) {
            communication.showProgress(View.VISIBLE)
            coroutineScope.launch(dispatchers.io()) {
                val result = block.invoke()
                communication.showProgress(View.GONE)
                result.map(numbersResultMapper)
            }
        }
    }
}