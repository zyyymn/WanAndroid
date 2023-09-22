package com.example.project.ui

import android.os.Bundle
import base.BaseVMFragment
import com.example.project.databinding.FragmentProjectChildBinding

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

    override fun observe() {
        TODO("Not yet implemented")
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun getLayoutID(): Int {
        TODO("Not yet implemented")
    }
}