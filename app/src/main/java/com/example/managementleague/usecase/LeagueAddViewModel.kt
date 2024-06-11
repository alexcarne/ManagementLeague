package com.example.managementleague.usecase

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.repository.LeagueRepository
import com.example.managementleague.model.repository.UserRepository
import com.example.managementleague.state.LeagueAddState
import com.example.managementleague.utils.AuthManager

class LeagueAddViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    private var state = MutableLiveData<LeagueAddState>()

    fun validate(numteams: Int, userid: Int,context:Context) {
        val user_id = UserRepository.getUserByEmail(AuthManager(context).getCurrentUser()!!.email!!).id
        when {
            TextUtils.isEmpty(name.value) -> state.value = LeagueAddState.NameEmptyError
            TextUtils.isEmpty(address.value) -> state.value = LeagueAddState.AddresEmptyError
            else -> {
                try {
                    LeagueRepository.insertLeague(
                        League(
                            LeagueRepository.currentid()+1, name.value!!, address.value!!, user_id, numteams, 0
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