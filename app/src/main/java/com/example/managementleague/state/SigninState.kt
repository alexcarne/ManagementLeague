package com.example.managementleague.state

import Resources
import com.example.managementleague.model.entity.User
import com.example.managementleague.utils.AuthRes

sealed class SigninState {

    data object  EmailEmptyError : SigninState()
    data object  PasswordEmptyError: SigninState()
    data class Success(var account: AuthRes<*>) : SigninState()
    data class Error(val errorMessage: String):SigninState()
    data class Loading(var value: Boolean) : SigninState()
}