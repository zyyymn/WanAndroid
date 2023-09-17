package com.example.user.viewmodel

import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.example.user.Bean.LoginBean
import com.example.user.repo.LoginRepo
import network.RespStateData


/**
 * 登录功能的activity
 * */

class LoginViewModel(private val repo: LoginRepo)
    :BaseViewModel(){

        var loginData = RespStateData<LoginBean>()

    fun login(username:String,password:String){
        launch(
            block = {
                repo.login(username,password,loginData)
            }
        )

    }

}