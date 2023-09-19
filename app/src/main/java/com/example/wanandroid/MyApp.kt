package com.example.wanandroid

import android.app.Application
import android.content.Context
import androidx.viewbinding.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.example.user.di.userModule
import util.AppLogUtil
import util.ToastUtil
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    private val modules = mutableListOf(homeModule, ProjectModule, naviModule, meModule, userModule)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtil.init(this)
        initKoin()
        initARouter()
        initMMKV()
        AppLogUtil.init(this)
    }

    //初始化映射
    private fun initMMKV() {
        MMKV.initialize(this)
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(modules)
        }
    }
}