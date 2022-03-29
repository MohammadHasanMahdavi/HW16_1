package com.example.task.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.data.repository.TaskRepository
import com.example.task.model.Task
import kotlin.concurrent.thread

class HomeViewModel(private val repository: TaskRepository) : ViewModel(){

    val username : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var taskList = MutableLiveData<MutableList<Task>>()

    fun deleteTask(task: Task){
        thread { repository.deleteTask(task)}.join(5000)
        getTasks()
    }

    fun updateTask(task: Task){
        thread { repository.updateTask(task) }.join(5000)
        getTasks()
    }
    fun insertTask(task: Task){
        repository.insertTask(task)
        getTasks()
    }
    fun getTasks(){
        var databaseTaskList = mutableListOf<Task>()
         thread { databaseTaskList = repository.getTaskList(this.username.value) as MutableList<Task>
         Log.d("TAGG",databaseTaskList.toString())}.join(5000)
        taskList.postValue(databaseTaskList)
    }
    fun searchDatabase(searchQuery : String?){
        thread {
            taskList.value = repository.searchDatabase(searchQuery) as MutableList<Task>
        }
    }

    fun deleteAll() {
        thread {
            repository.deleteAll(username.value)
        }.join(5000)
        getTasks()
    }

}