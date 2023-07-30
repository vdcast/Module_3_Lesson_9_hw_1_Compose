package com.example.module_3_lesson_9_hw_1_compose.data

import android.content.Context
import android.content.SharedPreferences

interface AppContainer {
    val prefs: SharedPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val prefs: SharedPreferencesRepository by lazy {
        SharedPreferencesImplementation(context)
    }
}