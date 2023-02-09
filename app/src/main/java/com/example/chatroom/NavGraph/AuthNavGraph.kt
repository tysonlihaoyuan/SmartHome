package com.example.chatroom.NavGraph

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatroom.Routes
import com.example.chatroom.screens.Login.LoginPage
import com.example.chatroom.screens.SigUp.RegisterPage

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController,context:Context){
    navigation(startDestination = Routes.Login.route, route = Routes.Authenticate.route){
        composable(
            route = Routes.Login.route
        ) {
            LoginPage(navController = navHostController,context= context)
        }
        composable(
            route = Routes.SignUp.route
        ) {
            RegisterPage(navController = navHostController,context= context)
        }
    }



}