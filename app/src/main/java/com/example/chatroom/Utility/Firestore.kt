package com.example.chatroom.Utility

import android.content.ContentValues
import android.util.Log
import com.example.chatroom.ViewModel.Data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


interface DatabaseFactory{


    fun getUserByUid(userUid: String):User?
}
class FirebaseFactory:DatabaseFactory {

    val database = Firebase.database
    private lateinit var currentUser: FirebaseAuth
//    var targetUser: User? = null


   override fun getUserByUid(userUid: String): User? {
        if (userUid == "Empty"){
            return null
        }


        val targetUserDocument = database.getReference("Users").child(userUid)

        var targetUser: User? = null
        GlobalScope.launch(Dispatchers.IO) {
            val user = targetUserDocument.get().await().getValue<User>()


            withContext(Dispatchers.Main){
                if (user != null) {
                    targetUser = user

                }

            }
        }
       Log.d(
           ContentValues.TAG,
           "sucessful found target user in corotione ${targetUser?.userName}  "
       )


        return targetUser
    }

}