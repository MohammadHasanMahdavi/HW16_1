package com.example.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [])
data class Task (
    @PrimaryKey @ColumnInfo(name = "Id") val id : Int,
    @ColumnInfo(name = "Title") val title:String,
    @ColumnInfo(name = "Description") val description : String,
    @ColumnInfo(name = "Date") val date : Date,
    @ColumnInfo(name = "State") val state : State
    )