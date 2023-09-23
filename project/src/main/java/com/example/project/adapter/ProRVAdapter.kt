package com.example.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project.R
import com.example.project.bean.p
import com.makeramen.roundedimageview.RoundedImageView
import okhttp3.internal.notifyAll
import util.Constants

class ProRVAdapter(var listener: ProjectClickListeener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
,View.OnClickListener{


    private var lastClickTime: Long = 0
    private lateinit var diff: AsyncListDiffer<p>
    private val NORMAL: Int =0
    private val  FOOT: Int = 1
    private val LAST: Int =2
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this,MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            NORMAL -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_pro_rv,parent,false)
                )
            }
            FOOT   -> {
                MyFootHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.example.common.R.layout.foot_rv,parent,false)
                )
            }
            else   -> {
                MyLastHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(com.example.common.R.layout.last_rv,parent,false)
                )
            }
        }
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

    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 1 else diff.currentList.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NORMAL){
            val data = diff.currentList[position]
            (holder as MyViewHolder).rimg.load(data.envelopePic)
            holder.desc.text = data.desc
            holder.title.text = data.title
            holder.name.text = data.author
            holder.timme.text = data.niceDate
            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)


            if (data.collect){
                holder.collect.setImageResource(com.example.common.R.drawable.icon_collect_2)
            }else{
                holder.collect.setImageResource(com.example.common.R.drawable.icon_collect_1)
            }

            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)

            holder.collect.tag = position
            holder.collect.setOnClickListener(this)
        }
    }

    fun setData(list:List<p>?){
        diff.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onClick(v: View?) {
        val  currentTime = System.currentTimeMillis()
        if (currentTime -lastClickTime > Constants.MIN_CLICK_DELAY_TIME){
            lastClickTime = currentTime

            if (v?.id ==R.id.img_collect){
                listener.onCollectClick(v?.tag as Int)
            }else{
                listener.onItemClick(v?.tag as Int)
            }
        }
    }

    inner class  MyViewHolder(item: View):RecyclerView.ViewHolder(item){
        var title: TextView = item.findViewById(R.id.title)
        var desc: TextView = item.findViewById(R.id.desc)
        var name: TextView = item.findViewById(R.id.name)
        var timme : TextView = item.findViewById(R.id.time)
        var rimg: RoundedImageView = item.findViewById(R.id.rimg)
        var collect: ImageView = item.findViewById(R.id.img_collect)
    }
    inner class MyFootHolder(item: View): RecyclerView.ViewHolder(item)
    inner class MyLastHolder(item: View): RecyclerView.ViewHolder(item)

    inner class MyCallback: DiffUtil.ItemCallback<p>(){
        override fun areItemsTheSame(oldItem: p, newItem: p): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: p, newItem: p): Boolean {
            return oldItem.title == newItem.title
        }

    }
}