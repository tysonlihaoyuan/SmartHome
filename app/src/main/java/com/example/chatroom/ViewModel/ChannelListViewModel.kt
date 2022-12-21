package com.example.chatroom.ViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatroom.Utility.FirebaseFactory
import com.example.chatroom.ViewModel.Data.ChatChannel
import com.example.chatroom.ViewModel.Data.Message
import com.example.chatroom.ViewModel.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChannelListViewModel(var firebaseFactory : FirebaseFactory):ViewModel() {

    private lateinit var userRef: DatabaseReference
    private var currentUser: FirebaseAuth
    val database = Firebase.database
    private lateinit var messageListener: ValueEventListener
    private lateinit var userListener: ValueEventListener
    val mUserList: MutableLiveData<List<User>> = MutableLiveData()
    val mUser :MutableLiveData<User?> = MutableLiveData()
    val mMessageList: MutableLiveData<List<Message>> = MutableLiveData()

    val mChannelLsit:MutableLiveData<List<ChatChannel>> =MutableLiveData()

    var userList: ArrayList<String> = ArrayList()
    val messageList: ArrayList<Message> = ArrayList()
    private var testChannel: ChatChannel= ChatChannel()

    init {
        currentUser = Firebase.auth
        userRef = database.getReference("Users")

        getCurrentUserChannel()
        loadUserList()



    }

    fun showChannelName(memberlist:MutableList<String>): String {

        var result = ""
        if (!memberlist.isEmpty()){
            for (member in memberlist){
                if(member != currentUser.uid){
                    result=member
                }
            }
        }else{
            result="Empty"
        }

       return result




        }
    fun showFirstMessage(messagelist:MutableList<Message>):String{

        var result = ""
        result = if (messagelist.isNotEmpty()){
            messagelist.last().message

        }else{
            "Empty "
        }
        return result


    }
    private fun loadUserList() {

        userListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // New data at this path. This method will be called after every change in the
                // data at this path or a subpath.

//                Log.d(TAG, "number of users ${dataSnapshot.childrenCount}")
                val currentUserList: ArrayList<User> = ArrayList<User>()
                dataSnapshot.children.forEach { child ->

                    val user: User? = child.getValue<User>()
                    if (user != null) {

                        currentUserList.add(0,user)

                    }

                }
                mUserList.postValue(currentUserList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        userRef.addValueEventListener(userListener)
    }


    fun matchUserbyUid(userlist:List<User>,targetUserUid:String):User?{

        if (userlist.isNotEmpty()){
            for (user in userlist){
                if(user.uid == targetUserUid){
                    return user

                }
            }

        }
        return null

    }


    //update the message to the database
    fun updateMessage(message:String,receiver:String){
        val database =Firebase.database
        val messageRef = database.getReference("ChatRoom")

        messageRef.addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var currentChatRoomMemberList: MutableList<String>
                var finalMessageList: MutableList<Message> = mutableListOf()
                val sender = currentUser.uid.toString()
                val timestamp = HashMap<String?, Any?>()
                timestamp["timestamp"] = ServerValue.TIMESTAMP
                timestamp.put("timestamp", ServerValue.TIMESTAMP);
                val newMessage = Message(sender,receiver,message,timestamp)
                val currentChatChannel =  mutableListOf(sender,receiver)





                currentChatChannel.sort()
                snapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()

                    if (chatChannel != null) {
                        currentChatRoomMemberList = chatChannel.members
//
                        if (currentChatRoomMemberList.equals(currentChatChannel) ) {

                            finalMessageList=chatChannel.messaesList
                            finalMessageList.add(newMessage)
                            messageRef.child("${chatChannel.channelUid}").child("messaesList").setValue(finalMessageList).addOnSuccessListener {

                                Log.d(
                                ContentValues.TAG,
                                "sucessful updated message to the channel ${chatChannel.channelUid}"
                            ) }.addOnFailureListener {
                                Log.w(ContentValues.TAG, "messages:onCancelled: ${it.message}")
                            }

//
//                            )

                        }
                    }
                }
                mMessageList.postValue(finalMessageList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }

        })
//


    }

    fun getCurrentMessageHistoryTest(receiver: String): List<Message>? {
        val database =Firebase.database
        val sender = currentUser.uid.toString()
        val currentChatChannel =  mutableListOf(sender,receiver)
        currentChatChannel.sort()
        val messageRef = database.getReference("ChatRoom").equalTo("sender",)



        messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentChatRoomMemberList: MutableList<String>
                var finalMessageList: MutableList<Message> = mutableListOf()

                snapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()

                    if (chatChannel != null) {
                        currentChatRoomMemberList = chatChannel.members

                        if (currentChatRoomMemberList.equals(currentChatChannel) ) {

                            finalMessageList=chatChannel.messaesList
                            Log.d(
                                ContentValues.TAG,
                                "sucessful found message history $finalMessageList  "
                            )


//                            )

                        }
                    }
                }
                mMessageList.postValue(finalMessageList)


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        messageRef.addValueEventListener(messageListener)
//
        return mMessageList.value
    }

     fun getUserbyUid(userUid: String): User? {

         var targetUser: User?
         targetUser = firebaseFactory.getUserByUid(userUid)


         return targetUser
    }


    fun getCurrentMessageHistory(receiver: String): List<Message>? {
        val database =Firebase.database
        val messageRef = database.getReference("ChatRoom")
        messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentChatRoomMemberList: MutableList<String>
                var finalMessageList: MutableList<Message> = mutableListOf()
                val sender = currentUser.uid.toString()



                val currentChatChannel =  mutableListOf(sender,receiver)
                currentChatChannel.sort()
                snapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()

                    if (chatChannel != null) {
                        currentChatRoomMemberList = chatChannel.members

                        if (currentChatRoomMemberList.equals(currentChatChannel) ) {

                            finalMessageList=chatChannel.messaesList
                            Log.d(
                            ContentValues.TAG,
                            "sucessful found message history $finalMessageList  "
                        )


//                            )

                        }
                    }
                }
                mMessageList.postValue(finalMessageList)


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        messageRef.addValueEventListener(messageListener)
//
        return mMessageList.value
    }

    //get the current user's existing channel list
    fun getCurrentUserChannel(){
        val database = Firebase.database
        val messageRef = database.getReference("ChatRoom")
        val currentUserUid = currentUser.uid
        messageListener =object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var currentChatRoomMemberList: ArrayList<String>
                var finalChatRoomList:ArrayList<ChatChannel> =ArrayList()
                snapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()


                    if (chatChannel != null) {
                        currentChatRoomMemberList = chatChannel.members as ArrayList<String>

                        if (currentChatRoomMemberList.contains(currentUserUid)) {
                           finalChatRoomList.add(chatChannel)

                            Log.d(
                                ContentValues.TAG,
                                "sucessful Found target ${chatChannel.channelUid}"
                            )

                        }
                    }
                }
                mChannelLsit.postValue(finalChatRoomList)



            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
            }


        }

        messageRef.addValueEventListener(messageListener)

    }













}




