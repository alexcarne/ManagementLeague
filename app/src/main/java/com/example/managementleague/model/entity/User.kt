package com.example.managementleague.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id:Int,
    val name:String,
    @NonNull
    val email:String,
    @NonNull
    val password:String,
    @NonNull
    val phone:String) {
}