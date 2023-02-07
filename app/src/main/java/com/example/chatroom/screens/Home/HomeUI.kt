package com.example.chatroom.screens.Home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.chatroom.screens.ChatChannel.showChannelList
import com.example.chatroom.screens.Login.LoginViewModel
import com.example.chatroom.screens.ScaffoldFrame
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun HomePage(navController: NavHostController, viewModel:LoginViewModel= hiltViewModel()){

    ScaffoldFrame(contentFunction = { SignOutButton(viewModel,navController = navController) }, navController = navController)


}
@Composable
fun SignOutButton(viewModel:LoginViewModel,navController: NavHostController) {
    Button(
        onClick = {viewModel.onClickSignOut(navController=navController)},
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "LogOut")
    }
}