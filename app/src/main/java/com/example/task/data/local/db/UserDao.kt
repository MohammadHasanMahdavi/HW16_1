package com.example.task.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.task.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)


    @Query("SELECT COUNT(*) FROM user WHERE username = :username AND password = :password")
    fun loginCheck(username:String,password:String) : Int

    @Query("SELECT COUNT(*) FROM user WHERE username = :username")
    fun registerCheck(username:String) : Int
}