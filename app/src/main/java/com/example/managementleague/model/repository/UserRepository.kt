package com.example.managementleague.model.repository

import com.example.managementleague.model.Database.ManagementLeagueDatabase
import com.example.managementleague.model.entity.User

class UserRepository {
    companion object {
        fun insertUser(user: User) {
            try {
                ManagementLeagueDatabase.getInstance().userDao().insert(user)
                Resources.Success(user)
            } catch (e: Exception) {
                Resources.Error(e)
            }
        }
        fun deleteUser(user: User){
            ManagementLeagueDatabase.getInstance().userDao().delete(user)
        }
        fun updateUser(user: User){
            ManagementLeagueDatabase.getInstance().userDao().update(user)
        }
        fun getUser(id:Int):User{
            return ManagementLeagueDatabase.getInstance().userDao().selectUser(id)
        }
        fun currentid():Int{
            return ManagementLeagueDatabase.getInstance().userDao().initialiceCount()
        }
    }
}