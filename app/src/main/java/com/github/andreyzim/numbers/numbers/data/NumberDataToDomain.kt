package com.github.andreyzim.numbers.numbers.data

import com.github.andreyzim.numbers.numbers.domain.NumberFact

class NumberDataToDomain : NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String) = NumberFact(id, fact)
}