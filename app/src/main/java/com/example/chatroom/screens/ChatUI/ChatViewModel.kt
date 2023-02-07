package com.example.chatroom.screens.ChatUI

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.Data.Message
import com.example.chatroom.Data.service.ChatService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel@Inject constructor(
    private val chatService :ChatService
):ViewModel() {
    val messageListLiveData: LiveData<List<Message>>
        get() = messageListMutableLiveData

    private val messageListMutableLiveData: MutableLiveData<List<Message>> = MutableLiveData()


    fun getMessageList(userId:String){
        viewModelScope.launch {
            chatService.getMessageList(userId,messageListMutableLiveData)
            Log.d(ContentValues.TAG, "messageList is ${messageListLiveData.value} ")
        }
    }
    fun updateMessage(message: String,receiver:String){
        viewModelScope.launch {
            chatService.updateMessage(message, receiver,messageListMutableLiveData)
            Log.d(ContentValues.TAG, "message after updated is ${messageListLiveData.value} ")
        }
    }


}