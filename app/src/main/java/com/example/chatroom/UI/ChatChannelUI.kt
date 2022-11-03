package com.example.chatroom.UI


import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.Data.ChatChannel
import com.example.chatroom.ViewModel.Data.User
import com.example.chatroom.ViewModel.MessageListViewModel

@Composable
//fun FriendLsit(navController: NavHostController, viewModel: AddFriendListViewModel)



fun chatChannelPage(navController: NavHostController, viewModel: MessageListViewModel, channelListLiveData: MutableLiveData<List<ChatChannel>>, userLiveData: MutableLiveData<User?>,context:Context)
{







    // Here we access the live data object and convert it to a form that Jetpack Compose
    // understands using the observeAsState method.

    // Reacting to state changes is the core behavior of Compose. We use the state composable
    // that is used for holding a state value in this composable for representing the current
    // value of the selectedIndex. Any composable that reads the value of counter will be recomposed
    // any time the value changes. This ensures that only the composables that depend on this
    // will be redraw while the rest remain unchanged. This ensures efficiency and is a
    // performance optimization. It is inspired from existing frameworks like React.
    val channellist by channelListLiveData.observeAsState(initial = emptyList())
    val user by userLiveData.observeAsState(initial = User())
    // Since Jetpack Compose uses the declarative way of programming, we can easily decide what
    // needs to shows vs hidden based on which branch of code is being executed. In this example,
    // if the personList returned by the live data is empty, we want to show a loading indicator,
    // otherwise we want show the appropriate list. So we run the appropriate composable based on
    // the branch of code executed and that takes care of rendering the right views.
    if (channellist.isEmpty()) {
        channnelLiveDataLoadingComponent()
    } else {
        user?.let { ScaffoldFrame(contentFunction = { showChannelList(channellist, it,context,viewModel,navController) }, navController = navController) }
    }




}




@Composable
fun channnelLiveDataLoadingComponent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // A pre-defined composable that's capable of rendering a circular progress indicator. It
        // honors the Material Design specification.
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally))
    }
}




@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun showChannelList(channelList: List<ChatChannel>,user:User,context: Context,viewModel: MessageListViewModel,navController: NavHostController) {
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
//                    stickyHeader {
//                        Topbar(viewModel,navController)
//
//                    }
                    items(
                        items = channelList, itemContent = { channel ->
                            val currentRecevier = viewModel.getUserbyUid(viewModel.showChannelName(channel.members))?.userName
                            Card(
                                shape = RoundedCornerShape(4.dp),
                                backgroundColor = White,
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .constrainAs(userItem) {
//                                        start.linkTo(parent.start, 5.dp)
//                                        end.linkTo(parent.end, 5.dp)
                                    }
                                    .clickable {
                                        ToastUtil.showToast(
                                            context,
                                            "the card is clicked ${channel.channelUid}"
                                        )
                                        navController.navigate(Routes.ChatView.route)
                                    }
                                    .padding(8.dp)


                            ) {

                                ListItem(

                                    text = {

                                        Text(
                                            text = "$currentRecevier",

                                            style = TextStyle(
                                                fontFamily = FontFamily.Serif, fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold
                                            )

                                        )
                                    },secondaryText = {
                                        Text(
                                            text = "Message ${viewModel.showFirstMessage(channel.messaesList)}",
                                            style = TextStyle(
                                                fontFamily = FontFamily.Serif, fontSize = 15.sp,
                                                fontWeight = FontWeight.Light, color = androidx.compose.ui.graphics.Color.DarkGray
                                            )
                                        )
                                    }

                                    ,icon = {

                                    })
                            }
                        })



                }
            }


        }
    }
}

//
//@Composable
//fun Title(title: String) {
//    Text(
//        text = title,
//        fontSize = 30.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier.fillMaxHeight(0.5f)
//    )
//}
//
//// Different set of buttons in this page
//@Composable
//fun Buttons(title: String, onClick: () -> Unit, backgroundColor: Color) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = backgroundColor,
//            contentColor = Color.White
//        ),
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(0),
//    ) {
//        Text(
//            text = title
//        )
//    }
//}
//
//@Composable
//fun Appbar(title: String, action: () -> Unit) {
//    TopAppBar(
//        title = {
//            Text(text = title)
//        },
//        navigationIcon = {
//            IconButton(
//                onClick = action
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.ArrowBack,
//                    contentDescription = "Back button"
//                )
//            }
//        }
//    )
//}
//
//@Composable
//fun TextFormField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType, visualTransformation: VisualTransformation) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = {
//            Text(
//                label
//            )
//        },
//        maxLines = 1,
//        modifier = Modifier
//            .padding(horizontal = 20.dp, vertical = 5.dp)
//            .fillMaxWidth(),
//        keyboardOptions = KeyboardOptions(
//            keyboardType = keyboardType
//        ),
//        singleLine = true,
//        visualTransformation = visualTransformation
//    )
//}
//
//@Composable
//fun SingleMessage(message: String, isCurrentUser: Boolean) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = if (isCurrentUser) MaterialTheme.colors.primary else Color.White
//    ) {
//        Text(
//            text = message,
//            textAlign =
//            if (isCurrentUser)
//                TextAlign.End
//            else
//                TextAlign.Start,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            color = if (!isCurrentUser) MaterialTheme.colors.primary else Color.White
//        )
//    }
//}