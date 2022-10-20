package com.example.chatroom.UI

import android.security.identity.AccessControlProfile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import java.text.SimpleDateFormat
import java.util.*
val message = mutableStateOf("")
private val BotChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)

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
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(text = username, fontWeight = FontWeight.SemiBold)
                Text(text = if (isOnline) "Online" else "Offline", fontSize = 12.sp)
            }

        }
    }
}

@Composable
fun ChatSection(modifier: Modifier = Modifier){
    val simpleDateFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(16.dp), reverseLayout = true){
        items(message_dummy){
            chat -> MessageItem(messageText = chat.text, time = simpleDateFormat.format((chat.time),isOut = chat.isOut) Spacer(modifier = Modifier.height(8.dp)))
        }
    }
}

@Composable
fun MessageItem(messageText: String?, time: String, isOut: Boolean){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (isOut) Alignment.End else Alignment.Start) {
        if (messageText!=null){
            if(messageText!=""){
                Box(modifier = Modifier
                    .background(
                        if (isOut) MaterialTheme.colors.primary else Color(0xFF616161),
                        shape = if (isOut) AuthorChatBubbleShape else BotChatBubbleShape
                    )
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)){
                    Text(text = messageText,color=Color.White)
                }
            }
        }
        Text(text = time, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun MessageSection(){
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White, elevation = 10.dp) {
        OutlinedTextField(placeholder = {Text("Message..")}, value = message.value, onValueChange = {message.value = it}, shape = RoundedCornerShape(25.dp),
        trailingIcon = {
            Icon(painter = painterResource(id = res.drawable.send_icon), contentDescription = null, tint = MaterialTheme.colors.primary, modifier = Modifier.clickable {})
        }, modifier = Modifier.fillMaxWidth().padding(10.dp),)
    }
}

