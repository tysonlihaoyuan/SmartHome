package com.example.chatroom.screens.FriendList


import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


import androidx.navigation.NavHostController
import com.example.chatroom.Data.User
import com.example.chatroom.screens.ChatChannel.ChatChannelViewModel
import com.example.chatroom.screens.ScaffoldFrame
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


@Composable
fun FriendListPage(channelViewModel: ChatChannelViewModel, navController:NavHostController){
//    Log.d(ContentValues.TAG, "FriendList is $channelViewModel ")
    val friendList by channelViewModel.friendListLiveData.observeAsState(initial = emptyList())
    channelViewModel.loadFriendList()
    channelViewModel.loadChannelList()
    ScaffoldFrame(contentFunction = { showFriendList(friendList, channelViewModel) }, navController = navController)
    
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showFriendList(
    friendList: List<User>,
    channelViewModel: ChatChannelViewModel
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
                                            person.profilePictureUrl?.let { imageUrl ->
                                                // Look at the implementation of this composable in ImageActivity to learn
                                                // more about its implementation. It uses Picasso to load the imageUrl passed
                                                // to it.
                                                NetworkImageComponentPicasso(
                                                    url = imageUrl,
                                                    modifier = Modifier.width(60.dp).height(60.dp)
                                                )
                                            }

                                        })
                                }
                            })



                    }

                }


            }
    }
}


// We represent a Composable function by annotating it with the @Composable annotation. Composable
// functions can only be called from within the scope of other composable functions. We should
// think of composable functions to be similar to lego blocks - each composable function is in turn
// built up of smaller composable functions.
@Composable
fun NetworkImageComponentPicasso(
    url: String,
    modifier: Modifier = Modifier
) {
    // Source code inspired from - https://kotlinlang.slack.com/archives/CJLTWPH7S/p1573002081371500.
    // Made some minor changes to the code Leland posted.
    val sizeModifier = modifier
        .fillMaxWidth()
        .sizeIn(maxHeight = 200.dp)
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    // Sometimes we need to make changes to the state of the app. For those cases, Composes provides
    // some Effect API's which provide a way to perform side effects in a predictable manner.
    // DisposableEffect is one such side effect API that provides a mechanism to perform some
    // clean up actions if the key to the effect changes or if the composable leaves composition.
    DisposableEffect(url) {
        val picasso = Picasso.get()
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // TODO(lmr): we could use the drawable below
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageBitmap()
            }
        }
        picasso
            .load(url)
            .into(target)
        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }

    val theImage = image
    val theDrawable = drawable
    if (theImage != null) {
        // Column is a composable that places its children in a vertical sequence. You
        // can think of it similar to a LinearLayout with the vertical orientation.
        // In addition we also pass a few modifiers to it.

        // You can think of Modifiers as implementations of the decorators pattern that are
        // used to modify the composable that its applied to.
        Column(
            modifier = sizeModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image is a pre-defined composable that lays out and draws a given [ImageBitmap].
            Image(bitmap = theImage, contentDescription = null)
        }
    } else if (theDrawable != null) {
        // We use the Canvas composable that gives you access to a canvas that you can draw
        // into. We also pass it a modifier.
        Canvas(modifier = sizeModifier) {
            drawIntoCanvas { canvas ->
                theDrawable.draw(canvas.nativeCanvas)
            }
        }
    }
}



