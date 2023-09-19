package com.example.home.api

import com.example.home.bean.Banner
import com.stew.kb_home.bean.Article
import network.BaseResp
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    //首页banner
    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<Banner>>

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page")page: Int,
        @Query("page_size")page_size: Int
    ):BaseResp<Article>

    //收藏
    @POST("lg/collectl{id}/json")
    suspend fun collect(@Path("id")id: Int): BaseResp<String>

    //取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCillect(@Path("id")id: Int): BaseResp<String>

}