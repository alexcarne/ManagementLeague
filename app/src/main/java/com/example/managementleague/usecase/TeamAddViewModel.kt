package com.example.managementleague.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementleague.model.entity.Player
import com.example.managementleague.model.entity.Team
import com.example.managementleague.model.repository.PlayerRepository
import com.example.managementleague.model.repository.TeamRepository
import com.example.managementleague.model.repository.UserRepository
import com.example.managementleague.state.LeagueAddState
import com.example.managementleague.state.TeamAddState


class TeamAddViewModel : ViewModel() {
    var team_name = MutableLiveData<String>()
    var player1_name = MutableLiveData<String>()
    var player2_name = MutableLiveData<String>()
    var player3_name = MutableLiveData<String>()
    var player4_name = MutableLiveData<String>()
    var player1_number = MutableLiveData<String>()
    var player2_number = MutableLiveData<String>()
    var player3_number = MutableLiveData<String>()
    var player4_number = MutableLiveData<String>()

    private var state = MutableLiveData<TeamAddState>()

    fun validate(leagueid: Int) {
        when {
            TextUtils.isEmpty(team_name.value) -> state.value = TeamAddState.TeamNameEmptyError
            TextUtils.isEmpty(player1_name.value) -> state.value = TeamAddState.PlayerNameEmptyError
            TextUtils.isEmpty(player2_name.value) -> state.value = TeamAddState.PlayerNameEmptyError
            TextUtils.isEmpty(player3_name.value) -> state.value = TeamAddState.PlayerNameEmptyError
            TextUtils.isEmpty(player1_number.value) -> state.value =
                TeamAddState.PlayerNumberEmptyError

            TextUtils.isEmpty(player2_number.value) -> state.value =
                TeamAddState.PlayerNumberEmptyError

            TextUtils.isEmpty(player3_number.value) -> state.value =
                TeamAddState.PlayerNumberEmptyError

            0 > player1_number.value!!.toInt() -> state.value = TeamAddState.PlayerNumberFormatError
            99 < player1_number.value!!.toInt() -> state.value =
                TeamAddState.PlayerNumberFormatError

            0 > player2_number.value!!.toInt() -> state.value = TeamAddState.PlayerNumberFormatError
            99 < player2_number.value!!.toInt() -> state.value =
                TeamAddState.PlayerNumberFormatError

            0 > player3_number.value!!.toInt() -> state.value = TeamAddState.PlayerNumberFormatError
            99 < player3_number.value!!.toInt() -> state.value =
                TeamAddState.PlayerNumberFormatError

            else -> {
                val team_id = TeamRepository.currentid() + 1
                try {
                    TeamRepository.insertTeam(
                        Team(
                            team_id,
                            team_name.value!!, leagueid, 0, 0, 0, 0, 0
                        )
                    )
                    PlayerRepository.insertPlayer(
                        Player(
                            PlayerRepository.currentid() + 1, team_id,
                            player1_name.value!!, player1_number.value!!.toInt()
                        )
                    )
                    PlayerRepository.insertPlayer(
                        Player(
                            PlayerRepository.currentid() + 1, team_id,
                            player2_name.value!!, player2_number.value!!.toInt()
                        )
                    )
                    PlayerRepository.insertPlayer(
                        Player(
                            PlayerRepository.currentid() + 1, team_id,
                            player3_name.value!!, player3_number.value!!.toInt()
                        )
                    )
                    if (!TextUtils.isEmpty(player4_name.value)&&!TextUtils.isEmpty(player4_number.value)){
                        PlayerRepository.insertPlayer(
                            Player(
                                PlayerRepository.currentid() + 1, team_id,
                                player4_name.value!!, player4_number.value!!.toInt()
                            )
                        )
                    }
                    state.value = TeamAddState.Success
                } catch (e: Exception) {
                    state.value = TeamAddState.Error
                }
            }
        }


    }

    fun getState(): LiveData<TeamAddState> {
        return state;
    }
}