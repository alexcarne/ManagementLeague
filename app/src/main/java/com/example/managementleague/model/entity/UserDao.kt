package com.example.managementleague.model.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface UserDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (user: User) : Long
    @Update
    fun update (user: User)
    @Delete
    fun delete(user: User)
    @Query("SELECT * FROM user c where c.id = :id")
    fun selectUser(id: Int): User
    @Query("SELECT max(id) FROM user")
    fun initialiceCount():Int
}
