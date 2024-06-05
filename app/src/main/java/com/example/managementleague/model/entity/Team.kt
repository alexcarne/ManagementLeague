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
    val macth_wins:Int,
    val macth_lost:Int,
    val pts_score:Int,
    val pts_conceaded:Int,
    val pts_league:Int
) {

}
