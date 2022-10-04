package com.example.chatroom.UI

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.chatroom.Activities.Routes


@Composable
    fun ScreenMain(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Login.route) {

            composable(Routes.Login.route) {
               LoginPage(navController = navController)
            }
        }
    }




