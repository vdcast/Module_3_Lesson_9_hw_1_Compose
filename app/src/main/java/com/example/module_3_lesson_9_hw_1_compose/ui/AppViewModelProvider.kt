package com.example.module_3_lesson_9_hw_1_compose.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.module_3_lesson_9_hw_1_compose.ChatApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(chatApplication().container.prefs)
        }
    }
}

fun CreationExtras.chatApplication(): ChatApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ChatApplication)