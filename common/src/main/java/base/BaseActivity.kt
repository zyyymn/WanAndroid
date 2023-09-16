package base

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import util.AppLogUtil
import util.LoadingViewUtil

/**
 * 项目中所有父类的基类
 * */
abstract class BaseActivity<T: ViewDataBinding>
    :AppCompatActivity(){

        val TAG = "BaseActivity"
    lateinit var mBind: T

    abstract fun getLayoutID():Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG,"onCreate:")
        mBind = DataBindingUtil.setContentView(this,getLayoutID())
        init()
    }

    //设置灰色主题
    private  fun setGrayTheme(){
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0F)
        paint.colorFilter =ColorMatrixColorFilter(cm)
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE,paint)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart:")
    }

    //恢复数据，在切屏，back时会自动保存数据
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG,"onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume:")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop:")
    }

    //保存数据
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG,"onSaveInstanceState:")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestory:")

        mBind.unbind()
    }

    //登录时的Dialog
    fun showLoadingDialog(){
        Log.d(TAG,TAG+"showLoadingDialog:")

        //加载
        LoadingViewUtil.showLoadingDialog(this,true)
    }

    //取消Dialog
    fun dismissLoadDialog(){
        Log.d(TAG,TAG+"dismissLoadDialog:")

        //取消
        LoadingViewUtil.dismissLoadingDialog()
    }



}