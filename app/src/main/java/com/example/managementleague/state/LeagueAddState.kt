package com.example.managementleague.state

import com.example.managementleague.utils.AuthRes

sealed class LeagueAddState {
    data object NameEmptyError : LeagueAddState()
    data object AddresEmptyError : LeagueAddState()
    data object Success : LeagueAddState()
    data object Error : LeagueAddState()

}