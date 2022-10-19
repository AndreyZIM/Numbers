package com.github.andreyzim.numbers.numbers.data

import com.github.andreyzim.numbers.numbers.domain.HandleError
import com.github.andreyzim.numbers.numbers.domain.NoInternetConnectionException
import com.github.andreyzim.numbers.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError: HandleError<Exception> {

    override fun handle(e: Exception) = when (e) {
        is UnknownHostException -> NoInternetConnectionException()
        else -> ServiceUnavailableException()
    }
}