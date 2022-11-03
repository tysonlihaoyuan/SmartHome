package com.example.chatroom.ViewModel

import android.content.ContentValues
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

class MessageListViewModel:ViewModel() {

    private lateinit var userRef: DatabaseReference
    private lateinit var currentUser: FirebaseAuth
    private lateinit var messageListener: ValueEventListener
    val mUserList: MutableLiveData<List<User>> = MutableLiveData()
    val mUser :MutableLiveData<User?> = MutableLiveData()
    val mMessageList: MutableLiveData<List<Message>> = MutableLiveData()
    val mChannelLsit:MutableLiveData<List<ChatChannel>> =MutableLiveData()
    var userList: ArrayList<String> = ArrayList()
    val messageList: ArrayList<Message> = ArrayList()
    private var testChannel: ChatChannel= ChatChannel()

    init {
        currentUser = Firebase.auth

        getUserbyUid(currentUser.uid.toString())
        getCurrentUserChannel()

//        testgetChannel()
//        createChannel("3EDrhJfEpvW2ez6K8UJRVbiTyVp2")

//        viewModelScope.launch {
//
//            getChannelTest("3EDrhJfEpvW2ez6K8UJRVbiTyVp2")?.let { onResult(it) }
//            Log.d(ContentValues.TAG, "init--testChannel UID ${testChannel.channelUid}")
//        }
//        getChannel("3EDrhJfEpvW2ez6K8UJRVbiTyVp2", mycallback = object : MyCallback {
//            override fun onCallback(value: ChatChannel?) {
//                if (value != null) {
//                    testChannel=value
////                    Log.d(ContentValues.TAG, "callback call${testChannel.channelUid}")
//                };
//            }
//        })
//        Log.d(ContentValues.TAG, "callback call${testChannel.channelUid}")

    }
// fun testgetChannel(){
//     val testChannel= getChannel("3EDrhJfEpvW2ez6K8UJRVbiTyVp2", myCallback = object : MyCallback {
//            override fun onCallback(value: ChatChannel?) {
//                if (value != null) {
//                    testChannel=value
////                    Log.d(ContentValues.TAG, "callback call${testChannel.channelUid}")
//                };
//            }
//        })
//     Log.d(
//         ContentValues.TAG,
//         "sucessful Found targetChanel in test ${testChannel.channelUid}"
//     )
//     val testUser = getUserbyUid("3EDrhJfEpvW2ez6K8UJRVbiTyVp2")
//     Log.d(
//         ContentValues.TAG,
//         "sucessful Found targetChanel in test ${testUser.useremail}"
//     )
//
//}
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
        if (!messagelist.isEmpty()){
            result =messagelist.first().message

        }else{
            result="Empty "
        }
        return result


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
//                        Log.d(
//                            ContentValues.TAG,
//                            "sucessful found currentChatRoomMemberList ${currentChatRoomMemberList.get(0)} and ${currentChatRoomMemberList.get(1)} "
//                        )
//                        Log.d(
//                            ContentValues.TAG,
//                            "sucessful found currentChatChannel ${currentChatChannel.get(0)} and ${currentChatChannel.get(1)} "
//                        )
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

//                            Log.d(
//                                ContentValues.TAG,
//                                "sucessful updated message to the channel ${chatChannel.channelUid}"
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
//        messageListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var currentChatRoomMemberList: MutableList<String>
//                var finalMessageList: MutableList<Message> = mutableListOf()
//                val sender = currentUser.uid.toString()
//                val timestamp = HashMap<String?, Any?>()
//                timestamp["timestamp"] = ServerValue.TIMESTAMP
//                timestamp.put("timestamp", ServerValue.TIMESTAMP);
//                val newMessage = Message(sender,receiver,message,timestamp)
//                val currentChatChannel =  mutableListOf(sender,receiver)
//                currentChatChannel.sort()
//                snapshot.children.forEach { child ->
//                    // Extract Message object from the DataSnapshot
//                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
//
//                    if (chatChannel != null) {
//                        currentChatRoomMemberList = chatChannel.members
////                        Log.d(
////                            ContentValues.TAG,
////                            "sucessful found currentChatRoomMemberList ${currentChatRoomMemberList.get(0)} and ${currentChatRoomMemberList.get(1)} "
////                        )
////                        Log.d(
////                            ContentValues.TAG,
////                            "sucessful found currentChatChannel ${currentChatChannel.get(0)} and ${currentChatChannel.get(1)} "
////                        )
//                        if (currentChatRoomMemberList.equals(currentChatChannel) ) {
//
//                            finalMessageList=chatChannel.messaesList
//                            finalMessageList.add(newMessage)
//                            messageRef.child("${chatChannel.channelUid}").child("messaesList").setValue(finalMessageList).addOnSuccessListener {  Log.d(
//                                ContentValues.TAG,
//                                "sucessful updated message to the channel ${chatChannel.channelUid}"
//                            ) }.addOnFailureListener {
//                                Log.w(ContentValues.TAG, "messages:onCancelled: ${it.message}")
//                            }
//
////                            Log.d(
////                                ContentValues.TAG,
////                                "sucessful updated message to the channel ${chatChannel.channelUid}"
////                            )
//
//                        }
//                    }
//                }
//                mMessageList.postValue(finalMessageList)
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
//            }
//
//
//        }
//
//        messageRef.addValueEventListener(messageListener)


    }


