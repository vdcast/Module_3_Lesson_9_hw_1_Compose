package com.example.module_3_lesson_9_hw_1_compose.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.module_3_lesson_9_hw_1_compose.ui.chat.ChatScreen
import com.example.module_3_lesson_9_hw_1_compose.ui.navigation.ChatAppNavGraph

@Composable
fun ChatApp(
    viewModelMain: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    ChatAppNavGraph(
        viewModelMain = viewModelMain,
        navController = navController
    )
}