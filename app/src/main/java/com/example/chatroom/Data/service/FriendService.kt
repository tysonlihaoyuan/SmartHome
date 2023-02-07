package com.example.chatroom.Data.service


import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.User

interface FriendService {

//    val friendList:Flow<List<User>>


    suspend fun getFriendList(userMutableLiveData: MutableLiveData<List<User>>, friendMapMutableLiveData:MutableLiveData<Map<String,User>>)

//    suspend fun getUserById(uid:String): User?
    suspend fun updateList(target:String,targetUserUid:String):List<Any>
    suspend fun delete(taskId: String)
    suspend fun deleteAllForUser(userId: String)
}