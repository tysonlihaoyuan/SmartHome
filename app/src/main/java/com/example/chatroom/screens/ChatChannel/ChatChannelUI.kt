package com.example.chatroom.screens.ChatChannel

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.screens.ScaffoldFrame

@Composable
fun ChatChannelPage(
    navController: NavHostController,
    viewModel: ChatChannelViewModel = hiltViewModel()
) {
    val channalList by viewModel.channelListLiveData.observeAsState(initial = emptyList())
//    viewModel.loadFriendList()
//    viewModel.loadChannelList()
    ScaffoldFrame(contentFunction = {
        showChannelList(
            channalList,
            viewModel,
            navController = navController
        )
    }, navController = navController)


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showChannelList(
    channelList: List<ChatChannel>,
    viewModel: ChatChannelViewModel,
    navController: NavHostController
) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (topicText, userList, userListContainer, userItem) = createRefs()
//
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            ConstraintLayout(Modifier.constrainAs(userListContainer) {
//

            }) {

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth()
//                        .verticalScroll(state = scrollState)
                        .constrainAs(userList) {
                            top.linkTo(parent.bottom, 10.dp)
                        }) {
                    items(
                        items = channelList, itemContent = { channel ->
                            val Channelname = viewModel.DisplayChannelName(channel.memberList)
                            val ChannelUserUid = viewModel.getTargetUserName(channel.memberList)
                            Card(
                                shape = RoundedCornerShape(4.dp),
                                backgroundColor = Color.White,
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .constrainAs(userItem) {
                                        //
                                    }
                                    .clickable {
                                        if (ChannelUserUid != null) {
                                            viewModel.onClickSelectChannel(
                                                ChannelUserUid,
                                                navController
                                            )
                                        }
                                    }


                            ) {

                                ListItem(

                                    text = {


                                        if (Channelname != null) {
                                            Text(
                                                text = Channelname,

                                                style = TextStyle(
                                                    fontFamily = FontFamily.Serif, fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }

                                    }, secondaryText = {
                                        Text(
                                            text = viewModel.DisplayFirstMessage(channel.messagesList),
                                            style = TextStyle(
                                                fontFamily = FontFamily.Serif,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
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