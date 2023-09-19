package com.example.home.viewmodel

import base.BaseViewModel
import com.example.home.bean.Banner
import com.example.home.repo.HomeRepo
import com.stew.kb_home.bean.Article
import network.RespStateData

class HomeViewModel(private var repo: HomeRepo ):BaseViewModel() {


    var bannerList = RespStateData<List<Banner>>()
    var article = RespStateData<Article>()
    var collectData = RespStateData<String>()

    fun getBanner() = launch {
        repo.getBanner(bannerList)
    }
    fun getArticle(currentPage: Int) = launch {
        repo.getArticle(currentPage,article)
    }
    fun collect(id: Int) = launch {
        repo.collect(id,collectData)
    }
    fun unCollect(id: Int) = launch {
        repo.unCollect(id,collectData)
    }

  /*  fun getBannerByFlow() = launch {
    }*/
}