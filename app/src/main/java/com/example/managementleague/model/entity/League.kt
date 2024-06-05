package com.example.managementleague.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.managementleague.model.repository.UserRepository
import java.io.Serializable

@Entity(tableName = "League",foreignKeys =
[ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("user_id"),
    onDelete = ForeignKey.RESTRICT,
    onUpdate = ForeignKey.CASCADE
)])
data class League(
    @PrimaryKey
    val id:Int,
    @NonNull
    val name : String,
    val address:String,
    val user_id:Int,
    @NonNull
    val maxteam:Int,
    val currentteams:Int

) : Serializable {
    fun getTeamnumber(): String {
        return "$currentteams / $maxteam"
    }

    fun getUser(): String {
        return UserRepository.getUser(user_id).name
    }
}