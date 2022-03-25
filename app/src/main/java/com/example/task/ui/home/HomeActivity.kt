package com.example.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.task.R
import com.example.task.model.Task
import com.example.task.ui.home.dialog.SaveTaskDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    //val factory = ViewModelFractory()
    //val model = ViewModelProvider(this,factory).get(MyViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val actionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,lifecycle)

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->tab.text = "Todo"
                1->tab.text = "Doing"
                2->tab.text = "Done"
            }

        }.attach()

        actionButton.setOnClickListener {
            val dialog = SaveTaskDialogFragment()
            dialog.show(supportFragmentManager,"Task")
        }
        Toast.makeText(this, "main activity", Toast.LENGTH_SHORT).show()
    }



}