package com.example.managementleague.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.state.LeagueAddState

class LeagueAddViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    private var state = MutableLiveData<LeagueAddState>()
    fun validate(numteams: Int, userid: Int) {
        when {
            TextUtils.isEmpty(name.value) -> state.value = LeagueAddState.NameEmptyError
            TextUtils.isEmpty(address.value) -> state.value = LeagueAddState.AddresEmptyError
            else -> {
                try {
                    LeagueRepository.insertLeague(
                        League(
                            LeagueRepository.currentid()+1, name.value!!, address.value!!, userid, numteams, 0
                        )
                    )
                    state.value = LeagueAddState.Success
                } catch (e: Exception) {
                    state.value = LeagueAddState.Error
                }
            }

        }
    }

    fun getState(): LiveData<LeagueAddState> {
        return state;
    }
}