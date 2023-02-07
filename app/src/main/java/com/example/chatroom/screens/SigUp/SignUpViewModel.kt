package com.example.chatroom.screens.SigUp

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.chatroom.Data.service.AccountService
import com.example.chatroom.Routes
import com.example.chatroom.common.ext.isValidEmail
import com.example.chatroom.common.ext.isValidPassword
import com.example.chatroom.common.ext.passwordMatches
import com.example.chatroom.common.snackbar.SnackbarManager
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.chatroom.R.string as AppText

@HiltViewModel
class SignUpViewModel@Inject constructor(
    private val accountService: AccountService,
    ): ViewModel() {
    var uiState = mutableStateOf(SignUpState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(navController: NavHostController) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

//        launchCatching {
//            accountService.linkAccount(email, password)
//            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
//        }
        viewModelScope.launch(){
            try{
                accountService.linkAccount(email,password)
                navController.navigate(Routes.FriendList.route)
                Log.d(ContentValues.TAG, "register user with email ${email} is successful")
            }
            catch ( e: FirebaseAuthException){
                Log.d(ContentValues.TAG, "register user with email ${email} is dead")
                SnackbarManager.showMessage(AppText.recovery_email_sent)

            }
        }
    }


}