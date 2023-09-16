package util

import android.app.Application
import android.content.Context
import android.widget.Toast

object ToastUtil {

    lateinit var mContext: Application
    fun init(context: Application){
        mContext= context
    }

    fun showMsg(str: String){
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show()
    }
}