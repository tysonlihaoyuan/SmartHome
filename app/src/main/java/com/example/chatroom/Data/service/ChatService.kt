package com.example.chatroom.Data.service

import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.Message

interface ChatService {

suspend fun getMessageList(userUid:String,messageListMutableLiveData: MutableLiveData<List<Message>>)

suspend fun updateMessage(message: String, receiver: String,messageListMutableLiveData: MutableLiveData<List<Message>>)
}