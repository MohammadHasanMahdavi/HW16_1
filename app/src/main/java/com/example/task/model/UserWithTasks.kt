package com.example.task.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTasks(
    @Embedded val user : User,
    @Relation(
        parentColumn = "Id",
        entityColumn = "UserOwnerId"
    )
    val tasks : List<Task>
)