package com.example.chatroom.Data.service.impl

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.User
import com.example.chatroom.Data.service.AccountService
import com.example.chatroom.Data.service.FriendService
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class FriendServicelmpl
@Inject constructor(private val userRef: Query, private val auth:AccountService,

):FriendService{



    override suspend fun getFriendList(userMutableLiveData:MutableLiveData<List<User>>, friendMapMutableLiveData:MutableLiveData<Map<String,User>>) {
        val userListener:ValueEventListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userMap = emptyMap<String,User>().toMutableMap()
                val currentUserList: ArrayList<User> = ArrayList()
                dataSnapshot.children.forEach { child ->

                    val user: User? = child.getValue<User>()
                    if (user != null) {

                        currentUserList.add(0,user)
                        Log.w(ContentValues.TAG, "user list: ${currentUserList}")
                        userMap[user.uid]= user
                        Log.w(ContentValues.TAG, "user map: ${userMap}")
                    }

                }
                userMutableLiveData.postValue(currentUserList)
                friendMapMutableLiveData.postValue((userMap))

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        userRef.addValueEventListener(userListener)
    }






    override suspend fun updateList(target: String, targetUserUid: String): List<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllForUser(userId: String) {
        TODO("Not yet implemented")
    }
}



