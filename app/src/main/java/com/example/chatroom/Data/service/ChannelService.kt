package com.example.chatroom.Data.service

import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.Data.Message

interface ChannelService {

    suspend fun getList(chanelrMutableLiveData: MutableLiveData<List<ChatChannel>>, channelMapMutableLiveData:MutableLiveData<Map<List<String>,String>>)
    suspend fun createChannel(toUserUid: String,channelMap : Map<List<String>,String>)
    fun displayChannelName(memberList:List<String>): String
    fun displayFirstMessage(messageList:List<Message>):String


}