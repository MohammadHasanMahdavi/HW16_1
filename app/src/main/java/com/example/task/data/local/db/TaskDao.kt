package com.example.task.data.local.db

import androidx.room.*
import com.example.task.model.Task

@Dao
interface TaskDao{
    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * From Task")
    fun getTaskList() : List<Task>
}