package com.example.user.ui

import androidx.lifecycle.ViewModelProvider
import base.BaseActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.user.Bean.LoginBean
import com.example.user.R
import com.example.user.databinding.ActivityLoginBinding
import com.example.user.viewmodel.LoginViewModel
import network.BaseStateObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import util.Constants
import util.KVUtil
import util.ToastUtil


@Route(path = Constants.PATH_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding>(){

    private val loginViewModel : LoginViewModel by viewModel()

    override fun getLayoutID(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        //监听loginData变化，看是否登录成功
        loginViewModel.loginData.observe(this,object : BaseStateObserver<LoginBean>(null) {
            override fun getRespDataSuccess(it: LoginBean) {
                KVUtil.put(Constants.USER_NAME,it.username)
                ToastUtil.showMsg("登陆成功！")
                finish()
            }
        })

        //点击登录
        mBind.txLogin.setOnClickListener{
            if (mBind.edit1.text.isNotEmpty() &&
                    mBind.edit2.text.isNotEmpty()){

                loginViewModel.login(
                    mBind.edit1.text.toString(),
                    mBind.edit2.text.toString())
            }else{
                ToastUtil.showMsg("输入有误")
            }
        }

        //back 按钮
        mBind.imgBack.setOnClickListener {
            finish()
        }

    }


}