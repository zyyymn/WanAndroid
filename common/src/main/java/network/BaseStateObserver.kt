package network

import android.database.Observable
import android.util.Log
import androidx.lifecycle.Observer

open class BaseStateObserver<T>(var t: Boolean):
    Observer<BaseResp<T>> {

    private val TAG = "BaseStateObserver"
    override fun onChanged(value: BaseResp<T>) {
        when(value.responseState){
            BaseResp.ResponseState.REQUEST_START ->{
                Log.d(TAG,"Observer: start")
                getRespDataStart()
            }
            BaseResp.ResponseState.REQUEST_SUCCESS ->{
                Log.d(TAG,"Observer: success")
                if (value.data==null){
                    getRespSuccess()
                }else{
                    getRespDataSuccess(value.data!!)
                }
            }
            BaseResp.ResponseState.REQUEST_FAILED ->{
                Log.d(TAG,"Observer: failed")
                getRespDataEnd()
            }
            BaseResp.ResponseState.REQUEST_ERROR ->{
                Log.d(TAG,"Observer: error")
                getRespDataEnd()
            }

            else -> {}
        }
    }

    open fun getRespDataStart(){}
    open fun getRespDataSuccess(it: T) {}
    open fun getRespSuccess() {}
    open fun getRespDataEnd() {}
}