package com.example.chatroom.ViewModel.Data
import com.google.firebase.Timestamp
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class User(
    val userName: String = "",
    val userStatus: String = "",
    val useremail: String = "",
    val userPassword: String = "",
    val uid: String = "",
//    val lastUpdate: Date = Timestamp.now().toDate(),

    val lastUpdate: HashMap<String?, Any?> =HashMap<String?, Any?>(),
    val registrationTokens: MutableList<String> = mutableListOf(),
    val subscribedChatRoomUID: MutableList<String> = mutableListOf(),
    val friendsUID: MutableList<String> = mutableListOf(),
//    val lastUpdate: Long = 0,
//    @ServerTimestamp
//
//    var lastUpdate: Date?= null
) {





}