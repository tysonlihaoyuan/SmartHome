package com.example.chatroom.Data.service.impl

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.Data.Message
import com.example.chatroom.Data.service.ChatService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class ChatServiceImpl @Inject constructor(
    private val channelRef: DatabaseReference, private val auth: FirebaseAuth,
) : ChatService {
    override suspend fun getMessageList(
        userUid: String,
        messageListMutableLiveData: MutableLiveData<List<Message>>
    ) {

        val messageListener: ValueEventListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var currentMessageList: ArrayList<Message> = ArrayList()
                dataSnapshot.children.forEach { child ->

                    val channel: ChatChannel? = child.getValue<ChatChannel>()
                    val memberlist = channel?.memberList
                    if (channel != null) {
                        Log.w(ContentValues.TAG, "channel is found ${channel.channelUid}}")
                        if (memberlist != null) {
                            Log.w(
                                ContentValues.TAG,
                                "memberlist[0] is  ${memberlist.contains(auth.uid.toString())} and ${auth.uid.toString()}"
                            )
                            Log.w(
                                ContentValues.TAG,
                                "memberlist[1] is  ${memberlist.contains(userUid)} and $userUid"
                            )
                            if (memberlist.contains(auth.uid.toString()) == true and memberlist.contains(
                                    userUid
                                ) == true
                            ) {
                                currentMessageList = channel.messagesList as ArrayList<Message>
                                Log.w(ContentValues.TAG, "Message list: ${currentMessageList}")
                            } else {
                                Log.w(ContentValues.TAG, "memberlist is missing")
                            }
                        } else {
                            Log.w(ContentValues.TAG, "memberlist is null")
                        }
                    }
                }
                messageListMutableLiveData.postValue(currentMessageList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        channelRef.addValueEventListener(messageListener)

    }

    override suspend fun updateMessage(
        message: String,
        receiver: String,
        messageListMutableLiveData: MutableLiveData<List<Message>>
    ) {

        var messageList: MutableList<Message> =
            messageListMutableLiveData.value as MutableList<Message>
        val sender = auth.uid.toString()
        val timestamp = HashMap<String?, Any?>()
        timestamp["timestamp"] = ServerValue.TIMESTAMP
        timestamp.put("timestamp", ServerValue.TIMESTAMP);
        val newMessage = Message(sender, receiver, message, timestamp)
        messageList.add(newMessage)
        messageListMutableLiveData.postValue(messageList)

        channelRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val currentMessageList = messageList
                Log.w(ContentValues.TAG, "updated message list: ${currentMessageList}")
                dataSnapshot.children.forEach { child ->

                    val channel: ChatChannel? = child.getValue<ChatChannel>()
                    val memberlist = channel?.memberList
                    if (channel != null) {
                        Log.w(ContentValues.TAG, "channel is found ${channel.channelUid}}")

                        if (memberlist != null) {
                            Log.w(ContentValues.TAG, "member is found ${auth.uid}},${memberlist.contains(auth.uid)}")
                            Log.w(ContentValues.TAG, "member is found ${receiver},${memberlist.contains(receiver)}")
                            if (memberlist.contains(auth.uid) and memberlist.contains(receiver)) {
                                Log.w(ContentValues.TAG, "member inner is found ${memberlist[0]}}")

                                child.child("messagesList").ref.setValue(currentMessageList)
                                    .addOnCompleteListener {
                                        Log.w(
                                            ContentValues.TAG,
                                            "updated message list: ${currentMessageList}"
                                        )
                                    }.addOnFailureListener {
                                    Log.w(ContentValues.TAG, "cannot update message list")
                                }

                            }
                        }
                    }
                }
                messageListMutableLiveData.postValue(currentMessageList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        })


    }


}




