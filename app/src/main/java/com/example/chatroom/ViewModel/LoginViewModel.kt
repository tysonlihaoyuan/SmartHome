package com.example.chatroom.ViewModel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.ToastUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel:ViewModel(){

    private lateinit var auth: FirebaseAuth
    //    private  var user: FirebaseUser
    private val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }



    init {
        Log.d(ContentValues.TAG, "LoginViewModel is created")

        auth = Firebase.auth
        Log.d(ContentValues.TAG, "LoginViewModel is created ${auth.currentUser?.email}")
//        user = auth.currentUser!!
        email.value = auth.currentUser?.email

    }
    fun Login(context: Context,

              userEmail: String,
              userPassword: String,

              navController: NavHostController
    ){
        Log.d(ContentValues.TAG, "register user function called email is $userEmail")

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
                    val user = auth.currentUser

                    ToastUtil.also { it.showToast(context, it.LOGIN_SUCCESSFUL) }
                    navController.navigate(Routes.ChatRoom.route)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    ToastUtil.also { it.showToast(context, it.ERROR_TEXT_PASSWORD_DO_NOT_MATCH) }

                }
            }
        // [END sign_in_with_email]
    }
}