package com.example.chatroom.ViewModel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.ToastUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginViewModel:ViewModel(){

    private var auth: FirebaseAuth


    init {
        Log.d(TAG, "RegisterViewModel is created")

        auth = FirebaseAuth.getInstance()
        Log.d(TAG, "init email is  ${auth.currentUser}")


    }

    fun login(context: Context,
              userEmail: String,
              userPassword: String,
              navController: NavHostController
    ){



        if (userEmail.isBlank()) {
            ToastUtil.also { it.showToast(context, it.EMAIL_IS_EMPTY) }
            return
        }
        if (userPassword.isBlank()) {
            ToastUtil.also { it.showToast(context, it.PASSWORD_IS_EMPTY) }
            return
        }




        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")



                    ToastUtil.also { it.showToast(context, it.LOGIN_SUCCESSFUL) }
                    navController.navigate(Routes.ChatRoom.route)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                    ToastUtil.also { it.showToast(context, it.CANNOT_LOGIN_CURRENT_USER) }


                }
            }

    }
    fun logout(navController: NavHostController){
        Firebase.auth.signOut()
        navController.navigate(Routes.Login.route)
    }
}