//    fun getCurrentMessageHistory(receiver:String){
//        val database =Firebase.database
//        val messageRef = database.getReference("ChatRoom")
//        messageListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var currentChatRoomMemberList: MutableList<String>
//                var finalMessageList: MutableList<Message> = mutableListOf()
//                val sender = currentUser.uid.toString()
//                val timestamp = HashMap<String?, Any?>()
//                timestamp["timestamp"] = ServerValue.TIMESTAMP
//                timestamp.put("timestamp", ServerValue.TIMESTAMP);
//                val newMessage = Message(sender,receiver,message,timestamp)
//                val currentChatChannel =  mutableListOf(sender,receiver)
//                currentChatChannel.sort()
//                snapshot.children.forEach { child ->
//                    // Extract Message object from the DataSnapshot
//                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
//
//                    if (chatChannel != null) {
//                        currentChatRoomMemberList = chatChannel.members
////                        Log.d(
////                            ContentValues.TAG,
////                            "sucessful found currentChatRoomMemberList ${currentChatRoomMemberList.get(0)} and ${currentChatRoomMemberList.get(1)} "
////                        )
////                        Log.d(
////                            ContentValues.TAG,
////                            "sucessful found currentChatChannel ${currentChatChannel.get(0)} and ${currentChatChannel.get(1)} "
////                        )
//                        if (currentChatRoomMemberList.equals(currentChatChannel) ) {
//
//                            finalMessageList=chatChannel.messaesList
//                            finalMessageList.add(newMessage)
//                            messageRef.child("${chatChannel.channelUid}").child("messaesList").setValue(finalMessageList).addOnSuccessListener {  Log.d(
//                                ContentValues.TAG,
//                                "sucessful updated message to the channel ${chatChannel.channelUid}"
//                            ) }.addOnFailureListener {
//                                Log.w(ContentValues.TAG, "messages:onCancelled: ${it.message}")
//                            }
//
////                            Log.d(
////                                ContentValues.TAG,
////                                "sucessful updated message to the channel ${chatChannel.channelUid}"
////                            )
//
//                        }
//                    }
//                }
//                mMessageList.postValue(finalMessageList)
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
//            }
//
//
//        }
//
//        messageRef.addValueEventListener(messageListener)
//    }

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

