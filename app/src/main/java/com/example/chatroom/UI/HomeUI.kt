package com.example.chatroom.UI

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.chatroom.Utility.ToastUtil

@Composable
fun HomePage(navController: NavHostController,context: Context){
    ScaffoldFrame(contentFunction = { button(context)}, navController =navController)
}

@Composable
fun button(context:Context){
    Button(
        modifier = Modifier.fillMaxWidth(1f),
        onClick = {
            ToastUtil.showToast(
                context,
                "the button is clicked"
            )
        }
    ){

        Text(text = "home page")
    }
}
