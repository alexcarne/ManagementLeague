package com.example.managementleague.state

sealed class SignUpState {
    data object  EmailEmptyError : SignUpState()
    data object  EmailFormatError: SignUpState()
    data object  PasswordEmptyError: SignUpState()
    data object  PasswordLengthError:SignUpState()
    data object  PasswordFormatError:SignUpState()
    data object  PhoneEmptyError:SignUpState()
    data object  PhoneFormatError:SignUpState()
    data object  Success: SignUpState()
    data object  Error: SignUpState()
    //data class Success<T>(val data: T): AuthRes<T>()
    //data class Error(val errorMessage: String): AuthRes<Nothing>()
}