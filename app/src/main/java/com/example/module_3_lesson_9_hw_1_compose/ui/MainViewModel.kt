package com.example.module_3_lesson_9_hw_1_compose.ui

import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackAllMessagesReceived
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackLoginSuccessful
import com.example.module_3_lesson_9_hw_1_compose.data.DbFirebaseManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val dbManager = DbFirebaseManager()

    private val _messagesList = MutableStateFlow(listOf<String>())
    val messagesList: StateFlow<List<String>> = _messagesList

    private val _currentUser = MutableStateFlow("")
    val currentUser: StateFlow<String> = _currentUser

    init {
        getAllMessagesOld()
    }
    fun sendMessageOld(message: String) {
        dbManager.sendMessageOld(message)
    }

    private fun getAllMessagesOld() {
        dbManager.getAllMessagesOld(object : DbCallbackAllMessagesReceived {
            override fun onAllMessagesReceived(messages: ArrayList<String>) {
                _messagesList.value = messages.toList()
            }
        })
    }

    fun createUser(username: String, password: String) {
        dbManager.createUser(username, password)
    }

    fun login(username: String, password: String) {
        dbManager.login(username, password, object : DbCallbackLoginSuccessful {
            override fun onSuccessfulLogin(currentUser: String) {
                _currentUser.value = currentUser
            }
        })
    }

    fun sendMessage(sender: String, content: String) {
        dbManager.sendMessage(sender, content)
    }
}