package com.example.task.ui.home

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.task.R
import com.example.task.ui.home.dialog.SaveTaskDialogFragment
import com.example.task.ui.login.EXTRAS_USERNAME
import com.example.task.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity(){
    val factory = HomeViewModelFractory()
    var model : HomeViewModel?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val actionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Todo"
                1 -> tab.text = "Doing"
                2 -> tab.text = "Done"
            }

        }.attach()

        actionButton.setOnClickListener {
            val dialog = SaveTaskDialogFragment()
            dialog.show(supportFragmentManager, "Task")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                model!!.searchDatabase(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                model!!.searchDatabase(newText)
                return true
            }

        })
        val exit = menu?.findItem(R.id.exit)
        exit?.setOnMenuItemClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            true
        }
        val deleteAll = menu?.findItem(R.id.clear_tasks)
        deleteAll?.setOnMenuItemClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are You Sure?")
                .setNegativeButton("No"){_,_->

                }
                .setPositiveButton("Yes"){_,_->
                    model!!.deleteAll()
                }
                .show()

            true
        }
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        model = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
    }

    override fun onBackPressed() {

    }
}
