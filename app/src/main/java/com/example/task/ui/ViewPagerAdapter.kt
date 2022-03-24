package com.example.task.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task.ui.doing.DoingFragment
import com.example.task.ui.done.DoneFragment
import com.example.task.ui.todo.TodoFragment

class ViewPagerAdapter(manager:FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(manager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TodoFragment()
            1 -> DoingFragment()
            2 -> DoneFragment()
            else -> Fragment()
        }
    }
}