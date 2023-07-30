package com.example.module_3_lesson_9_hw_1_compose.data

import android.content.Context

class SharedPreferencesImplementation(private val context: Context) : SharedPreferencesRepository {
    private val USERNAME = "username"
    private val PASSWORD = "password"
    private val REMEMBERCOUNTER = "remember_counter"

    private val prefs = context.getSharedPreferences("login_settings", Context.MODE_PRIVATE)

    override fun setUsername(username: String) {
        prefs.edit().putString(USERNAME, username).apply()
    }

    override fun getUsername(): String? {
        return prefs.getString(USERNAME, "")
    }

    override fun setPassword(password: String) {
        prefs.edit().putString(PASSWORD, password).apply()
    }

    override fun getPassword(): String? {
        return prefs.getString(PASSWORD, "")
    }

    override fun setRememberCounter(counter: Int) {
        prefs.edit().putInt(REMEMBERCOUNTER, counter).apply()
    }

    override fun getRememberCounter(): Int {
        return prefs.getInt(REMEMBERCOUNTER, 0)
    }
}