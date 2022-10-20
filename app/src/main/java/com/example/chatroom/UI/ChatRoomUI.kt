package com.example.chatroom.UI


import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.AddFriendListViewModel
import com.example.chatroom.ViewModel.Data.User
import com.example.chatroom.ViewModel.LoginViewModel

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material.Icon

import androidx.compose.material.icons.Icons

@Composable
//fun ChatRoomPage(navController: NavHostController, viewModel: AddFriendListViewModel)
fun ChatRoomPage(navController: NavHostController, viewModel: AddFriendListViewModel, loginViewModel: LoginViewModel, personListLiveData: MutableLiveData<List<User>>, context:Context)
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
        // Since Jetpack Compose uses the declarative way of programming, we can easily decide what
        // needs to shows vs hidden based on which branch of code is being executed. In this example,
        // if the personList returned by the live data is empty, we want to show a loading indicator,
        // otherwise we want show the appropriate list. So we run the appropriate composable based on
        // the branch of code executed and that takes care of rendering the right views.
        if (personList.isEmpty()) {
            LiveDataLoadingComponent()
        } else {
            showFriendList(personList,context,viewModel)
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

@Composable
fun topbar(viewModel: AddFriendListViewModel) {
    Surface(color = Color.White) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (addFriendButton, topicText) = createRefs()
            Text(
                text = "ChatRoom",
                color = Color.Black,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .constrainAs(topicText) {
                        start.linkTo(parent.start, 20.dp)
//                        top.linkTo(parent.top, 10.dp)
                    }

            )

            Button(
                onClick = { viewModel.updateUsertimestamp()},
                modifier = Modifier
                    .constrainAs(addFriendButton) {
                        end.linkTo(parent.end, 10.dp)
//                        top.linkTo(parent.top, 10.dp)

                    }
                    .width(50.dp)
                    .height(35.dp)

            ) {
                Text(text = "+")
            }

        }
    }
}



@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun showFriendList(personList: List<User>,context: Context,viewModel: AddFriendListViewModel) {
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
                    stickyHeader {
                        topbar(viewModel)

                    }
                    items(
                        items = personList, itemContent = { person ->

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
                                            "the card is clicked ${person.userName}"
                                        )
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





