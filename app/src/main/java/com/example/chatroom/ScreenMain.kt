package com.example.chatroom


import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.chatroom.screens.ChatChannel.ChatChannelPage
import com.example.chatroom.screens.ChatChannel.ChatChannelViewModel
import com.example.chatroom.screens.ChatUI.ChatViewModel
import com.example.chatroom.screens.ChatUI.ChatViewPage

import com.example.chatroom.screens.FriendList.FriendListPage
import com.example.chatroom.screens.Home.HomePage
import com.example.chatroom.screens.Login.LoginPage


import com.example.chatroom.screens.SigUp.RegisterPage


@Composable
fun ScreenMain() {
    val navController = rememberNavController()


    val context = LocalContext.current

//        val activity = AppCompatActivity
    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.SignUp.route) {
            RegisterPage(navController = navController,context= context)
        }
        composable(Routes.Login.route) {
            LoginPage(navController = navController,context= context)
        }

        composable(Routes.ChatChanel.route) {
            val chatChannelModel: ChatChannelViewModel= hiltViewModel()
            ChatChannelPage(navController = navController,context=context,
                viewModel = chatChannelModel
            )

            //            Log.d(ContentValues.TAG, "ChatChanel is $viewModel ")
//            ChatChannelPage(navController = navController,context=context)

        }
        composable(Routes.FriendList.route) {
            val chatChannelModel: ChatChannelViewModel= hiltViewModel()
            FriendListPage(chatChannelModel,navController = navController)
//            Log.d(ContentValues.TAG, "FriendList is $viewModel ")
//            FriendListPage(navController = navController)
        }
//        composable(Routes.ForgotPassword.route) {
//            ForgetPasswordPage(navController = navController)
//        }
        composable(Routes.ChatView.route +"/{targetUserUid}",
            arguments = listOf(
            navArgument("targetUserUid"){
                type= NavType.StringType
                defaultValue="Empty"
                nullable=true
            }
        )) {entry->

            val chatChannelModel: ChatChannelViewModel= hiltViewModel()
            val chatViewModel: ChatViewModel= hiltViewModel()
            entry.arguments?.getString("targetUserUid")
                ?.let { ChatViewPage(chatChannelModel,chatViewModel, targetUserUid = it) }

        }
        composable(Routes.Home.route) {

            HomePage(navController = navController)
        }

    }

}




