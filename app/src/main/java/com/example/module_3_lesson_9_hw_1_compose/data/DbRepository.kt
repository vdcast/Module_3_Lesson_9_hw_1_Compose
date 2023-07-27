package com.example.module_3_lesson_9_hw_1_compose.data

interface DbRepository {
    fun sendMessage(message: String)
    fun getAllMessages(callback: DbCallback)

    fun createUser(username: String, password: String)
}