package com.example.task.data.local

import android.content.Context
import androidx.room.*
import com.example.task.data.local.db.TaskDao
import com.example.task.data.local.db.UserDao
import com.example.task.model.Task
import com.example.task.model.User

@Database(entities = [User::class,Task::class],version = 1 ,)
abstract class TaskDatabase() : RoomDatabase() {
    abstract fun taskDao():TaskDao
    abstract fun userDao():UserDao

    companion object{
        @Volatile
        private var INSTANCE : RoomDatabase? = null

        fun getDatabase(context: Context) : RoomDatabase{
            return INSTANCE ?: synchronized(this){
                val db = Room.databaseBuilder(context
                    ,TaskDatabase::class.java
                    ,"Task-Database").build()
                INSTANCE = db
                return db as TaskDatabase
            }
        }
    }
}