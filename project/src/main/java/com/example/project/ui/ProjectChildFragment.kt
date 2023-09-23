package com.example.project.ui

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.BaseVMFragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.project.R
import com.example.project.adapter.ProRVAdapter
import com.example.project.adapter.ProjectClickListeener
import com.example.project.bean.Project
import com.example.project.databinding.FragmentProjectChildBinding
import com.example.project.viewmodel.ProjectViewModel
import network.BaseStateObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import util.LoadingViewUtil
import util.ToastUtil
import kotlin.time.Duration.Companion.days

class ProjectChildFragment
    : BaseVMFragment<FragmentProjectChildBinding>() {


    companion object{
        private const val C_ID: String ="cid"
        private const val INDEX: String = "index"

        fun newInstance(cid: Int,index:Int) :ProjectChildFragment{
            val f = ProjectChildFragment()
            val bundle = Bundle()
            bundle.putInt(C_ID,cid)
            bundle.putInt(INDEX,index)
            f.arguments = bundle
            return f

        }
    }

    var collectPosition: Int = 0
    private var currentID: Int =0
    private var currentIndex: Int = 0
    private val  projectViewModel:  ProjectViewModel by viewModel()
    lateinit var proRVAdapter: ProRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMoew = false
    val list: MutableList<Project.ProjectDetail> = arrayListOf()

    override fun getLayoutID(): Int {
        return R.layout.fragment_project_child
    }

    override fun observe() {

        projectViewModel.proList.observe(this,object
            :BaseStateObserver<Project>(null){
            override fun getRespDataSuccess(it: Project) {

                resetUI()
                currentPage = it.curPage

                if (currentPage ==1){
                    list.clear()
                }

                if (it.over){
                    proRVAdapter.isLastPage =true
                }
                list.addAll(it.datas)

                if (currentPage == 1){
                    proRVAdapter.setData(null)
                    proRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                }else{
                    proRVAdapter.setData(list)
                }
            }

            override fun getRespDataEnd() {
                resetUI()
            }
            })

        projectViewModel.collectData.observe(this,object
            :BaseStateObserver<String>(null){

            override fun getRespDataStart() {
                LoadingViewUtil.showLoadingDialog(requireContext(),true)
            }

            override fun getRespDataEnd() {
                LoadingViewUtil.dismissLoadingDialog()
            }

            override fun getRespSuccess() {
                LoadingViewUtil.dismissLoadingDialog()
                if (list[collectPosition].collect){
                    ToastUtil.showMsg("取消收藏")
                    list[collectPosition].collect = false
                }else{
                    ToastUtil.showMsg("收藏成功")
                    list[collectPosition].collect = true
                }
                
                proRVAdapter.notifyItemChanged(collectPosition)
            }
            })
    }

    override fun init() {
        //从ProjectFragment传过来的数据
        val a = arguments
        if (a != null){
            currentID = a.getInt(C_ID)
            currentIndex = a.getInt(INDEX)
        }

        //初始化rvadapter
        lm = LinearLayoutManager(activity)
        mBind.rvPro.layoutManager = lm
        proRVAdapter = ProRVAdapter(object : ProjectClickListeener{
            override fun onItemClick(position: Int) {
                val data = list[position]
                ARouter.getInstance()
                    .build(util.Constants.PATH_WEB)
                    .withString(util.Constants.WEB_LINK,data.link)
                    .withString(util.Constants.WEB_TITLE,data.title)
                    .navigation()
            }

            override fun onCollectClick(position: Int) {
                  collectPosition = position
                if (list[collectPosition].collect){
                    projectViewModel.unCollect(list[collectPosition].id)
                }else{
                    projectViewModel.collect(list[collectPosition].id)
                }
            }
        })

        mBind.rvPro.adapter = proRVAdapter

        //滑动监听
        mBind.rvPro.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    &&proRVAdapter.itemCount != 0
                    &&(lm.findLastVisibleItemPosition()+1 == proRVAdapter.itemCount)
                    &&!isLoadMoew
                    &&!proRVAdapter.isLastPage){
                    Log.d(TAG,"onScrollStateChanged: last-----")
                    isLoadMoew = true
                    lazyLoad()
                }
            }
        })

        mBind.srlPro.setColorSchemeResources(com.example.common.R.color.theme_color)
        mBind.srlPro.setOnRefreshListener {
            proRVAdapter.isLastPage = false
            projectViewModel.getProList(1,currentID)
        }



    }

    override fun lazyLoad() {
        projectViewModel.getProList(currentPage+1,currentID)
    }

    private fun resetUI(){
        isLoadMoew = false
        if (mBind.srlPro.isRefreshing){
            mBind.srlPro.isRefreshing = false
        }
    }

}