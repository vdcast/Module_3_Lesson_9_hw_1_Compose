package com.example.module_3_lesson_9_hw_1_compose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackAllMessagesReceived
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackLoginSuccessful
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackUserNotFound
import com.example.module_3_lesson_9_hw_1_compose.data.DbCallbackWrongPassword
import com.example.module_3_lesson_9_hw_1_compose.data.DbFirebaseManager
import com.example.module_3_lesson_9_hw_1_compose.data.Message
import com.example.module_3_lesson_9_hw_1_compose.data.SharedPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val prefs: SharedPreferencesRepository) : ViewModel() {
    private val dbManager = DbFirebaseManager()

    fun kek() {
        prefs.setPassword("daw")
    }

    private val _currentUser = MutableStateFlow("")
    val currentUser: StateFlow<String> = _currentUser

    private val _messagesList = MutableStateFlow(listOf<Message>())
    val messagesList: StateFlow<List<Message>> = _messagesList

    val snackbarError = MutableStateFlow<String?>(null)

    val isCheckedRememberMeViewModel = MutableStateFlow<Boolean>(false)

//    private val _messagesListOld = MutableStateFlow(listOf<String>())
//    val messagesListOld: StateFlow<List<String>> = _messagesListOld

//    init {
//        getAllMessages()
//    }

//    fun sendMessageOld(message: String) {
//        dbManager.sendMessageOld(message)
//    }
//    private fun getAllMessagesOld() {
//        dbManager.getAllMessagesOld(object : DbCallbackAllMessagesReceivedOld {
//            override fun onAllMessagesReceivedOld(messages: ArrayList<String>) {
//                _messagesListOld.value = messages.toList()
//            }
//        })
//    }

    fun createUser(username: String, password: String) {
        dbManager.createUser(username, password)
    }
    fun login(username: String, password: String) {
        dbManager.login(
            username,
            password,
            object : DbCallbackLoginSuccessful {
                override fun onSuccessfulLogin(currentUser: String) {
                    _currentUser.value = currentUser
                    getAllMessages()
                    rememberMe(username, password)
                }
            },
            object : DbCallbackWrongPassword {
                override fun onWrongPassword() {
                    snackbarError.value = "Wrong password"
                }
            },
            object : DbCallbackUserNotFound {
                override fun onUserNotFound() {
                    snackbarError.value = "User not found"
                }
            }
        )
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

    fun logout() {
        _currentUser.value = ""

        isCheckedRememberMeViewModel.value = false
        prefs.setUsername("")
        prefs.setPassword("")
        prefs.setRememberCounter(0)
        Log.d("MYLOG", prefs.getUsername().toString())
        Log.d("MYLOG", prefs.getPassword().toString())
        Log.d("MYLOG", prefs.getRememberCounter().toString())
    }

    fun rememberMe(username: String, password: String) {
        if (isCheckedRememberMeViewModel.value) {
            prefs.setUsername(username)
            prefs.setPassword(password)
            prefs.setRememberCounter(5)
            Log.d("MYLOG", prefs.getUsername().toString())
            Log.d("MYLOG", prefs.getPassword().toString())
            Log.d("MYLOG", prefs.getRememberCounter().toString())
        }
    }
}