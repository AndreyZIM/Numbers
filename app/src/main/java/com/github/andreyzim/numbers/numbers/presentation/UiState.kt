package com.github.andreyzim.numbers.numbers.presentation

sealed class UiState {

    class Success : UiState() {}

    data class Error(private val message: String) : UiState() {}
}
