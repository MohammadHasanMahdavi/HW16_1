package com.example.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey @ColumnInfo(name = "Username") val username : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "name") val name : String
)
