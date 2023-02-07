package com.example.chatroom.Data.service.Module

//import com.example.chatroom.Data.service.impl.AccountServiceImpl
import com.example.chatroom.Data.service.AccountService
import com.example.chatroom.Data.service.ChannelService
import com.example.chatroom.Data.service.ChatService
import com.example.chatroom.Data.service.FriendService
import com.example.chatroom.Data.service.impl.AccountServiceImpl
import com.example.chatroom.Data.service.impl.ChannelServicelmp
import com.example.chatroom.Data.service.impl.ChatServiceImpl
import com.example.chatroom.Data.service.impl.FriendServicelmpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ServiceModule {
        @Binds
        abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

        @Binds
        abstract fun provideChatService(impl: ChatServiceImpl): ChatService

        @Binds
        abstract fun provideFriendService(impl: FriendServicelmpl): FriendService
        @Binds
        abstract fun provideChannelService(impl: ChannelServicelmp): ChannelService
//
//        @Binds
//        abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
    }
