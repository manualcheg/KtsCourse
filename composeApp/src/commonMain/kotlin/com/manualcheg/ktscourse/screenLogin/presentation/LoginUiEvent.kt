package com.manualcheg.ktscourse.screenLogin.presentation

sealed class LoginUiEvent {
    object LoginSuccessEvent : LoginUiEvent()
    object LoginErrorEvent : LoginUiEvent()
}