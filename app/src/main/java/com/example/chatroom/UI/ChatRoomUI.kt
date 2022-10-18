package com.example.chatroom.UI

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatroom.Activities.Routes
import com.example.chatroom.ViewModel.AddFriendListViewModel
import com.example.chatroom.ViewModel.Data.User
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Utility.ToastUtil
import com.example.chatroom.ViewModel.LoginViewModel



@Composable
//fun ChatRoomPage(navController: NavHostController, viewModel: AddFriendListViewModel)
fun ChatRoomPage(navController: NavHostController,viewModel: AddFriendListViewModel,loginViewModel: LoginViewModel,personListLiveData: MutableLiveData<List<User>>,context:Context)
{



    Column(
        modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()


        ){

            Button(
                onClick = { loginViewModel.logout(navController) },
                shape = RoundedCornerShape(50.dp),

                modifier = Modifier
                    .width(50.dp)
                    .height(35.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Back",textAlign = TextAlign.Center,)

            }

                Spacer(modifier = Modifier.width(40.dp))

                Text(text = "ChatRoom", style = typography.caption, fontSize = 20.sp)

            Spacer(modifier = Modifier.width(40.dp))

                Button(
                    onClick = {  /*TODO*/ },
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(35.dp)
                ) {
                        Text(text = "ADD",textAlign = TextAlign.Center,)

                }



        }


        Spacer(modifier = Modifier.height(20.dp))
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
            LiveDataComponentList(personList,context)
        }


  }


    }
//@Composable
//fun ChatRoomPage(navController: NavHostController,viewModel: AddFriendListViewModel,personListLiveData: MutableLiveData<List<User>>) {
//
//
//    // Here we access the live data object and convert it to a form that Jetpack Compose
//    // understands using the observeAsState method.
//
//    // Reacting to state changes is the core behavior of Compose. We use the state composable
//    // that is used for holding a state value in this composable for representing the current
//    // value of the selectedIndex. Any composable that reads the value of counter will be recomposed
//    // any time the value changes. This ensures that only the composables that depend on this
//    // will be redraw while the rest remain unchanged. This ensures efficiency and is a
//    // performance optimization. It is inspired from existing frameworks like React.
//    val personList by personListLiveData.observeAsState(initial = emptyList())
//    // Since Jetpack Compose uses the declarative way of programming, we can easily decide what
//    // needs to shows vs hidden based on which branch of code is being executed. In this example,
//    // if the personList returned by the live data is empty, we want to show a loading indicator,
//    // otherwise we want show the appropriate list. So we run the appropriate composable based on
//    // the branch of code executed and that takes care of rendering the right views.
//    if (personList.isEmpty()) {
//        LiveDataLoadingComponent()
//    } else {
//        LiveDataComponentList(personList)
//    }
//}



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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LiveDataComponentList(personList: List<User>,context: Context) {
    // LazyColumn is a vertically scrolling list that only composes and lays out the currently
    // visible items. This is very similar to what RecyclerView tries to do as it's more optimized
    // than the VerticalScroller.
    LazyColumn {
        items(
            items = personList, itemContent = { person ->
                // Card composable is a predefined composable that is meant to represent the
                // card surface as specified by the Material Design specification. We also
                // configure it to have rounded corners and apply a modifier.

                // You can think of Modifiers as implementations of the decorators pattern that are used to
                // modify the composable that its applied to. In this example, we assign a padding of
                // 16dp to the Card along with specifying it to occupy the entire available width.
                Card(
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = White,
                    modifier = Modifier.fillParentMaxWidth()
                        .clickable { ToastUtil.showToast(context, "the card is clicked ${person.userName}")}
                                        .padding(8.dp)


                ) {
                    // ListItem is a predefined composable that is a Material Design implementation of [list
                    // items](https://material.io/components/lists). This component can be used to achieve the
                    // list item templates existing in the spec
                    ListItem(

                        text = {
                        // The Text composable is pre-defined by the Compose UI library; you can use this
                        // composable to render text on the screen
                        Text(
                            text = person.userName,
                            style = TextStyle(
                                fontFamily = FontFamily.Serif, fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }



                    )
//                        , icon = {
//                        person.profilePictureUrl?.let { imageUrl ->
//                            // Look at the implementation of this composable in ImageActivity to learn
//                            // more about its implementation. It uses Picasso to load the imageUrl passed
//                            // to it.
//                            NetworkImageComponentPicasso(
//                                url = imageUrl,
//                                modifier = Modifier.width(60.dp).height(60.dp)
//                            )
//                        }
//                    })
                }
            })
    }
}


