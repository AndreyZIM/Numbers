package com.github.andreyzim.numbers.main.sl

import androidx.lifecycle.ViewModel

interface Module<T: ViewModel> {
    fun viewModel(): T
}