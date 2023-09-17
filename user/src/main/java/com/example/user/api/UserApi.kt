package com.example.user.api

import com.example.user.Bean.LoginBean
import network.BaseResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 登录的请求API
 * */
interface UserApi {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResp<LoginBean>
}