//
//    fun findChannel(toUserUid: String):ChatChannel {
//        val database = Firebase.database
//        var targetChanel: ChatChannel = ChatChannel()
//        val currentUserUid = currentUser.uid.toString()
//        userList = arrayListOf(currentUserUid, toUserUid)
//        userList.sort()
//        val messageRef = database.getReference("ChatRoom")
//            messageRef.addListenerForSingleValueEvent(object : ValueEventListener {
//
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    var innerChatChannel:ChatChannel = ChatChannel()
//                    var currentChatRoomMemberList: ArrayList<String>
//                    snapshot.children.forEach { child ->
//                        // Extract Message object from the DataSnapshot
//                        val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
//
//
//                        if (chatChannel != null) {
//                            currentChatRoomMemberList = chatChannel.members as ArrayList<String>
//                            currentChatRoomMemberList.sort()
//                            if (userList.equals(currentChatRoomMemberList)) {
//                                innerChatChannel = chatChannel
//
//                                Log.d(
//                                    ContentValues.TAG,
//                                    "sucessful Found innerChatChannel ${innerChatChannel.channelUid}"
//                                )
//
//                            }
//                        }
//                    }
//
//                    Log.d(
//                        ContentValues.TAG,
//                        "sucessful Found targetChanel1 ${innerChatChannel.channelUid}"
//                    )
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
//                }
//            })
//
//
//
//
////        messageListener =object :ValueEventListener{
////            override fun onDataChange(snapshot: DataSnapshot) {
////                var innerChatChannel:ChatChannel = ChatChannel()
////                var currentChatRoomMemberList: ArrayList<String>
////                snapshot.children.forEach { child ->
////                    // Extract Message object from the DataSnapshot
////                    val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
////
////
////                    if (chatChannel != null) {
////                        currentChatRoomMemberList = chatChannel.members as ArrayList<String>
////                        currentChatRoomMemberList.sort()
////                        if (userList.equals(currentChatRoomMemberList)) {
////                            innerChatChannel = chatChannel
////
////                            Log.d(
////                                ContentValues.TAG,
////                                "sucessful Found innerChatChannel ${innerChatChannel.channelUid}"
////                            )
////
////                        }
////                    }
////                }
////
////                Log.d(
////                    ContentValues.TAG,
////                    "sucessful Found targetChanel1 ${innerChatChannel.channelUid}"
////                )
////
////
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                Log.w(ContentValues.TAG, "messages:onCancelled: ${error.message}")
////            }
////
////
////        }
////
////        messageRef.addValueEventListener(messageListener)
//
//        Log.d(ContentValues.TAG, "sucessful Found targetChanel2 ${targetChanel.channelUid}")
//        return targetChanel
//    }



    fun createChannel(toUserUid: String) {
        val database = Firebase.database
        val messageRef = database.getReference("ChatRoom")
        val newChannelUid = messageRef.push().key.toString()
        val newChannelMessageList: MutableList<Message> = mutableListOf()
        val newChannelCreatedTimeStamp: HashMap<String?, Any?> = HashMap<String?, Any?>()
        // store members' UID
        val newChannelMembers: MutableList<String> =
            mutableListOf(currentUser.uid.toString(), toUserUid)
        newChannelMembers.sort()
        val newChannel = ChatChannel(
            newChannelUid,
            newChannelMessageList,
            newChannelCreatedTimeStamp,
            newChannelMembers
        )

        messageRef.child(newChannelUid).setValue(newChannel).addOnCompleteListener() {
            Log.d(ContentValues.TAG, "sucessful add chatChannel ${newChannel.channelUid}")
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "cannot add chatChannel")
        }


    }


    fun getUserbyUid(userUid: String): User? {
        if (userUid == "Empty"){
            return null
        }
        val database = Firebase.database
        val targetUser: User = User()
        database.getReference("Users").child(userUid).get().addOnSuccessListener {
            val targetUser: User? = it.getValue<User>()
            mUser.postValue(targetUser)
            Log.d(ContentValues.TAG, "sucessful found user ${it.value}")
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "cannot found user ${userUid}")
        }
        return mUser.value
    }


//    //test corountine
//
//
//    suspend fun DatabaseReference.awaitsSingle(): DataSnapshot? =
//        suspendCancellableCoroutine { continuation ->
//            val listener = object : com.google.firebase.database.ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                    val exception = when (error.toException()) {
//                        is FirebaseException -> error.toException()
//                        else -> Exception("The Firebase call for reference $this was cancelled")
//                    }
//                    continuation.resumeWithException(exception)
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    try {
//                        continuation.resume(snapshot)
//                    } catch (exception: Exception) {
//                        continuation.resumeWithException(exception)
//                    }
//                }
//            }
//            continuation.invokeOnCancellation { this.removeEventListener(listener) }
//            this.addListenerForSingleValueEvent(listener)
//        }

//            suspend fun getChannelTest(toUserUid: String): ChatChannel? {
//
////        var innerChatChannel:ChatChannel = ChatChannel()
//                var targetChanel: ChatChannel = ChatChannel()
//                var currentChatRoomMemberList: ArrayList<String>
//                val currentUserUid = currentUser.uid.toString()
//                var userList = arrayListOf<String>(currentUserUid, toUserUid)
//                userList.sort()
//                try {
//                    Firebase.database.getReference("ChatRoom")
//                        .awaitsSingle()?.children?.forEach() { child ->
//                            // Extract Message object from the DataSnapshot
//                            val chatChannel: ChatChannel? = child.getValue<ChatChannel>()
//
//                            if (chatChannel != null) {
//                                currentChatRoomMemberList = chatChannel.members as ArrayList<String>
//                                currentChatRoomMemberList.sort()
//                                if (userList.equals(currentChatRoomMemberList)) {
//                                    targetChanel = chatChannel
//                                    Log.d(
//                                        ContentValues.TAG,
//                                        "sucessful Found innerChatChannel ${targetChanel.channelUid}"
//                                    )
//
//                                }
//                            }
//
//                        }
//                } catch (ex: Exception) {
//                    Log.d(ContentValues.TAG, "cannot add chatChannel")
//                    null
//                }
//                Log.d(
//                    ContentValues.TAG,
//                    "sucessful Found outside targert ${targetChanel.channelUid}"
//                )
//                return targetChanel
//            }



}

interface MyCallback {
    fun onCallback(value: ChatChannel?)
}


