package com.example.chatroom.UI

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.Data.Message
import com.example.chatroom.ViewModel.Data.User
import com.example.chatroom.ViewModel.MessageListViewModel

@Composable
fun ChatViewPage(navController: NavHostController, context: Context, viewModel:MessageListViewModel, userLiveData: MutableLiveData<User?>,messageLiveData:MutableLiveData<List<Message>>) {

    val messageList by messageLiveData.observeAsState(initial = emptyList())
    val user by userLiveData.observeAsState(initial = User())
    Log.d(ContentValues.TAG, "user is existing  ${user?.userName}")
    // Since Jetpack Compose uses the declarative way of programming, we can easily decide what
    // needs to shows vs hidden based on which branch of code is being executed. In this example,
    // if the personList returned by the live data is empty, we want to show a loading indicator,
    // otherwise we want show the appropriate list. So we run the appropriate composable based on
    // the branch of code executed and that takes care of rendering the right views.
    user?.let { viewModel.getCurrentMessageHistory(it.uid) }
////        Log.d(ContentValues.TAG, "user is existing  ${user?.userName}")
//    Log.d(ContentValues.TAG, "message is existing  ${messageList}")
    ScaffoldFrameTop(contentFunction = {
        user?.let {
            showMessageList(
            messageList = messageList ,
            user = it,
            context =context ,
            viewModel = MessageListViewModel(),
            navController = navController
            )
        }


    },  bottomFunction= { user?.let { AddMessage(user = it, viewModel = MessageListViewModel()) } }, topbarFunction = {user?.let { Top(
        user = it
    ) }})

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
        Text(text = "new chat")

    }
}
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun showMessageList(messageList: List<Message>, user:User, context: Context, viewModel: MessageListViewModel, navController: NavHostController) {

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
                        items = messageList
                    ) { message ->

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
                            val currentSent = viewModel.getUserbyUid(message.sent)?.userName
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
                                    Text(
                                        text = "Sent From: $currentSent",
                                        style = TextStyle(
                                            fontFamily = FontFamily.Serif, fontSize = 15.sp,
                                            fontWeight = FontWeight.Light, color = Color.DarkGray
                                        )
                                    )
                                }, icon = {

                                })
                        }
                    }


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
fun Top(user: User){
    Text(text = "${user.userName}", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))
}


@Composable
fun AddMessage(user:User,viewModel: MessageListViewModel) {
    Box(
        modifier = Modifier.background(Color.White).padding(bottom = 10.dp)

        ) {

        var message = remember { mutableStateOf(TextFieldValue()) }



//
//        Text(text = "${user.userName}", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier.height(20.dp).padding(10.dp))
        OutlinedTextField(
            value = message.value,
            onValueChange = {message.value=it},
            label = {
                Text(
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
                        viewModel.updateMessage(message = message.value.text, user.uid);message.value=
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

