package com.example.home.adapter

import android.widget.ImageView
import coil.load
import com.example.home.R
import com.example.home.bean.Banner
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter : BaseBannerAdapter<Banner>(){
    override fun bindData(
        holder: BaseViewHolder<Banner>?,
        data: Banner?,
        position: Int,
        pageSize: Int
    ) {
        holder?.findViewById<ImageView>(R.id.image_banner)?.load(data?.imagePath)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }
}