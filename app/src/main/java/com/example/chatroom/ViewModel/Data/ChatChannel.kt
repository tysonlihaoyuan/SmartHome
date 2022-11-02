package com.example.chatroom.ViewModel.Data

import androidx.lifecycle.MutableLiveData

class ChatChannel (
    val channelUid :String = "",
    val messaesList: MutableList<Message> = mutableListOf(),
    val channelCreatedTimeStamp: HashMap<String?, Any?> =HashMap<String?, Any?>(),
    // store members' UID
    val members:MutableList<String> = mutableListOf()
        ){


}