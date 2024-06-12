package com.example.managementleague.state

sealed class TeamAddState {
    data object TeamNameEmptyError : TeamAddState()
    data object PlayerNameEmptyError : TeamAddState()
    data object PlayerNumberEmptyError : TeamAddState()
    data object PlayerNumberFormatError : TeamAddState()

    data object Success : TeamAddState()
    data object Error : TeamAddState()
}