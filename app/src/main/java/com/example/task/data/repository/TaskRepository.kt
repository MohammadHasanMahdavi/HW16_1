package com.example.task.data.repository

import com.example.task.data.datasource.LocalDatasource
import com.example.task.model.Task

class TaskRepository (private var localDatasource: LocalDatasource){
    fun insertTask(task:Task){
        localDatasource.insertTask(task)
    }

    fun deleteTask(task: Task){
        localDatasource.deleteTask(task)
    }

    fun updateTask(task:Task){
        localDatasource.updateTask(task)
    }

    fun getTaskList() : List<Task>{
        return localDatasource.getTasks()
    }
}