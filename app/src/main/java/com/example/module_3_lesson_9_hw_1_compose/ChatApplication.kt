package com.example.module_3_lesson_9_hw_1_compose

import android.app.Application
import com.example.module_3_lesson_9_hw_1_compose.data.SharedPreferencesImplementation
import com.example.module_3_lesson_9_hw_1_compose.data.SharedPreferencesRepository

class ChatApplication : Application() {
    lateinit var prefs: SharedPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferencesImplementation(this)
    }
}

