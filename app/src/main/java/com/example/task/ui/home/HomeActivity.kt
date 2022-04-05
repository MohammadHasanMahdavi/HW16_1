package com.example.task.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.home.dialog.SaveTaskDialogFragment
import com.example.task.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    val factory = HomeViewModelFractory()
    var viewModel: HomeViewModel? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager
        val actionButton = binding.floatingActionButton

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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel!!.searchDatabase(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel!!.searchDatabase(newText)
                return true
            }

        })
        val exit = menu.findItem(R.id.exit)

        exit?.setOnMenuItemClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            true
        }

        val deleteAll = menu.findItem(R.id.clear_tasks)

        deleteAll?.setOnMenuItemClickListener {

            AlertDialog.Builder(this)
                .setMessage("Are You Sure?")
                .setNegativeButton("No") { _, _ -> }
                .setPositiveButton("Yes") { _, _ ->
                    viewModel!!.deleteAll()
                }.show()

            true
        }
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onBackPressed() {
        finish()
    }
}
