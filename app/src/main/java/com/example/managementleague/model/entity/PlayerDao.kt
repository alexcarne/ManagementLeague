package com.example.managementleague.model.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = ForeignKey.RESTRICT)
    fun insert (player: Player) : Long
    @Update
    fun update (player: Player)
    @Delete
    fun delete(player: Player)
    @Query("SELECT * FROM player")
    fun selectAll(): Flow<List<Player>>
    @Query("SELECT * FROM player")
    fun selectAllRAW(): List<Player>
    @Query("SELECT max(id) FROM player")
    fun initialiceCount():Int

}
