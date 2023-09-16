package base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import util.LoadingViewUtil

/**
 * 项目中所有fragment的基类*/
abstract class BaseFragment<T: ViewDataBinding> :Fragment(){

    val TAG = "BaseFragment"
    lateinit var mBind: T
    lateinit var mContext: Context

    //绑定activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    abstract fun getLayoutID(): Int

    //加载fragment视图
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView:")
        mBind = DataBindingUtil.inflate(inflater,getLayoutID(),container,false)
        return mBind.root
    }

    //fragment与activity交互
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreated:")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart:")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume:")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause:")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop:")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy:")
        mBind.unbind()
    }

    //登录时的Dialog
    fun showLoadingDialog(){
        Log.d(TAG,TAG+"showLoadingDialog:")

        //加载
        LoadingViewUtil.showLoadingDialog(mContext,true)
    }

    //取消Dialog
    fun dismissLoadDialog(){
        Log.d(TAG,TAG+"dismissLoadDialog:")

        //取消
        LoadingViewUtil.dismissLoadingDialog()
    }

}