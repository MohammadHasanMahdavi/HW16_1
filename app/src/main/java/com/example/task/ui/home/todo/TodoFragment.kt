package com.example.task.ui.home.todo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.TaskRecyclerAdapter
import com.example.task.ui.home.HomeViewModelFractory
import com.example.task.ui.login.EXTRAS_USERNAME
import kotlin.concurrent.thread


class TodoFragment : Fragment(R.layout.fragment_todo) ,SearchView.OnQueryTextListener{
    val factory = HomeViewModelFractory()
    var model : HomeViewModel? = null
    val taskList = mutableListOf<Task>()
    lateinit var recyclerAdapter : TaskRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = TaskRecyclerAdapter(taskList,requireActivity().supportFragmentManager)
        val todoRecyclerView : RecyclerView= view.findViewById<RecyclerView>(R.id.todo_recycler)
        todoRecyclerView.adapter = recyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


            model!!.taskList.observe(viewLifecycleOwner){
                val todoList = it.filter { it.state == State.TODO }
                taskList.clear()
                taskList.addAll(todoList)
                val emptyTextView : TextView = view.findViewById(R.id.empty_todo_tv)
                if (todoList.isEmpty()){
                    emptyTextView.visibility = View.VISIBLE
                    todoRecyclerView.visibility = View.GONE
                }
                else{
                    emptyTextView.visibility = View.GONE
                    todoRecyclerView.visibility = View.VISIBLE
                    recyclerAdapter.notifyDataSetChanged()
                }
            }

        thread {
            model!!.getTasks()
        }
        Toast.makeText(requireContext(), model!!.username.value, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
        model!!.username.value = requireActivity().intent.getStringExtra(EXTRAS_USERNAME)
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    fun searchDatabase(query:String){
        val searchQuery = "$query"

        model!!.searchDatabaseResult.observe(viewLifecycleOwner){list ->
            list.let {
                recyclerAdapter.taskList = it
            }
        }
    }
}


