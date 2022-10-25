package com.github.andreyzim.numbers.numbers.data.cache

import com.github.andreyzim.numbers.numbers.data.NumberData

class NumbersDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache = NumberCache(id, fact, System.currentTimeMillis())
}