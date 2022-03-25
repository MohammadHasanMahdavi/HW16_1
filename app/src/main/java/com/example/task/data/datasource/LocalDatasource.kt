package com.example.task.data.datasource

import android.util.Log
import com.example.task.data.local.TaskDatabase
import com.example.task.model.Task
import com.example.task.model.User

class LocalDatasource (private var database:TaskDatabase) {
    private val taskDao = database.taskDao()
    private val userDao = database.userDao()
    fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    fun getTasks():List<Task>{
        return taskDao.getTaskList()
    }

    fun insertUser(user: User){
        userDao.insertUser(user)
    }
    fun loginCheck(username:String,password:String) : Boolean{
        return userDao.loginCheck(username,password)==1
    }

    fun registerCheck(username:String) : Boolean{
        return userDao.registerCheck(username)==0
    }
}