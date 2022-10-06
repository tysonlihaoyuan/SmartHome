package com.example.chatroom.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.BoxScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore


import com.google.firebase.ktx.Firebase

class RegisterViewModel: ViewModel() {
    private lateinit var auth: FirebaseAuth
//    private  var user: FirebaseUser
    private val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }

//    internal val displayName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>(auth.currentUser?.displayName)
//    }

    init {
        Log.d(TAG, "AuthViewModel is created")

        auth = Firebase.auth
        Log.d(TAG, "AuthViewModel is created ${auth.currentUser?.email}")
//        user = auth.currentUser!!
        email.value = auth.currentUser?.email

    }


    fun RegisterUser(
        context: Context,
        userName: String,
        userEmail: String,
        userPassword: String,
        confirmPassword: String,
        navController: NavHostController
    ) {
        Log.d(TAG, "register user function called email is $userEmail")
        if (userName.isBlank()) {
            ToastUtil.also { it.showToast(context, it.NAME_IS_EMPTY) }
            return
        }
        if (userEmail.isBlank()) {
            ToastUtil.also { it.showToast(context, it.EMAIL_IS_EMPTY) }
            return
        }
        if (userPassword.isBlank()) {
            ToastUtil.also { it.showToast(context, it.PASSWORD_IS_EMPTY) }
            return
        }
        if (userPassword != confirmPassword) {

            ToastUtil.also { it.showToast(context, it.ERROR_TEXT_PASSWORD_DO_NOT_MATCH) }
            return

        }
        auth.createUserWithEmailAndPassword(userEmail, userEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val db= Firebase.firestore

                    db.collection("user").document("${FirebaseAuth.getInstance().currentUser!!.uid}").set(User(userName,"Register",userEmail,userPassword,uid=FirebaseAuth.getInstance().currentUser!!.uid))



                    Log.d(TAG, "register user with email ${userEmail} is successful")

                    navController.navigate(Routes.ChatRoom.route)


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    ToastUtil.also { it.showToast(context, it.CANNOT_REGISTER_CURRENT_USER) }

                }
            }

    }



}