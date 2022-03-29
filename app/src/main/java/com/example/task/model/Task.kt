package com.example.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Task")
data class Task (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id : Int,
    @ColumnInfo(name = "UserOwnerName") val userOwnerName : String?,
    @ColumnInfo(name = "Title") val title:String,
    @ColumnInfo(name = "Description") val description : String,
    @ColumnInfo(name = "Date") val date : String,
    @ColumnInfo(name = "Time") val time : String,
    @ColumnInfo(name = "State") val state : State,
    @ColumnInfo(name = "imageLocation") val location : String
    )