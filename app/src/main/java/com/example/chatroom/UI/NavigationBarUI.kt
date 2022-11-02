package com.example.chatroom.UI

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.chatroom.Activities.Routes


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldFrame(contentFunction: @Composable () -> Unit,
                   navController: NavHostController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { Topbar( navController = navController ) },
        bottomBar = { BottomNavigationBar(navController) },

        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Routes.AddFriend.route)}){
            Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
        } },

    ){
        contentFunction()
    }
}




@Composable
fun Topbar( navController: NavHostController) {
    Surface(color = Color.White) {
        ConstraintLayout(
//            modifier = Modifier.fillMaxSize(),
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



        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(

        Routes.ChatChanel,
        Routes.FriendList,
        Routes.Home
    )
    BottomNavigation(
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icons.Default},
                label = { Text(text = item.route  ) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
