package com.example.project.bean

data class Project(
    val datas: List<ProjectDetail>,
    var over: Boolean,
    val curPage: Int
) {
    data class ProjectDetail(
        val id: Int,
        val author: String,
        val link: String,
        val desc: String,
        val niceDate: String,
        val envelopePic: String,
        val title: String,
        var collect: Boolean
    )
}
