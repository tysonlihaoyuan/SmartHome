package com.example.chatroom.UI

import android.security.identity.AccessControlProfile
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.UI.Theme.Purple700

@Preview
@Composable
fun ChatboxPage(navController: NavHostController){
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        TopBarSection(username = "Bot", profile = painterResource(id = res.drawable.portrait.jpg), isOnline = true)
        ChatSection(Modifier.weight(1f))
        MessageSection()
    }
}
@Composable
fun TopBarSection(username:String, profile: Painter, isOnline: Boolean){
    Card(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), backgroundColor = Color(0xFFFAFAFA), elevation = 4.dp) {
        Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = profile, contentDescription = null, modifier = Modifier.size(42.dp).clip(CircleShape))

        }
    }
}

