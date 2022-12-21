package com.example.chatroom.UI


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

import com.example.chatroom.Activities.RegisterActivity
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.FirebaseFactory
import com.example.chatroom.ViewModel.AddFriendListViewModel

import com.example.chatroom.ViewModel.LoginViewModel
import com.example.chatroom.ViewModel.ChannelListViewModel
import com.example.chatroom.ViewModel.RegisterViewModel


@Composable
    fun ScreenMain(){
        val navController = rememberNavController()
        val RegisterviewModel = RegisterViewModel()
        val LoginviewModel = LoginViewModel()
        val AddFriendviewModel = AddFriendListViewModel()
        val ChannelListViewModel = ChannelListViewModel(firebaseFactory = FirebaseFactory())
        val context = LocalContext.current
//        val activity = AppCompatActivity
        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.SignUp.route) {
               RegisterPage(navController = navController,RegisterviewModel, context)
            }
            composable(Routes.Login.route) {
                LoginPage(navController = navController,LoginviewModel, context)
            }
            composable(Routes.FriendList.route) {
                FriendLsit(navController = navController,AddFriendviewModel,ChannelListViewModel,ChannelListViewModel.mUser,AddFriendviewModel.mUserList,context)
            }
            composable(Routes.ForgotPassword.route) {
                ForgetPasswordPage(navController = navController)
            }
            composable(Routes.ChatChanel.route) {
                chatChannelPage(navController = navController,ChannelListViewModel,ChannelListViewModel.mChannelLsit,ChannelListViewModel.mUser,context)
            }
            composable(Routes.Home.route) {
                HomePage(navController =navController , LoginviewModel,LoginviewModel.mlogoutAppFlag,context =context )
            }
            composable(Routes.AddFriend.route) {
                addFriendPage(navController =navController , context =context )
            }

            composable(Routes.ChatView.route) {
                ChatViewPage(navController =navController , context =context , viewModel = ChannelListViewModel,ChannelListViewModel.mUser,ChannelListViewModel.mMessageList)
            }
        }

    }




