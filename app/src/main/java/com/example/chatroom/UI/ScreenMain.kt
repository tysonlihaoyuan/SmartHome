package com.example.chatroom.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.chatroom.Activities.RegisterActivity
import com.example.chatroom.Activities.Routes
import com.example.chatroom.ViewModel.RegisterViewModel


@Composable
    fun ScreenMain(){
        val navController = rememberNavController()
        val viewModel = RegisterViewModel()
        val context = LocalContext.current
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.SignUp.route) {
               RegisterPage(navController = navController,viewModel, context)
            }
            composable(Routes.Login.route) {
                LoginPage(navController = navController)
            }
            composable(Routes.ChatRoom.route) {
                ChatRoomPage(navController = navController)
            }
            composable(Routes.ForgotPassword.route) {
                ForgetPasswordPage(navController = navController)
            }
        }
    }




