package util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.common.R

/**
 *
* 登录Dialog 页面工具类
 * */
object LoadingViewUtil {

    private var dialog: AlertDialog? = null

    fun showLoadingDialog(context: Context,isCancel: Boolean){
        if (context is Activity){
            if (context.isFinishing){
                return
            }
        }
        if (dialog == null){
            val b = AlertDialog.Builder(context)
            b.setView(
                LayoutInflater.from(context).inflate(R.layout.common_loading_dialog,null)
            )

            b.setCancelable(isCancel)
            dialog = b.create()

            if (dialog?.window == null){
                return
            }
            dialog?.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        if (dialog!=null && !(dialog!!.isShowing)){
            dialog!!.show()
        }
    }

    fun dismissLoadingDialog(){
        if (dialog !=null && dialog!!.isShowing){
            dialog?.dismiss()
            dialog = null
        }
    }

}