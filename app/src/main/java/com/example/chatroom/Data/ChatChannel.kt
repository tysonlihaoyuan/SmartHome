package com.example.chatroom.Data

import com.example.chatroom.Data.Message

data class ChatChannel (
    val channelUid :String = "",
    val messagesList: MutableList<Message> = mutableListOf(),
    val channelCreatedTimeStamp: HashMap<String?, Any?> =HashMap<String?, Any?>(),
    // store members' UID
    val memberList:MutableList<String> = mutableListOf()

        )