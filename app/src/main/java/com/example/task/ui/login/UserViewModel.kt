package com.example.task.ui.login

import androidx.lifecycle.ViewModel
import com.example.task.data.repository.UserRepository
import com.example.task.model.User
import kotlin.concurrent.thread

class UserViewModel(private var userRepository: UserRepository) : ViewModel() {
    fun insertUser(user: User){
        thread {
            userRepository.insetrUser(user)
        }
    }
    fun loginCheck(username:String,password:String) : Boolean {
        var check = false
        thread {
            check = userRepository.loginCheck(username, password)
        }.join(5000)
        return check
    }
    fun registerCheck(username:String) : Boolean {
        var check = false
        thread {
            check = userRepository.registerCheck(username)
        }.join(5000)
        return check
    }
}