package com.example.chatroom.screens.ChatChannel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.Data.Message
import com.example.chatroom.Data.User
import com.example.chatroom.Data.service.AccountService
import com.example.chatroom.Data.service.ChannelService
import com.example.chatroom.Data.service.FriendService
import com.example.chatroom.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatChannelViewModel @Inject constructor(
    private val channelService: ChannelService,
    private val friendService: FriendService,
    private val accountService: AccountService

): ViewModel(){

    val channelListLiveData: LiveData<List<ChatChannel>>
        get() = channelListMutableLiveData

    private val channelListMutableLiveData: MutableLiveData<List<ChatChannel>> = MutableLiveData()


    val channelMapLiveData: LiveData<Map<List<String>,String>>
        get() = channelMapMutableLiveData
    private val channelMapMutableLiveData: MutableLiveData<Map<List<String>,String>> = MutableLiveData()

    val friendListLiveData:LiveData<List<User>>
        get() = friendMutableListLiveData
    private val friendMutableListLiveData:MutableLiveData<List<User>> = MutableLiveData()

    val friendMapLiveData:LiveData<Map<String,User>>
    get() = friendMapMutableLiveData

    private val friendMapMutableLiveData: MutableLiveData<Map<String,User>> = MutableLiveData()

    val targetUserLiveData:LiveData<String>
        get() = targetUserMutableLiveData

    private val targetUserMutableLiveData: MutableLiveData<String> = MutableLiveData()




    fun loadFriendList(){
        viewModelScope.launch {
            friendService.getFriendList(friendMutableListLiveData,friendMapMutableLiveData)
            Log.d(ContentValues.TAG, "userList is ${friendListLiveData.value} is successful")
            Log.d(ContentValues.TAG, "userList is ${friendMapLiveData.value} is successful")

        }
    }


    fun DisplayChannelName(memberList: List<String>): String? {


        val channelName: String? = friendMapMutableLiveData.value?.get(channelService.displayChannelName(memberList))?.userName
        Log.d(ContentValues.TAG, "current channel name ${channelName} ")
        return channelName


    }

    fun getUserbyId(uid:String): User? {
        return friendMapMutableLiveData.value?.get(uid)

    }
    fun getTargetUserName(memberList: List<String>): String? {


        val channelUid: String? = channelService.displayChannelName(memberList)
        Log.d(ContentValues.TAG, "current channel name ${channelUid} ")
        return channelUid


    }




    fun DisplayFirstMessage(messageList: List<Message>):String{
        return channelService.displayFirstMessage(messageList)
    }



    fun loadChannelList(){
        viewModelScope.launch {
            channelService.getList(channelListMutableLiveData,channelMapMutableLiveData)
        }

    }
    fun onClickCreateChannel(targetUid:String){
        viewModelScope.launch {
            channelMapMutableLiveData.value?.let { channelService.createChannel(targetUid, it) }
        }
    }
    fun onClickSelectChannel(targetUid: String,navController: NavHostController){
        targetUserMutableLiveData.postValue(targetUid)
        navController.navigate(Routes.ChatView.withArgs(targetUid))

    }



}