package com.example.user.repo

import androidx.lifecycle.MutableLiveData
import base.BaseRepository
import com.example.user.Bean.LoginBean
import com.example.user.api.UserApi
import network.BaseResp
import network.RespStateData

class LoginRepo(private val api: UserApi): BaseRepository() {

    suspend fun login(
        username:String,
        password:String,
        data:RespStateData<LoginBean>)=
        dealResp(
            block = {api.login(username,password)},data
        )
}