package com.example.task.data.repository

import com.example.task.data.datasource.LocalDatasource
import com.example.task.model.User

class UserRepository(private var localDatasource: LocalDatasource) {

    fun insetrUser(user: User){
        localDatasource.insertUser(user)
    }
    fun loginCheck(username:String,password:String) : Boolean {
        return localDatasource.loginCheck(username,password)
    }
    fun registerCheck(username:String) : Boolean{
        return localDatasource.registerCheck(username)
    }

}