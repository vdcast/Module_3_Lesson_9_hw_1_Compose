package com.example.module_3_lesson_9_hw_1_compose.data

interface SharedPreferencesRepository {
    fun setUsername(username: String)
    fun getUsername(): String?
    fun setPassword(password: String)
    fun getPassword(): String?
    fun setRememberCounter(counter: Int)
    fun getRememberCounter(): Int
}