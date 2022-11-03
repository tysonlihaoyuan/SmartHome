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
import com.example.chatroom.ViewModel.MessageListViewModel
import com.example.chatroom.ViewModel.RegisterViewModel


@Composable
    fun ScreenMain(){
        val navController = rememberNavController()
        val RegisterviewModel = RegisterViewModel()
        val LoginviewModel = LoginViewModel()
        val AddFriendviewModel = AddFriendListViewModel()
        val MessageListViewModel = MessageListViewModel()
        val context = LocalContext.current
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.SignUp.route) {
               RegisterPage(navController = navController,RegisterviewModel, context)
            }
            composable(Routes.Login.route) {
                LoginPage(navController = navController,LoginviewModel, context)
            }
            composable(Routes.FriendList.route) {
                FriendLsit(navController = navController,AddFriendviewModel,AddFriendviewModel.mUserList,context)
            }
            composable(Routes.ForgotPassword.route) {
                ForgetPasswordPage(navController = navController)
            }
            composable(Routes.ChatChanel.route) {
                chatChannelPage(navController = navController,MessageListViewModel,MessageListViewModel.mChannelLsit,MessageListViewModel.mUser,context)
            }
            composable(Routes.Home.route) {
                HomePage(navController =navController , context =context )
            }
            composable(Routes.AddFriend.route) {
                addFriendPage(navController =navController , context =context )
            }
            composable(Routes.ChatView.route) {
                ChatViewPage(navController =navController , context =context , viewModel = MessageListViewModel,MessageListViewModel.mUser,MessageListViewModel.mMessageList)
            }
        }

    }




