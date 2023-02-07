package com.example.chatroom.screens.Login

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.chatroom.Data.User
import com.example.chatroom.Data.service.AccountService
import com.example.chatroom.R
import com.example.chatroom.Routes
import com.example.chatroom.common.ext.isValidEmail
import com.example.chatroom.common.ext.isValidPassword
import com.example.chatroom.common.ext.passwordMatches
import com.example.chatroom.common.snackbar.SnackbarManager
import com.example.chatroom.screens.SigUp.SignUpState
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val accountService: AccountService
):ViewModel() {
    var uiState = mutableStateOf(LoginState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password


    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    fun onLogInClick(navController: NavHostController) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            Log.d(ContentValues.TAG, "${R.string.email_error}")
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            Log.d(ContentValues.TAG, "${R.string.password_error}")
            return
        }



//        launchCatching {
//            accountService.linkAccount(email, password)
//            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
//        }
        viewModelScope.launch(){
            try{
                accountService.authenticate(email,password)
                navController.navigate(Routes.FriendList.route)
                Log.d(ContentValues.TAG, "LogIn user with email ${email} is successful")
            }
            catch ( e: FirebaseAuthException){
                Log.d(ContentValues.TAG, "LogIn user with email ${email} is dead")
                SnackbarManager.showMessage(R.string.recovery_email_sent)

            }
        }
    }
    fun onClickSignOut(navController: NavHostController){

        viewModelScope.launch {


            accountService.currentUser.collect{ user ->
                Log.d(ContentValues.TAG, "LogIn user before is ${user.uid} is successful")
                val userTask = Firebase.database.getReference("Users").child(user.uid).get().await()
                val users = userTask.getValue<User>()
                Log.d(ContentValues.TAG, "LogIn user is ${user.uid} is successful")
                if (users != null) {
                    Log.d(ContentValues.TAG, "LogIn user is ${users.uid} is successful")
                }

            }
            accountService.signOut()

            Log.d(ContentValues.TAG, "LogIn user with email ${accountService.currentUserId} is successful")
            navController.navigate(Routes.Login.route)

        }
    }


}