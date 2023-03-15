package com.example.chatroom.screens.Home

import androidx.lifecycle.ViewModel
import com.example.chatroom.Data.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HomeViewModel@Inject constructor(private val accountService:AccountService):ViewModel() {



}