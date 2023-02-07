package com.example.chatroom.screens.FriendList


import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatroom.Data.User
import com.example.chatroom.screens.ChatChannel.ChatChannelViewModel
import com.example.chatroom.screens.ScaffoldFrame

@Composable
fun FriendListPage(channelViewModel: ChatChannelViewModel, navController:NavHostController){
//    Log.d(ContentValues.TAG, "FriendList is $channelViewModel ")
    val friendList by channelViewModel.friendListLiveData.observeAsState(initial = emptyList())
    val channalMap  by channelViewModel.channelMapLiveData.observeAsState(initial = emptyMap())
    channelViewModel.loadFriendList()
    channelViewModel.loadChannelList()
    ScaffoldFrame(contentFunction = { showFriendList(friendList,channelViewModel,navController = navController) }, navController = navController)
    
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showFriendList(
    friendList: List<User>,
    channelViewModel: ChatChannelViewModel,

    navController: NavHostController
) {
    Surface(color = White) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val ( topicText,userList,userListContainer,userItem) = createRefs()
//
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
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
                                items = friendList, itemContent = { person ->

                                Card(
                                    shape = RoundedCornerShape(4.dp),
                                    backgroundColor = White,
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .constrainAs(userItem) {
                                            //
                                        }
                                        .clickable {
                                            channelViewModel.onClickCreateChannel(person.uid)
                                        }




                                ) {

                                    ListItem(

                                        text = {

                                            Text(
                                                text = person.userName,

                                                style = TextStyle(
                                                    fontFamily = FontFamily.Serif, fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        },secondaryText = {
                                            Text(
                                                text = "Email: ${person.userEmail}",
                                                style = TextStyle(
                                                    fontFamily = FontFamily.Serif, fontSize = 15.sp,
                                                    fontWeight = FontWeight.Light, color = Color.DarkGray
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
