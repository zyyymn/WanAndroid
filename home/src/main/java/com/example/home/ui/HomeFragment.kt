package com.example.home.ui


import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import base.BaseVMFragment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.R
import com.example.home.adapter.BannerAdapter
import com.example.home.adapter.HomeItemClickListener
import com.example.home.adapter.HomeRVAdapter
import com.example.home.bean.Banner
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.viewmodel.HomeViewModel
import com.stew.kb_home.bean.Article
import network.BaseStateObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import util.Constants
import util.ToastUtil

class HomeFragment: BaseVMFragment<FragmentHomeBinding>(){

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var homeRVAdapter: HomeRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMore = false
    var list:MutableList<Article.ArticleDetail> = arrayListOf()
    var collectPosition: Int = 0

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun observe() {

        //监听bannerlist的变化
        homeViewModel.bannerList.observe(this,object :
        BaseStateObserver<List<Banner>>(null){
            override fun getRespDataSuccess(it: List<Banner>) {
                Log.d(TAG,"OBSERVER BANNERLIST:${it.size}")
                if (it.isEmpty()){
                    return
                }
                mBind.topView.refreshData(it)
            }

            override fun getRespDataEnd() {
                resetUI()
            }
        })

        //监听文章列表的变化
        homeViewModel.article.observe(this,object :BaseStateObserver<Article>(null){
            override fun getRespDataSuccess(it: Article) {
                resetUI()
                currentPage = it.curPage -1

                //下拉刷新
                if (currentPage == 0){
                    list.clear()
                }

                //最后一页
                if (it.over){
                    homeRVAdapter.isLastPage = true
                }

                //list添加数据
                list.addAll(it.datas)

                //处理数据
                if (currentPage ==0){
                    homeRVAdapter.setData(null)
                    homeRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                }else{
                    homeRVAdapter.setData(list)
                }
                Log.d(TAG, "observer articlerList: ${list.size}")
            }

            override fun getRespDataEnd() {
                resetUI()
            }

        })

        //监听收藏的变化
        homeViewModel.collectData.observe(this,object :BaseStateObserver<String>(null){
            override fun getRespDataStart() {
                showLoadingDialog()
            }

            override fun getRespDataEnd() {
                dismissLoadDialog()
            }

            override fun getRespSuccess() {
                dismissLoadDialog()
                if (list[collectPosition].collect){
                    ToastUtil.showMsg("取消收藏")
                    list[collectPosition].collect = false
                }else{
                    ToastUtil.showMsg("收藏成功")
                    list[collectPosition].collect = true
                }
                homeRVAdapter.notifyItemChanged(collectPosition)
            }
        })
    }

    private fun resetUI() {
        isLoadMore = true     //到end了 值为true
        if (mBind.srlHome.isRefreshing){
            mBind.srlHome.isRefreshing = false
        }

    }

    override  fun init() {
        //banner部分的adapter初始化
        mBind.topView.apply {
            setAdapter(BannerAdapter())
            setLifecycleRegistry(lifecycle)
            setScrollDuration(600)
            setInterval(5000)
            setAutoPlay(false)
        }.create()

        lm = LinearLayoutManager(activity)

        //RV部分的adapter初始化
        mBind.bottomView.layoutManager = lm
        homeRVAdapter = HomeRVAdapter(object : HomeItemClickListener{
            override fun onItemClick(position: Int) {
                val data = list[position]
                //路由实现模块间的跳转和数据传输
                ARouter.getInstance()
                    .build(Constants.PATH_WEB)
                    .withString(Constants.WEB_LINK,data.link)
                    .withString(Constants.WEB_TITLE,data.title)
                    .navigation()
            }

            override fun onCollectClick(position: Int) {
                collectPosition = position
                //已经收藏的移除
                if (list[collectPosition].collect){
                    homeViewModel.unCollect(list[collectPosition].id)
                }else{
                    homeViewModel.collect(list[collectPosition].id)
                }
            }

        })
        mBind.bottomView.adapter = homeRVAdapter

        //设置滑动监听
        mBind.bottomView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    &&homeRVAdapter.itemCount !=0
                    &&lm.findLastVisibleItemPosition()+1 == homeRVAdapter.itemCount
                    && !isLoadMore && !homeRVAdapter.isLastPage){
                    Log.d(TAG,"onScrollStateChanged: last-----")

                    isLoadMore = true
                    homeViewModel.getArticle(currentPage+1)
                }
            }
        })

        mBind.srlHome.setColorSchemeResources(com.example.common.R.color.theme_color)
        mBind.srlHome.setOnRefreshListener {
            homeRVAdapter.isLastPage = false
            getHomeData()

        }
        getHomeData()
    }

    private fun getHomeData() {
        homeViewModel.getBanner()
    }


}