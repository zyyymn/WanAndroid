package com.example.wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import base.BaseActivity
import com.example.wanandroid.databinding.ActivityMainBinding
import util.Constants
import util.KVUtil

class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var fragmentList: MutableList<Fragment>
    var oldFragmentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        mBind.imgDraw.setOnClickListener {
            mBind.dl.open()
        }

        mBind.bnv.setOnItemSelectedListener{
           when(it.itemId){
               R.id.f1 -> {
                   swithFragment(0)
                   mBind.fName = "首页"
                   return@setOnItemSelectedListener true
               }

               R.id.f2 ->{
                   swithFragment(1)
                   mBind.fName = "项目"
                   return@setOnItemSelectedListener true
               }

               R.id.f3 ->{
                   swithFragment(2)
                   mBind.fName = "导航"
                   return@setOnItemSelectedListener true
               }

               R.id.f4 ->{
                   swithFragment(3)
                   mBind.fName = "收藏"
                   return@setOnItemSelectedListener true
               }
           }
            false
        }

        fragmentList = mutableListOf(

        )

        swithFragment(0)
        mBind.fName = "首页"
    }


    //切换页面
    fun swithFragment(position: Int){
        val targetFragment = fragmentList[position]
        val oldFragment = fragmentList[oldFragmentIndex]
        oldFragmentIndex = position
        val ft = supportFragmentManager.beginTransaction()

        if (oldFragment.isAdded){
            ft.hide(oldFragment)
        }

        if (!targetFragment.isAdded){
            ft.add(R.id.f_content,targetFragment)
        }

        ft.show(targetFragment).commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        val name = KVUtil.getString(Constants.USER_NAME)
        if (name != null){
            findViewById<TextView>(R.id.tx_info).text = name
        }
    }


}