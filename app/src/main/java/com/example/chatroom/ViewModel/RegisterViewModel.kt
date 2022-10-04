package com.example.chatroom.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel: ViewModel(){
    private lateinit var auth: FirebaseAuth
    internal lateinit var user: FirebaseUser
    internal val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }
    internal val displayName: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.displayName)
    }



}