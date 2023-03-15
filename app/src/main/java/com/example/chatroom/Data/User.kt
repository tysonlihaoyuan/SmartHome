package com.example.chatroom.Data

data class User(
    val userName: String = "",
    val userStatus: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val uid: String = "",
    val profilePictureUrl: String? = null,

    val lastUpdate: HashMap<String?, Any?> =HashMap(),
    val registrationTokens: MutableList<String> = mutableListOf(),
    val subscribedChatRoomUID: MutableList<String> = mutableListOf(),
    val friendsUID: List<String> = mutableListOf(),

    )