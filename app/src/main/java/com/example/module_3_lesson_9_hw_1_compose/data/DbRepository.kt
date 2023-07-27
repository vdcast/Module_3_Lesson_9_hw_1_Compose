package com.example.module_3_lesson_9_hw_1_compose.data

interface DbRepository {
    fun sendMessageOld(message: String)
    fun getAllMessagesOld(callback: DbCallbackAllMessagesReceived)
    fun createUser(username: String, password: String)
    fun login(username: String, password: String, callback: DbCallbackLoginSuccessful)
    fun sendMessage(sender: String, content: String)
}