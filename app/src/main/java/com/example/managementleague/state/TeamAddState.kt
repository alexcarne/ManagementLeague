package com.example.managementleague.state

sealed class TeamAddState {
    data object NameEmptyError : LeagueAddState()
    data object AddresEmptyError : LeagueAddState()

    data object Success : LeagueAddState()
    data object Error : LeagueAddState()
}