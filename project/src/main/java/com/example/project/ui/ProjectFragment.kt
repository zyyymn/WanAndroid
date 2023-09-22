package com.example.project.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import base.BaseVMFragment
import com.example.project.R
import com.example.project.adapter.ProVPAdapter
import com.example.project.bean.ProjectType
import com.example.project.databinding.FragmentProjectBinding
import com.example.project.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator
import network.BaseStateObserver

class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {

    private val projectViewModel: ProjectViewModel by viewModels()
    private val l: MutableList<String> = arrayListOf()
    private val f: MutableList<Fragment> = arrayListOf()


    override fun observe() {
        projectViewModel.proTypeList.observe(this,object
            :BaseStateObserver<List<ProjectType>>(null){
            override fun getRespDataSuccess(it: List<ProjectType>) {
                initTab(it)
            }
            })
    }

    private fun initTab(it: List<ProjectType>) {

        for (i in 0..4){
            //tablayout  id+name
            l.add((i+1).toString()+"."+it[i].name)
            f.add(ProjectChildFragment.newInstance(it[i].id,i))
        }
        //绑定 VPadapter
        mBind.viewPager.adapter = ProVPAdapter(this,f)
        mBind.viewPager.offscreenPageLimit = 5
        //绑定 tablayout 与 viewPager
        TabLayoutMediator(mBind.tabLayout,mBind.viewPager){
            tab,position-> tab.text = l[position]
        }.attach()
    }

    override fun init() {
        projectViewModel.getProTypeList()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_project
    }

}