package com.example.chatroom.Data.service

import android.graphics.Bitmap

import com.example.chatroom.Data.User
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val currentUserId: String
    val existingUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
    suspend fun selectProfile()
    suspend fun uploadProfilePicture(bitmap: Bitmap)


}