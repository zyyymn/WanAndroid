package com.example.user.repo

import androidx.lifecycle.MutableLiveData
import base.BaseRepository
import com.example.user.Bean.LoginBean
import com.example.user.api.UserApi

class LoginRepo(private val api: UserApi): BaseRepository() {

    suspend fun login(
        username:String,
        password:String,
       )
}