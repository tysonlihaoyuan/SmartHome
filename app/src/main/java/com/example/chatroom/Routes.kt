package com.example.chatroom

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Register: Routes("Register")
    object SignUp: Routes("SignUp")
//    object ChatRoom: Routes("ChatRoom")
    object ForgotPassword: Routes("ForgotPassword")
    object Home: Routes("Home")
    object ChatChanel: Routes("ChatChanel")
    object FriendList: Routes("FriendList")
    object AddFriend: Routes("AddFriend")
    object ChatView: Routes("ChatView")


    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}