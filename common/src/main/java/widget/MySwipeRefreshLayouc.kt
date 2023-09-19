package widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import util.Extension.dp2px
import kotlin.math.abs

/**
 * 自定义view布局刷新，处理滑动冲突
 * */
class MySwipeRefreshLayouc(
    context: Context, attr: AttributeSet
) :SwipeRefreshLayout(context,attr){
    var vpIsDragging : Boolean = false
    var x1 = 0f
    var y1 = 0f
    var x2 = 0f
    var y2 = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN ->{
                x1 = ev.x
                x2 = ev.y
            }
            MotionEvent.ACTION_MOVE ->{
                if (vpIsDragging){
                    return false
                }
                x2 = ev.x
                y2 =ev.y
                if (abs(x1-x2) > abs(y1-y2)
                    && y1<= 180.dp2px()){
                    vpIsDragging = true
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


}