package com.example.chatroom.screens.ChatUI

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatroom.Data.Message
import com.example.chatroom.Data.User
import com.example.chatroom.screens.ChatChannel.ChatChannelViewModel


@Composable
fun ChatViewPage(chatChannelviewModel: ChatChannelViewModel, chatViewModelviewModel: ChatViewModel,targetUserUid:String){

    val messageList by chatViewModelviewModel.messageListLiveData.observeAsState(initial = emptyList())



    Log.d(ContentValues.TAG, "page is load  ${chatChannelviewModel.targetUserLiveData.value}")
     chatViewModelviewModel.getMessageList(targetUserUid)

    val targetUser = chatChannelviewModel.getUserbyId(targetUserUid)

    val targetUsername = targetUser?.userName
    Log.d(ContentValues.TAG, "user is existing  ${chatChannelviewModel.targetUserLiveData.value}")

//        Log.d(ContentValues.TAG, "user is existing in virw  ${user?.userName}")
//    Log.d(ContentValues.TAG, "message is existing  ${messageList}")
    ScaffoldFrameTop(contentFunction = {

        if (targetUsername != null) {


                showMessageList(
                    messageList = messageList ,
                    chatChannelviewModel = chatChannelviewModel
                )


        }



    },  bottomFunction= {
        if (targetUsername != null) {
            AddMessage(user = targetUserUid, chatViewModel =chatViewModelviewModel )

        }
    } , topbarFunction = {
        if (targetUsername != null) {
            Top(user = targetUsername)
        }
    } )

}


@Composable
fun MessageLiveDataLoadingComponent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // A pre-defined composable that's capable of rendering a circular progress indicator. It
        // honors the Material Design specification.
        androidx.compose.material.Text(text = "new chat")

    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showMessageList(messageList: List<Message>,  chatChannelviewModel:ChatChannelViewModel) {

    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val ( topicText,userList,userListContainer,userItem) = createRefs()
//

            ConstraintLayout(Modifier.constrainAs(userListContainer){
//

            }) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .verticalScroll(state = scrollState)
                        .constrainAs(userList) {
                            top.linkTo(parent.bottom, 10.dp)
                        }){

                    items(
                        items = messageList, itemContent={ message ->

                        Card(
                            shape = RoundedCornerShape(4.dp),
                            backgroundColor = Color.White,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .constrainAs(userItem) {
//                                        start.linkTo(parent.start, 5.dp)
//                                        end.linkTo(parent.end, 5.dp)
                                }

                                .padding(8.dp)


                        ) {
                            val currentUSer = chatChannelviewModel.getUserbyId(message.sent)?.userName

                            Log.d(ContentValues.TAG, "user is existing  ${message.sent}")
                            ListItem(

                                text = {

                                    Text(
                                        text = message.message,

                                        style = TextStyle(
                                            fontFamily = FontFamily.Serif, fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                    )
                                }, secondaryText = {
                                    androidx.compose.material.Text(
                                        text = "Sent From: $currentUSer",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Serif, fontSize = 15.sp,
                                            fontWeight = FontWeight.Light, color = Color.DarkGray
                                        )
                                    )
                                }, icon = {

                                })
                        }
                    })


                }
            }


        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldFrameTop(
    contentFunction: @Composable () -> Unit,
    topbarFunction: @Composable () -> Unit,
    bottomFunction: @Composable () -> Unit,

    ) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { topbarFunction() },
        bottomBar = { bottomFunction() },



        ){
        contentFunction()
    }
}
@Composable
fun Top(user: String){
    androidx.compose.material.Text(
        text = "${user}",
        style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
    )
}


@Composable
fun AddMessage(user:String,chatViewModel: ChatViewModel) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 10.dp)

    ) {

        var message = remember { mutableStateOf(TextFieldValue()) }



//
//        Text(text = "${user.userName}", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier
            .height(20.dp)
            .padding(10.dp))
        OutlinedTextField(
            value = message.value,
            onValueChange = {message.value=it},
            label = {
                androidx.compose.material.Text(
                    "Type Your Message"
                )
            },
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 1.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        chatViewModel.updateMessage(message = message.value.text, user);message.value=
                        TextFieldValue("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Button"
                    )
                }
            }
        )



    }
}