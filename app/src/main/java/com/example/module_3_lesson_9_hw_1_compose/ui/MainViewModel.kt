package com.example.module_3_lesson_9_hw_1_compose.ui

import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallback
import com.example.module_3_lesson_9_hw_1_compose.data.DbFirebaseManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _messagesList = MutableStateFlow(listOf<String>())
    val messagesList: StateFlow<List<String>> = _messagesList

    private val dbManager = DbFirebaseManager()

    fun sendMessage(message: String) {
        dbManager.sendMessage(message)
    }

    private fun getAllMessagesViewModel() {
        dbManager.getAllMessages(object : DbCallback {
            override fun onAllMessagesReceived(messages: ArrayList<String>) {
                _messagesList.value = messages.toList()
            }
        })
    }

    init {
        getAllMessagesViewModel()
    }
}