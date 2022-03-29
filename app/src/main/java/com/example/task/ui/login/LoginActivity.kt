package com.example.task.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.R

const val EXTRAS_USERNAME = "com.example.task.USERNAME"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}