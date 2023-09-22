package com.example.project.repo

import base.BaseRepository
import com.example.project.api.ProjectApi
import com.example.project.bean.Project
import com.example.project.bean.ProjectType
import network.RespStateData

class ProjectRepo(private var api: ProjectApi)
    :BaseRepository(){

    suspend fun getProTypeList(data: RespStateData<List<ProjectType>>)
        =dealResp({
            api.getProType()
    },data)

    suspend fun getProList(
        currentPage: Int,
        cid : Int,
        data: RespStateData<Project>
    )=dealResp({
        api.getProList(currentPage,10,cid)
    },data)

    suspend fun collect(
        id: Int,
        data: RespStateData<String>
    )=dealResp({
        api.collect(id)
    },data)


    suspend fun unCollect(
        id: Int,
        data: RespStateData<String>
    )=dealResp({
        api.unCollect(id)
    },data)
}