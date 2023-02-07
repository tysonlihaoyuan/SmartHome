package com.example.chatroom.Data

data class Message(
    val sent: String = "",
    val receiver: String = "",
    val message: String ="",
    val messageCreatedTimeStamp: HashMap<String?, Any?> =HashMap(),
    val messageLastUpdate :HashMap<String?, Any?> =HashMap()
)
