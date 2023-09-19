package com.example.home.repo

import androidx.constraintlayout.helper.widget.Flow
import base.BaseRepository
import com.example.home.api.HomeApi
import com.example.home.bean.Banner
import com.stew.kb_home.bean.Article
import network.RespStateData

class HomeRepo(private val api: HomeApi): BaseRepository() {

    suspend fun getBanner(data: RespStateData<List<Banner>>){
        dealResp({
            api.getBanner()
        },data)
    }

    suspend fun getArticle(currentPage: Int,data: RespStateData<Article>){
        dealResp({
            api.getArticleList(currentPage,10)
        },data)
    }

    suspend fun collect(id: Int,data: RespStateData<String>){
        dealResp({
            api.collect(id)
        },data)
    }

    suspend fun unCollect(id: Int,data: RespStateData<String>){
        dealResp({
            api.unCillect(id)
        },data)
    }

    //flow
  /*  fun getBannerByFlow(): Flow<BaseResp<List<Banner>>>{
        return flow{
            val resp = api.getBanner()
            emit(resp)
        }.flowOn(Dispatchers.IO)
    }*/
}