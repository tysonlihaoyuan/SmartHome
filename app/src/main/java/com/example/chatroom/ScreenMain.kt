package com.example.chatroom


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.chatroom.NavGraph.authNavGraph
import com.example.chatroom.NavGraph.channelNavGraph
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
    val navHostController = rememberNavController()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = Routes.Authenticate.route,
        route = Routes.Root_Graph.route
    ) {
        navigation(startDestination = Routes.Login.route, route = Routes.Authenticate.route) {
            composable(
                route = Routes.Login.route
            ) {
                LoginPage(navController = navHostController, context = context)
            }
            composable(
                route = Routes.SignUp.route
            ) {
                RegisterPage(navController = navHostController, context = context)
            }
        }

        navigation(
            startDestination = Routes.FriendList.route,
            route = Routes.ChannelAndFriend.route
        ) {

            composable(Routes.ChatChanel.route) {
//                val chatChannelModel: ChatChannelViewModel = hiltViewModel()
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val chatChannelModel = viewModel<ChatChannelViewModel>(viewModelStoreOwner = viewModelStoreOwner)
                    ChatChannelPage(
                        navController = navHostController,
                        viewModel = chatChannelModel
                    )

                }
            }
            composable(Routes.FriendList.route) {

                val chatChannelModel =
                    viewModel<ChatChannelViewModel>(viewModelStoreOwner = viewModelStoreOwner)
                FriendListPage(chatChannelModel, navController = navHostController)
            }

            composable(Routes.ChatView.route + "/{targetUserUid}",
                arguments = listOf(
                    navArgument("targetUserUid") {
                        type = NavType.StringType
                        defaultValue = "Empty"
                        nullable = true
                    }
                )) { entry ->

                val chatChannelModel: ChatChannelViewModel = viewModel(viewModelStoreOwner = viewModelStoreOwner)
                val chatViewModel: ChatViewModel = hiltViewModel()
                entry.arguments?.getString("targetUserUid")
                    ?.let { ChatViewPage(chatChannelModel, chatViewModel, targetUserUid = it) }

            }
            composable(Routes.Home.route) {

                HomePage(navController = navHostController)
            }


        }

    }
}






