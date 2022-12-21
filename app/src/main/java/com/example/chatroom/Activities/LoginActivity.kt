package com.example.chatroom.Activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import com.example.chatroom.UI.ChatroomTheme
import com.example.chatroom.UI.ScreenMain
import com.example.chatroom.ViewModel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

// ...
// Initialize Firebase Auth
    private val model: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ChatroomTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

//                    ScreenMain();

                }

            }
        }
//        model.mlogoutAppFlag.observe(
//            this
//        ) { aBoolean ->
//            if (aBoolean == true) {
//                Log.d(ContentValues.TAG, "observse the change, $aBoolean")
//                finish()
//            }
//        }

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}