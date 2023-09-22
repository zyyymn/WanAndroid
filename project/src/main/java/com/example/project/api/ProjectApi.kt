package com.example.project.api

import com.example.project.bean.Project
import com.example.project.bean.ProjectType
import network.BaseResp
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {

    //获取项目分类
    @GET("project/tree/json")
    suspend fun getProType(): BaseResp<List<ProjectType>>

    //获取项目列表
    @GET("project/list/{page}/json")
    suspend fun getProList(
        @Path("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("cid") cid: Int
    ): BaseResp<Project>

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResp<String>

    //取消收藏（文章列表）
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): BaseResp<String>
}