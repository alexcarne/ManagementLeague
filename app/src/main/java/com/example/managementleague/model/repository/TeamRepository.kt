package com.example.managementleague.model.repository

import com.example.managementleague.model.Database.ManagementLeagueDatabase
import com.example.managementleague.model.entity.Team
import kotlinx.coroutines.flow.Flow

class TeamRepository {
    companion object {
        fun insertTeam(team: Team) {
            try {
                ManagementLeagueDatabase.getInstance().teamDao().insert(team)
                Resources.Success(team)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }

        fun deleteTeam(team: Team) {
            try {
                ManagementLeagueDatabase.getInstance().teamDao().delete(team)
                Resources.Success(team)
            } catch (e: Exception) {
                Resources.Error(e)
            }

        }

        fun updateTeam(team: Team) {
            try {
                ManagementLeagueDatabase.getInstance().teamDao().update(team)
                Resources.Success(team)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }
        fun getTeamList(id:Int): Flow<List<Team>> {
            return ManagementLeagueDatabase.getInstance().teamDao().selectAll(id)
        }
        fun getTeamListRAW(): List<Team> {
            return ManagementLeagueDatabase.getInstance().teamDao().selectAllRAW()
        }
        fun currentid():Int{
            return ManagementLeagueDatabase.getInstance().teamDao().initialiceCount()
        }

    }
}