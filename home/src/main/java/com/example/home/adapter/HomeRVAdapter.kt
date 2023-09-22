package com.example.home.adapter

import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.home.R
import com.stew.kb_home.bean.a
import util.Constants


class HomeRVAdapter(var listener: HomeItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() ,
     View.OnClickListener{

    //存放收藏的item
    private lateinit var diff: AsyncListDiffer<a>

    //正常加载item
    private val NORMAL = 0

    //快速滑动加载item
    private val FOOT: Int = 1
    //到底
    private val LAST: Int = 2
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this,MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            NORMAL ->{
                MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_home,parent,false)
                )
            }
            FOOT  ->{
                MyFootHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.example.common.R.layout.foot_rv,parent,false)
                )
            }
            else  ->{
                MyLastHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.example.common.R.layout.last_rv,parent,false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 1 else diff.currentList.size +1

    }

    fun setData(list: List<a>?){
        //AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(if (list !=null) ArrayList(list) else null)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount -1){
            if (isLastPage){
                LAST
            }else{
                FOOT
            }
        }else{
            NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("HomeRVAdapter", "onBindViewHolder: position = $position")
        if (getItemViewType(position) ==NORMAL){
            val data = diff.currentList[position]
           //网络获取的数据
        }
    }

    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
       val currentTime = System.currentTimeMillis()
       if (currentTime - lastClickTime> Constants.MIN_CLICK_DELAY_TIME){
           lastClickTime = currentTime

           if (v?.id == R.id.img_collect){
               listener.onCollectClick(v?.tag as Int)
           }else{
               listener.onItemClick(v?.tag as Int)
           }
       }
    }




    inner class MyViewHolder(item: View):RecyclerView.ViewHolder(item){
        var title: TextView = item.findViewById(R.id.title)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
        var type: TextView = item.findViewById(R.id.type)
        var tag1: TextView = item.findViewById(R.id.tag1)
        var tag2: TextView = item.findViewWithTag(R.id.tag2)
        var collect: ImageView = item.findViewById(R.id.img_collect)

    }

    inner class MyFootHolder(item: View):RecyclerView.ViewHolder(item)
    inner class MyLastHolder(item: View): RecyclerView.ViewHolder(Item)

    //还未实现
    inner class  MyCallback: DiffUtil.ItemCallback<a>(){
        override fun areItemsTheSame(oldItem: a, newItem: a): Boolean {

        }

        override fun areContentsTheSame(oldItem: a, newItem: a): Boolean {
            TODO("Not yet implemented")
        }

    }


}