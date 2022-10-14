package com.github.andreyzim.numbers.numbers.domain

import com.github.andreyzim.numbers.numbers.presentation.NumberUi

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi = NumberUi(id, fact)
}