package com.example.task.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.dialog.EditTaskDialogFragment

class TaskRecyclerAdapter(var taskList : MutableList<Task>,val manager : FragmentManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ClickListener{
        fun onClickListener()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TaskViewHolder -> {
                val title = taskList[position].title
                val description = taskList[position].description
                val date = taskList[position].date
                val time = taskList[position].time
                val id = taskList[position].id
                holder.bind(
                    0, taskList[position].date, taskList[position].time, taskList[position].title,taskList[position].state,id
                )
                val lis = object : ClickListener {
                    override fun onClickListener() {
                        val dialog = EditTaskDialogFragment(title,description,date,time,id)
                        Log.d("TagDeb1",title)
                        Log.d("TagDeb1",description)
                        dialog.show(manager,"Edit")
                    }

                }
                holder.itemView.setOnClickListener{
                    lis.onClickListener()
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.icon_image_view)
        val dateTextView: TextView = itemView.findViewById(R.id.date_tv)
        val timeTextView: TextView = itemView.findViewById(R.id.time_tv)
        val titleTextView: TextView = itemView.findViewById(R.id.title_tv)
        var taskId : Int? = null

        fun bind(imageId:Int,date:String,time:String,title:String,state:State,id:Int){

            //imageView.setImageResource(imageId)
            dateTextView.text = date
            timeTextView.text = time
            titleTextView.text = title
            taskId = id
        }
    }
}