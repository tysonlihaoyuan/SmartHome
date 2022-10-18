package com.example.chatroom.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.chatroom.ViewModel.Data.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue


class AddFriendListViewModel : ViewModel() {
//    val db = Firebase.firestore
    private lateinit var userRef: DatabaseReference
//    private lateinit var databaseReference: DatabaseReference
    private lateinit var userListener: ValueEventListener
    val mUserList:MutableLiveData<List<User>> = MutableLiveData()
    val userList:ArrayList<User> = ArrayList()

    init{
        Log.d(TAG, "addFriendviwmodel is created")
//        databaseReference = Firebase.database.reference

        loadUserList()
    }


    fun loadUserList() {

//        db.collection("user").addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            val currentUserList:ArrayList<User> = ArrayList<User>()
//            snapshot?.documents?.forEach {
//                it.toObject(User::class.java)?.let { user ->
//                    Log.d(TAG, "" + user.userName)
//                    currentUserList.add(user)
//                    userList.add(user)
//                }
//            }
//            mUserList.postValue(currentUserList)
//            Log.d(TAG, "Current userList: $mUserList")
//        }
//        userRef = databaseReference.child
        val database = Firebase.database
        val userRef = database.getReference("Users")
        Log.d(TAG, "number of users ${userRef.key}")
        userListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // New data at this path. This method will be called after every change in the
                // data at this path or a subpath.

                Log.d(TAG, "number of users ${dataSnapshot.childrenCount}")
                val currentUserList: ArrayList<User> = ArrayList<User>()
                dataSnapshot.children.forEach { child ->
                    // Extract Message object from the DataSnapshot
                    val user: User? = child.getValue<User>()

                    if (user != null) {
                        currentUserList.add(user)
                        userList.add(user)
                    }
                    // Use the message
                    // [START_EXCLUDE]
                    Log.d(TAG, "current user ${user?.userName}")
                    Log.d(TAG, "message sender name: ${user?.useremail}")

//                  userList.add(user)
                    // [END_EXCLUDE]
                }
                mUserList.postValue(currentUserList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "messages:onCancelled: ${error.message}")
            }


        }
        userRef.addValueEventListener(userListener)
    }
        fun getUserlist(): List<User> {
            loadUserList()
            Log.d(TAG, "Current cites in CA: $mUserList")
            return userList

        }


    }




