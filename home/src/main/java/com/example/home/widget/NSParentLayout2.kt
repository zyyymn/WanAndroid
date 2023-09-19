package com.example.home.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper

class NSParentLayout2(
    context: Context,
    attr:AttributeSet
):LinearLayout(context,attr),NestedScrollingParent2 {

    private lateinit var npHelper: NestedScrollingParentHelper
    var p: LayoutParams?=null
    lateinit var topView: View
    lateinit var  bottomView: View
    var topViewHeight:Int = 0

    init {

        orientation = VERTICAL
        npHelper = NestedScrollingParentHelper(this)
        p = LayoutParams(LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT)

    }



    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
       return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        npHelper.onNestedScrollAccepted(child,target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        npHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {


    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val FLAG_TOP_ON = dy > 0 && scaleY< topViewHeight
        val FLAG_TOP_OFF= dy < 0 && scaleY> 0 && !target.canScrollVertically(-1)
        if (FLAG_TOP_ON || FLAG_TOP_OFF){
            scrollBy(0,dy)
            consumed[1] = dy
        }

        (parent as SwipeRefreshLayout).isEnabled =
            !(parent is SwipeRefreshLayout && scaleY> 0)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

}