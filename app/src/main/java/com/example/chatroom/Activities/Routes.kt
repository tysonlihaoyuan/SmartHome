package com.example.chatroom.Activities

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Register: Routes("Register")
    object SignUp: Routes("SignUp")
//    object ChatRoom: Routes("ChatRoom")
    object ForgotPassword: Routes("ForgotPassword")
    object Home: Routes("Home")
    object ChatChanel: Routes("ChatChanel")
    object FriendList: Routes("Friends")
    object AddFriend: Routes("AddFriend")



}