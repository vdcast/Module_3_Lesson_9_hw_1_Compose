package com.example.module_3_lesson_9_hw_1_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.module_3_lesson_9_hw_1_compose.ui.MainViewModel
import com.example.module_3_lesson_9_hw_1_compose.ui.chat.ChatScreen
import com.example.module_3_lesson_9_hw_1_compose.ui.login.LoginScreen
import com.example.module_3_lesson_9_hw_1_compose.ui.signup.SignUpScreen

@Composable
fun ChatAppNavGraph(
    viewModelMain: MainViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.LoginScreen.route
    ) {
        composable(ScreenRoutes.LoginScreen.route) {
            LoginScreen(
                viewModelMain = viewModelMain,
                onLoginClicked = { navController.navigate(ScreenRoutes.ChatScreen.route) },
                onSignUpClicked = { navController.navigate(ScreenRoutes.SignUpScreen.route) }
            )
        }
        composable(ScreenRoutes.ChatScreen.route) {
            ChatScreen(viewModelMain = viewModelMain)
        }
        composable(ScreenRoutes.SignUpScreen.route) {
            SignUpScreen(
                viewModelMain = viewModelMain,
                onCreateClicked = { navController.popBackStack() },
                onCancelCLicked = { navController.popBackStack() }
            )
        }
    }
}