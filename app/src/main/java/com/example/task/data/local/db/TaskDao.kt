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


    @Query("SELECT * From Task WHERE UserOwnerName = :username")
    fun getTaskList(username:String?) : List<Task>

    @Query("SELECT * FROM task WHERE title lIKE :searchQuery OR description LIKE :searchQuery OR date LIKE :searchQuery OR time LIKE :searchQuery")
    fun searchDatabase(searchQuery : String?) : List<Task>

    @Query("DELETE FROM task WHERE userOwnerName = :username")
    fun deleteAll(username:String?)
}