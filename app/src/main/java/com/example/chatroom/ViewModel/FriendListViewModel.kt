package com.example.chatroom.ViewModel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatroom.ViewModel.Data.ChatChannel
import com.example.chatroom.ViewModel.Data.Message
import com.example.chatroom.ViewModel.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class FriendListViewModel : ViewModel() {

    private lateinit var userListener: ValueEventListener
    val mUserList:MutableLiveData<List<User>> = MutableLiveData()
    val userList:ArrayList<User> = ArrayList()
    val database = Firebase.database
    private var userRef : Query = database.getReference("Users").orderByChild("lastUpdate/timestamp")
    private var currentUser: FirebaseAuth
    val mAllChannelMap:MutableLiveData<Map<List<String>,String>> =MutableLiveData()

    init{
        Log.d(TAG, "addFriendviwmodel is created")
        currentUser = Firebase.auth


    }


     fun loadUserList() {

        userListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val currentUserList: ArrayList<User> = ArrayList()
                dataSnapshot.children.forEach { child ->

                    val user: User? = child.getValue<User>()
                    if (user != null) {

                        currentUserList.add(0,user)
                        userList.add(0,user)
                        Log.w(TAG, "user list: ${currentUserList}")
                    }

                }
                mUserList.postValue(currentUserList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "messages:onCancelled: ${error.message}")
            }


        }

        userRef.addValueEventListener(userListener)
    }

     fun loadChannelList() {

        val messageRef = database.getReference("ChatRoom")

        messageRef.addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val channelMap = emptyMap<List<String>,String>().toMutableMap()
                snapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
                    val memberlist = chatChannel?.members as ArrayList<String>
                    if (chatChannel != null) {
                        channelMap[memberlist]= chatChannel.channelUid
                    }

                    Log.w(TAG, "get all Channel to map $channelMap")
                }

                mAllChannelMap.postValue(channelMap)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "messages:onCancelled: ${error.message}")
            }

        })
//
    }
    fun checkExistingChannel(toUserUid: String, channelMap : Map<List<String>,String>):Boolean{

        val newChannelMembers: MutableList<String> =
            mutableListOf(currentUser.uid.toString(), toUserUid)
        newChannelMembers.sort()
        if (channelMap[newChannelMembers] != null){
            Log.d(TAG, "chatChannel is existing ${channelMap[newChannelMembers]}")
            return true
        }else {
            Log.d(TAG, "chatChannel is not existing ${channelMap[newChannelMembers]}")
            return false
        }
    }
    fun createChannel(toUserUid: String, channelMap : Map<List<String>,String>){
        val newChannelMembers: MutableList<String> =
            mutableListOf(currentUser.uid.toString(), toUserUid)
        newChannelMembers.sort()

        val database = Firebase.database
        val messageRef = database.getReference("ChatRoom")
        val newChannelUid = messageRef.push().key.toString()
        val newChannelMessageList: MutableList<Message> = mutableListOf()
        val newChannelCreatedTimeStamp: HashMap<String?, Any?> = HashMap()
        val newChannel = ChatChannel(
            newChannelUid,
            newChannelMessageList,
            newChannelCreatedTimeStamp,
            newChannelMembers
        )

        messageRef.child(newChannelUid).setValue(newChannel).addOnCompleteListener() {
            Log.d(TAG, "sucessful add chatChannel ${newChannel.channelUid}")
        }.addOnFailureListener {
            Log.d(TAG, "cannot add chatChannel")
        }



    }



 fun updateUsertimestamp(){
        val database = Firebase.database
        val userRef = database.getReference("Users")
        var auth: FirebaseAuth =Firebase.auth
        val currentUid = auth.uid
        val map = HashMap<String?, Any?>()
        map["timestamp"] = ServerValue.TIMESTAMP
        map.put("timestamp", ServerValue.TIMESTAMP);

        userRef.child("${currentUid}").child("lastUpdate").setValue(map)

 }

    }




