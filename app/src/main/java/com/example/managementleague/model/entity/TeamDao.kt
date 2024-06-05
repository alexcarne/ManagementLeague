package com.example.managementleague.model.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (team: Team) : Long
    @Update
    fun update (team: Team)
    @Delete
    fun delete(team: Team)
    @Query("SELECT * FROM team where id_league = :id")
    fun selectAll(id:Int): Flow<List<Team>>
    @Query("SELECT * FROM team")
    fun selectAllRAW(): List<Team>
    @Query("SELECT max(id) FROM team")
    fun initialiceCount():Int
}
