package com.example.managementleague.model.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (league: League) : Long
    @Update
    fun update (league: League)
    @Delete
    fun delete(league: League)
    @Query("SELECT * FROM League")
    fun selectAll(): Flow<List<League>>
    @Query("SELECT * FROM League where id =:id")
    fun getLeaguefromId(id:Int): League

    @Query("SELECT * FROM League")
    fun selectAllRAW(): List<League>
    @Query("SELECT max(id) FROM League")
    fun initialiceCount():Int
}
