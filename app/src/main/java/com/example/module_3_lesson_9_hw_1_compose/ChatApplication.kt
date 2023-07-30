package com.example.module_3_lesson_9_hw_1_compose

import android.app.Application
import com.example.module_3_lesson_9_hw_1_compose.data.AppContainer
import com.example.module_3_lesson_9_hw_1_compose.data.AppDataContainer
import com.example.module_3_lesson_9_hw_1_compose.data.SharedPreferencesImplementation
import com.example.module_3_lesson_9_hw_1_compose.data.SharedPreferencesRepository

class ChatApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

