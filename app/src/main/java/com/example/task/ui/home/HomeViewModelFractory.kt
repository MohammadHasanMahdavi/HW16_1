package com.example.task.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task.data.ServiceLocator
import java.lang.IllegalArgumentException

class HomeViewModelFractory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val repository = ServiceLocator.getTaskRepository()
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }
}