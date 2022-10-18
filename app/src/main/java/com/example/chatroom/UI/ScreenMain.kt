package com.example.chatroom.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.chatroom.Activities.RegisterActivity
import com.example.chatroom.Activities.Routes
import com.example.chatroom.ViewModel.AddFriendListViewModel
import com.example.chatroom.ViewModel.LoginViewModel
import com.example.chatroom.ViewModel.RegisterViewModel


@Composable
    fun ScreenMain(){
        val navController = rememberNavController()
        val RegisterviewModel = RegisterViewModel()
        val LoginviewModel = LoginViewModel()
        val AddFriendviewModel = AddFriendListViewModel()
        val context = LocalContext.current
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.SignUp.route) {
               RegisterPage(navController = navController,RegisterviewModel, context)
            }
            composable(Routes.Login.route) {
                LoginPage(navController = navController,LoginviewModel, context)
            }
            composable(Routes.ChatRoom.route) {
                ChatRoomPage(navController = navController,AddFriendviewModel,LoginviewModel,AddFriendviewModel.mUserList,context)
//                ChatRoomPage(navController = navController)
            }
            composable(Routes.ForgotPassword.route) {
                ForgetPasswordPage(navController = navController)
            }
        }
    }




