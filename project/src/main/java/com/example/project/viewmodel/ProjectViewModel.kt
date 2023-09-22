package com.example.project.viewmodel

import base.BaseViewModel
import com.example.project.bean.Project
import com.example.project.bean.ProjectType
import com.example.project.repo.ProjectRepo
import network.RespStateData

class ProjectViewModel(private var repo: ProjectRepo)
    :BaseViewModel(){

    var proTypeList = RespStateData<List<ProjectType>>()
    var proList = RespStateData<Project>()
    var collectData = RespStateData<String>()


    fun getProTypeList() = launch(
        block = {repo.getProTypeList(proTypeList)}
    )

    fun getProList(currentPage: Int,cid: Int) = launch(
        block = { repo.getProList(currentPage, cid, proList) }
    )
    fun collect(id: Int) = launch(
        block = {repo.collect(id,collectData)}
    )
    fun unCollect(id: Int) = launch (
        block = {repo.unCollect(id,collectData)}
            )
}