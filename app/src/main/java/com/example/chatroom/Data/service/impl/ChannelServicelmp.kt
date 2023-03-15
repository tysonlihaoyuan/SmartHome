package com.example.chatroom.Data.service.impl

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatroom.Data.ChatChannel
import com.example.chatroom.Data.Message
import com.example.chatroom.Data.service.ChannelService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChannelServicelmp@Inject constructor(private val auth:FirebaseAuth, private val channelRef: DatabaseReference) :ChannelService {

    override fun displayChannelName(memberList: List<String>): String {
        var result = ""
        if (!memberList.isEmpty())
        {
            for (member in memberList) {
                if (member != auth.uid) {
                    result = member
                }
            }
        } else
        {
            result = "Empty"
        }

        return result
}

    override  fun displayFirstMessage(messageList: List<Message>): String {

        var result = if (messageList.isNotEmpty()) {
            messageList.last().message

        } else {
            "Empty "
        }
        return result
    }



    override suspend fun getList(channelMutableLiveData: MutableLiveData<List<ChatChannel>>, channelMapMutableLiveData:MutableLiveData<Map<List<String>,String>>) {
        val channelListener: ValueEventListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val channelMap = emptyMap<List<String>,String>().toMutableMap()
                val currentChannelList: ArrayList<ChatChannel> = ArrayList()
                dataSnapshot.children.forEach { child ->

                    val channel: ChatChannel? = child.getValue<ChatChannel>()
                    val memberlist = channel?.memberList as ArrayList<String>
                    channelMap[memberlist]= channel.channelUid
                    if(memberlist.contains(auth.uid)){
                        currentChannelList.add(0,channel)
                        Log.w(ContentValues.TAG, "channel list: ${currentChannelList}")

                    }

                }
                channelMutableLiveData.postValue(currentChannelList)
                channelMapMutableLiveData.postValue(channelMap)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }
        channelRef.addValueEventListener(channelListener)
    }

    override suspend fun createChannel(toUserUid: String,channelMap : Map<List<String>,String>) {
        if(checkExistingChannel(toUserUid,channelMap)== false){
            val newChannelMembers: MutableList<String> =
                mutableListOf(auth.uid.toString(), toUserUid)
            newChannelMembers.sort()


            val newChannelUid = channelRef.push().key.toString()

            val newChannelMessageList: MutableList<Message> = mutableListOf<Message>()

//            val sender = auth.uid.toString()
//            val timestamp = HashMap<String?, Any?>()
//            timestamp["timestamp"] = ServerValue.TIMESTAMP
//            timestamp.put("timestamp", ServerValue.TIMESTAMP);
//            val newMessage = Message(sender, toUserUid, " ", timestamp)
//            newChannelMessageList.add(newMessage)


            val newChannelCreatedTimeStamp = HashMap<String?, Any?>()
            newChannelCreatedTimeStamp["timestamp"] = ServerValue.TIMESTAMP
            newChannelCreatedTimeStamp.put("timestamp", ServerValue.TIMESTAMP);
            val newChannel = ChatChannel(
                channelUid= newChannelUid,
                newChannelMessageList,
                newChannelCreatedTimeStamp,
                newChannelMembers
            )

            channelRef.child(newChannelUid).setValue(newChannel).await()
            Log.d(ContentValues.TAG, "chatChannel is created ${channelMap[newChannelMembers]}")

        }else{
            Log.d(ContentValues.TAG, "chatChannel is not created ")
        }




    }


    private fun checkExistingChannel(toUserUid: String,channelMap : Map<List<String>,String>):Boolean{

        val newChannelMembers: MutableList<String> = mutableListOf(auth.uid.toString(), toUserUid)
        newChannelMembers.sort()
        if (channelMap[newChannelMembers] != null){
            Log.d(ContentValues.TAG, "chatChannel is existing ${channelMap[newChannelMembers]}")
            return true
        }else {
            Log.d(ContentValues.TAG, "chatChannel is not existing ${channelMap[newChannelMembers]}")
            return false
        }
    }


}