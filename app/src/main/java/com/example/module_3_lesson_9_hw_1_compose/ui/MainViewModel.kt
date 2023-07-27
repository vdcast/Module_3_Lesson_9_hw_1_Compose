package com.example.module_3_lesson_9_hw_1_compose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackAllMessagesReceived
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackAllMessagesReceivedOld
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackLoginSuccessful
import com.example.module_3_lesson_9_hw_1_compose.data.DbFirebaseManager
import com.example.module_3_lesson_9_hw_1_compose.data.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val dbManager = DbFirebaseManager()

    private val _messagesListOld = MutableStateFlow(listOf<String>())
    val messagesListOld: StateFlow<List<String>> = _messagesListOld

    private val _currentUser = MutableStateFlow("")
    val currentUser: StateFlow<String> = _currentUser

    private val _messagesList = MutableStateFlow(listOf<Message>())
    val messagesList: StateFlow<List<Message>> = _messagesList

    init {
        getAllMessagesOld()
        getAllMessages()
    }

    fun sendMessageOld(message: String) {
        dbManager.sendMessageOld(message)
    }
    private fun getAllMessagesOld() {
        dbManager.getAllMessagesOld(object : DbCallbackAllMessagesReceivedOld {
            override fun onAllMessagesReceivedOld(messages: ArrayList<String>) {
                _messagesListOld.value = messages.toList()
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
    private fun getAllMessages() {
        dbManager.getAllMessages(object : DbCallbackAllMessagesReceived {
            override fun onAllMessagesReceived(messages: ArrayList<Message>) {
                _messagesList.value = messages.toList()
            }
        })
    }
}