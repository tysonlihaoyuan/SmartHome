<<<<<<< HEAD


=======
>>>>>>> 8acc092d1c0ebcab7a09018dd7b31390809fe108
package com.example.chatroom.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.chatroom.ViewModel.Data.User


class AddFriendListViewModel : ViewModel() {
    val db = Firebase.firestore
    val mUserList:MutableLiveData<List<User>> = MutableLiveData()
    val userList:ArrayList<User> = ArrayList()
<<<<<<< HEAD
    init{
        loadUserList()
    }
=======
init{
    loadUserList()
}
>>>>>>> 8acc092d1c0ebcab7a09018dd7b31390809fe108

    fun loadUserList(){

        db.collection("user").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val currentUserList:ArrayList<User> = ArrayList<User>()
            snapshot?.documents?.forEach {
                it.toObject(User::class.java)?.let { user ->
                    Log.d(TAG, "" + user.userName)
                    currentUserList.add(user)
                    userList.add(user)
                }
            }
            mUserList.postValue(currentUserList)
            Log.d(TAG, "Current userList: $mUserList")
        }



    }
    fun getUserlist():List<User>{
        loadUserList()
        Log.d(TAG, "Current cites in CA: $mUserList")
        return userList

    }




}
<<<<<<< HEAD
=======

>>>>>>> 8acc092d1c0ebcab7a09018dd7b31390809fe108
