package com.example.managementleague.model.repository

import com.example.managementleague.model.Database.ManagementLeagueDatabase
import com.example.managementleague.model.entity.League
import com.example.managementleague.model.entity.Player
import kotlinx.coroutines.flow.Flow

class PlayerRepository {
    companion object {
        fun insertPlayer(player: Player) {
            try {
                ManagementLeagueDatabase.getInstance().playerDao().insert(player)
                Resources.Success(player)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }

        fun deletePlayer(player: Player) {
            try {
                ManagementLeagueDatabase.getInstance().playerDao().delete(player)
                Resources.Success(player)
            } catch (e: Exception) {
                Resources.Error(e)
            }

        }

        fun updatePlayer(player: Player) {
            try {
                ManagementLeagueDatabase.getInstance().playerDao().update(player)
                Resources.Success(player)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }
        fun getPlayerList(): Flow<List<Player>> {
            return ManagementLeagueDatabase.getInstance().playerDao().selectAll()
        }
        fun getPlayerListRAW(): List<Player> {
            return ManagementLeagueDatabase.getInstance().playerDao().selectAllRAW()
        }
        fun currentid():Int{
            return ManagementLeagueDatabase.getInstance().playerDao().initialiceCount()
        }


    }
}