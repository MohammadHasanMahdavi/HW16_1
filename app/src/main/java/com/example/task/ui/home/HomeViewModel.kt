package com.example.task.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.data.repository.TaskRepository
import com.example.task.model.Task
import kotlin.concurrent.thread

class HomeViewModel(private val repository: TaskRepository) : ViewModel(){

    val taskList : MutableLiveData<List<Task>> by lazy {
        MutableLiveData<List<Task>>()
    }

    fun insertTask(task: Task){
        repository.insertTask(task)
        getTasks()
    }
    fun getTasks(){
        var databaseTaskList = listOf<Task>()
         thread { databaseTaskList = repository.getTaskList()
         Log.d("TAGG",databaseTaskList.toString())}.join(5000)
        taskList.postValue(databaseTaskList)
    }

}