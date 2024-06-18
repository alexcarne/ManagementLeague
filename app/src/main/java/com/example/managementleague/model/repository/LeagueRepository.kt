package com.example.managementleague.model.repository

import com.example.managementleague.model.Database.ManagementLeagueDatabase
import com.example.managementleague.model.entity.League
import kotlinx.coroutines.flow.Flow

class LeagueRepository {
    companion object {
        var league:League? = null
        fun insertLeague(league: League) {
            try {
                ManagementLeagueDatabase.getInstance().leagueDao().insert(league)
                Resources.Success(league)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }

        fun deleteLeague(league: League) {
            try {
                ManagementLeagueDatabase.getInstance().leagueDao().delete(league)
                Resources.Success(league)
            } catch (e: Exception) {
                Resources.Error(e)
            }

        }

        fun updateLeague(league: League) {
            try {
                ManagementLeagueDatabase.getInstance().leagueDao().update(league)
                Resources.Success(league)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }
        fun getLeagueList(): Flow<List<League>> {
            return ManagementLeagueDatabase.getInstance().leagueDao().selectAll()
        }
        fun getLeagueList(id:Int): League {
            return ManagementLeagueDatabase.getInstance().leagueDao().getLeaguefromId(id)
        }
        fun getLeagueListRAW(): List<League> {
            return ManagementLeagueDatabase.getInstance().leagueDao().selectAllRAW()
        }
        fun currentid():Int{
            return ManagementLeagueDatabase.getInstance().leagueDao().initialiceCount()
        }


    }
}