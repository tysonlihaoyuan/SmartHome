package com.example.chatroom.Activities

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.chatroom.UI.ChatroomTheme
import com.example.chatroom.UI.ScreenMain

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ChatroomTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    ScreenMain();

                }

            }
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}