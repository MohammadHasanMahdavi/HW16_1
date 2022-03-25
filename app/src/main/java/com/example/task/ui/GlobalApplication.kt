package com.example.task.ui

import android.app.Application
import android.content.Context

class GlobalApplication : Application() {

    companion object{
        private var appContext : Context? = null
        fun getAppContext() : Context{
            return appContext!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}