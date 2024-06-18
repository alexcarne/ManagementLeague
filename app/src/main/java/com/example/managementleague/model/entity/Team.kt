package com.example.managementleague.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "team", foreignKeys =
    [ForeignKey(
        entity = League::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_league"),
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Team(
    @PrimaryKey
    val id: Int,
    @NonNull
    val name: String,
    val id_league: Int,
    var macth_wins:Int,
    var macth_lost:Int,
) {
    fun calculateTotalMaches():Int {
       return macth_lost+macth_wins
    }
    fun addmacthwins(){
        macth_wins++
    }
    fun addmactlosr(){
        macth_lost++
    }

}
