package com.example.chatroom.UI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.Data.User
import com.example.chatroom.ViewModel.LoginViewModel
import kotlinx.coroutines.*
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavHostController,loginViewModel: LoginViewModel,mlogoutFlag:MutableLiveData<Boolean>,context: Context){
   val logoutFlag by mlogoutFlag.observeAsState()
    val activity = (LocalContext.current as? Activity)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { Topbar( navController = navController ) },
        bottomBar = { BottomNavigationBar(navController) },

        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Routes.AddFriend.route)}){
            Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
        } }

        ){
        Button(
            modifier = Modifier.fillMaxWidth(1f),
            onClick = {
                GlobalScope.launch(Dispatchers.IO){
                    Log.d(ContentValues.TAG, "the button is initisal clicked $logoutFlag")
                    loginViewModel.logout()

                    delay(3000)
                    Log.d(ContentValues.TAG, "the button in IO clicked $logoutFlag")
                    withContext(Dispatchers.Main){
                        if (logoutFlag==true){
                            Log.d(ContentValues.TAG, "the button is clicked $logoutFlag")
                            if (activity != null) {
                                Log.d(ContentValues.TAG, "the button is clicked $activity")
                                activity.finish()
                                Log.d(ContentValues.TAG, "the button is clicked $activity")
                                navController.navigate(Routes.Login.route)
                            }

                        }
                    }
                }

//            Log.d(ContentValues.TAG, "the button is clicked $logoutFlag")
//            navController.navigate(Routes.Login.route)

            }
        ){

            Text(text = "logout")
        }

    }
//    ScaffoldFrame(contentFunction = {}, navController =navController)
}


