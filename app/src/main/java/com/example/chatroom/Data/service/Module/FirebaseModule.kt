package com.example.chatroom.Data.service.Module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides fun auth(): FirebaseAuth = Firebase.auth

    @Provides fun firebaseRealtime(): FirebaseDatabase = Firebase.database

    @Provides fun userRef(): Query = Firebase.database.getReference("Users").orderByChild("lastUpdate/timestamp")

    @Provides fun channelRef(): DatabaseReference = Firebase.database.getReference("Channel").ref

    @Provides fun fireStorage(): StorageReference=Firebase.storage.reference.child("profile")
}