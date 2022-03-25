package com.example.task.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task.data.ServiceLocator

class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java))
        {
            val repository = ServiceLocator.getUserRepository()
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }
}