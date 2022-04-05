package com.example.task.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task.R

const val EXTRAS_USERNAME = "com.example.task.USERNAME"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onBackPressed() {
        finish()
    }
}