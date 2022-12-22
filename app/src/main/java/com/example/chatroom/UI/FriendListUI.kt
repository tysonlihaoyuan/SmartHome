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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.FriendListViewModel
import com.example.chatroom.ViewModel.Data.User
import com.example.chatroom.ViewModel.LoginViewModel

import androidx.constraintlayout.compose.ConstraintLayout
import com.example.chatroom.Activities.Routes
import com.example.chatroom.ViewModel.ChannelListViewModel

@Composable
//fun FriendLsit(navController: NavHostController, viewModel: FriendListViewModel)
fun FriendLsit(navController: NavHostController, friendlistviewModel: FriendListViewModel,userviewModel: ChannelListViewModel,userLiveData: MutableLiveData<User?>,personListLiveData: MutableLiveData<List<User>>, context:Context)
{







        // Here we access the live data object and convert it to a form that Jetpack Compose
        // understands using the observeAsState method.

        // Reacting to state changes is the core behavior of Compose. We use the state composable
        // that is used for holding a state value in this composable for representing the current
        // value of the selectedIndex. Any composable that reads the value of counter will be recomposed
        // any time the value changes. This ensures that only the composables that depend on this
        // will be redraw while the rest remain unchanged. This ensures efficiency and is a
        // performance optimization. It is inspired from existing frameworks like React.
        val personList by personListLiveData.observeAsState(initial = emptyList())
        val user by userLiveData.observeAsState(initial = User())
        friendlistviewModel.loadUserList()
        friendlistviewModel.loadChannelList()

        // Since Jetpack Compose uses the declarative way of programming, we can easily decide what
        // needs to shows vs hidden based on which branch of code is being executed. In this example,
        // if the personList returned by the live data is empty, we want to show a loading indicator,
        // otherwise we want show the appropriate list. So we run the appropriate composable based on
        // the branch of code executed and that takes care of rendering the right views.
        if (personList.isEmpty()) {
            LiveDataLoadingComponent()
        } else {
            ScaffoldFrame(contentFunction = { showFriendList(personList,context,friendlistviewModel,userviewModel,navController) }, navController = navController)
        }




    }




@Composable
fun LiveDataLoadingComponent() {
    // Column is a composable that places its children in a vertical sequence. You
    // can think of it similar to a LinearLayout with the vertical orientation.
    // In addition we also pass a few modifiers to it.

    // You can think of Modifiers as implementations of the decorators pattern that are
    // used to modify the composable that its applied to. In this example, we configure the
    // Column composable to occupy the entire available width and height using
    // Modifier.fillMaxSize().
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
fun showFriendList(
    personList: List<User>,
    context: Context,
    friendlistviewModel: FriendListViewModel,
    userviewModel: ChannelListViewModel,
    navController: NavHostController) {
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
//
                    items(
                        items = personList, itemContent = { person ->

                            Card(
                                shape = RoundedCornerShape(4.dp),
                                backgroundColor = White,
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .constrainAs(userItem) {
//
                                    }
                                    .clickable {

                                        val sucessCreatedChannel = friendlistviewModel.mAllChannelMap.value?.let {
                                            friendlistviewModel.checkExistingChannel(person.uid,
                                                it
                                            )
                                        }
                                        //Todo: complete direct to target chat channel
                                        if (sucessCreatedChannel == false){
                                            friendlistviewModel.mAllChannelMap.value?.let {
                                                friendlistviewModel.createChannel(person.uid,
                                                    it
                                                )
                                            }
                                        }else{
                                            userviewModel.mUser.postValue(person)
                                            navController.navigate(Routes.ChatView.route)
//                                            ToastUtil.showToast(
//                                            context,
//                                            "the card is clicked ${person.userName}"
//                                        )
                                        }

                                    }
                                    .padding(8.dp)


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
                                            text = "Email: ${person.useremail}",
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







