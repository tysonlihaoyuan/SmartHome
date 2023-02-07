package com.example.chatroom.screens.SigUp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatroom.UI.Theme.Purple700
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatroom.Routes
import com.example.chatroom.R.string as AppText
@Composable
fun RegisterPage(navController: NavHostController, viewModel: SignUpViewModel = hiltViewModel(), context : Context) {
    val uiState by viewModel.uiState
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Back to Login"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { navController.navigate(Routes.Login.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple700
            )
        )
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        remember { mutableStateOf(TextFieldValue()) }
////        val name = remember { mutableStateOf(TextFieldValue()) }
//        val password = remember { mutableStateOf(TextFieldValue()) }
//        val confirmpassword = remember { mutableStateOf(TextFieldValue()) }

        Text(text = "Register", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))


        //Username field
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Email") },
            placeholder = { Text(stringResource(AppText.email)) },
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) })

//        Spacer(modifier = Modifier.height(20.dp))
//        TextField(
//            label = { Text(text = "name") },
//            value = name.value,
//            onValueChange = { name.value = it })

        //Password field
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = uiState.password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onPasswordChange(it) })

        //ConfirmPassword filed
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "ConfirmPassword") },
            value = uiState.repeatPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onRepeatPasswordChange(it) })
        Spacer(modifier = Modifier.height(20.dp))


//        val userName =  name.value.text.trim();
//        val userEmail =  email.value.text.trim();
//        val userPassword =  password.value.text;
//        val userConfirmPassword =  confirmpassword.value.text;
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = { viewModel.onSignUpClick(navController) },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Register")
            }

        }

    }
}