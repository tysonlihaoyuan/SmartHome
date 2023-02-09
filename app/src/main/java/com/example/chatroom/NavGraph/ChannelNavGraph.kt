package com.example.chatroom.NavGraph

import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.chatroom.Routes
import com.example.chatroom.screens.ChatChannel.ChatChannelPage
import com.example.chatroom.screens.ChatChannel.ChatChannelViewModel
import com.example.chatroom.screens.ChatUI.ChatViewModel
import com.example.chatroom.screens.ChatUI.ChatViewPage
import com.example.chatroom.screens.FriendList.FriendListPage
import com.example.chatroom.screens.Home.HomePage
import com.example.chatroom.screens.Login.LoginPage
import com.example.chatroom.screens.SigUp.RegisterPage

fun NavGraphBuilder.channelNavGraph(navHostController: NavHostController, context: Context,viewModel: ChatChannelViewModel){

    navigation(startDestination = Routes.FriendList.route, route = Routes.ChannelAndFriend.route){

        composable(Routes.ChatChanel.route) {
            val chatChannelModel: ChatChannelViewModel = hiltViewModel()
            ChatChannelPage(navController = navHostController,
                viewModel = chatChannelModel
            )


        }
        composable(Routes.FriendList.route) {
            val chatChannelModel: ChatChannelViewModel = hiltViewModel()
            FriendListPage(chatChannelModel, navController = navHostController)
        }

        composable(Routes.ChatView.route +"/{targetUserUid}",
            arguments = listOf(
                navArgument("targetUserUid"){
                    type= NavType.StringType
                    defaultValue="Empty"
                    nullable=true
                }
            )) {entry->

            val chatChannelModel: ChatChannelViewModel = hiltViewModel()
            val chatViewModel: ChatViewModel = hiltViewModel()
            entry.arguments?.getString("targetUserUid")
                ?.let { ChatViewPage(chatChannelModel,chatViewModel, targetUserUid = it) }

        }
        composable(Routes.Home.route) {

            HomePage(navController = navHostController)
        }
    }



}