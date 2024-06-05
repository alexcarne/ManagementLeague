package com.example.managementleague.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player",foreignKeys =
    [androidx.room.ForeignKey(
    entity = Team::class,
    parentColumns = kotlin.arrayOf("id"),
    childColumns = kotlin.arrayOf("id_team"),
    onDelete = androidx.room.ForeignKey.RESTRICT,
    onUpdate = androidx.room.ForeignKey.CASCADE
)])
data class Player(
    @PrimaryKey
    val id:Int,
    val id_team:Int,
    @NonNull
    val name:String,
    @NonNull
    val number:Int) {

}
