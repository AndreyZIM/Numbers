package com.github.andreyzim.numbers.numbers.data

interface NumbersCloudDataSource: FetchNumber {
    suspend fun randomNumber(): NumberData
}