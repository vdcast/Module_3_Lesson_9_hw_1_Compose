package com.example.module_3_lesson_9_hw_1_compose.ui.navigation

sealed class ScreenRoutes(val route: String) {
    object LoginScreen : ScreenRoutes("login_screen")
    object ChatScreen : ScreenRoutes("chat_screen")
    object SignUpScreen : ScreenRoutes("sign_up_screen")
}