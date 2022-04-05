package com.example.task.data

import com.example.task.data.datasource.LocalDatasource
import com.example.task.data.local.TaskDatabase
import com.example.task.data.repository.TaskRepository
import com.example.task.data.repository.UserRepository
import com.example.task.ui.GlobalApplication

class ServiceLocator {
    companion object{
        private val database  = TaskDatabase.getDatabase(GlobalApplication.getAppContext())
        private val localDatasource = LocalDatasource(database as TaskDatabase)
        private val taskRepository = TaskRepository(localDatasource)
        private val userRepository = UserRepository(localDatasource)
        fun getTaskRepository() : TaskRepository {
            return taskRepository
        }
        fun getUserRepository() : UserRepository{
            return userRepository
        }
    }
}