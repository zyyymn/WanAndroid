package base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import network.BaseResp
import network.RespStateData
import retrofit2.HttpException
import util.Constants
import util.ToastUtil
import java.net.ConnectException
import java.net.UnknownHostException


/**
 * 对仓库的公共处理*/
open class BaseRepository {

    suspend fun  <T>dealResp(
        block: suspend ()->BaseResp<T>,
        livedata: RespStateData<T>
    ){

        Log.d("TestSus","br1")
        var result = BaseResp<T>()
        result.responseState = BaseResp.ResponseState.REQUEST_START
        livedata.value = result

        try {
            Log.d("TestSus","br2")
            result = block.invoke()
            Log.d("TestSus", "br3")
            Log.d("BaseRepository", result.errorCode.toString() + "/" + result.errorMsg)

            when(result.errorCode){
               Constants.HTTP_SUCCESS -> {
                   result.responseState = BaseResp.ResponseState.REQUEST_SUCCESS

               }
                Constants.HTTP_AUTH_INVALID -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ToastUtil.showMsg("请先登录")
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()

                }
                else -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ToastUtil.showMsg("code:" + result.errorCode.toString() + " / msg:" + result.errorMsg)
                }
            }
        }catch (e: Exception){
            Log.d("BaseRepository", "dealResp: Exception$e")
            when(e){
                is UnknownHostException,
                is HttpException,
                is ConnectException,
                -> {
                    ToastUtil.showMsg("网络错误")
                }
                else -> {
                    ToastUtil.showMsg("未知错误！！")
                }
            }
            result.responseState = BaseResp.ResponseState.REQUEST_ERROR
        }finally {
            Log.d("TestSus","br4")
            livedata.value = result
        }


    }


}