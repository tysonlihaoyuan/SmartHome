package com.example.chatroom.Data.service.impl


import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.chatroom.Data.User
import com.example.chatroom.Data.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseRealtime: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage

) : AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val existingUser: Boolean
        get() = auth.currentUser != null
    override val currentUser: Flow<User>
        get() = flow<User> {


            val user = auth.currentUser?.let { User(uid = it.uid) }
            if (user != null) {
                emit(user)
            }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        val timestamp = HashMap<String?, Any?>()
        timestamp["timestamp"] = ServerValue.TIMESTAMP
        timestamp.put("timestamp", ServerValue.TIMESTAMP);
        auth.currentUser?.let { firebaseRealtime.getReference("Users").child(it.uid).child("lastUpdate").setValue(timestamp).await()}

    }

    override suspend fun sendRecoveryEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAnonymousAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun linkAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).await()
        val timestamp = HashMap<String?, Any?>()
        timestamp["timestamp"] = ServerValue.TIMESTAMP
        timestamp.put("timestamp", ServerValue.TIMESTAMP);

        auth.currentUser?.let {
            firebaseRealtime.getReference("Users").child(it.uid).setValue(
                User(
                    userEmail = email,
                    uid = it.uid,
                    userName = email,
                    userPassword = password,
                    lastUpdate = timestamp
                )
            )
        }


    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun selectProfile() {


    }

    override suspend fun uploadProfilePicture(bitmap: Bitmap) {
        val profileName = auth.currentUser!!.uid +".jpg"
        val imageRef= firebaseStorage.reference.child(profileName)
        val baos =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()


    }


}