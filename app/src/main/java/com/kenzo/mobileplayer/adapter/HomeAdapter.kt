package com.kenzo.mobileplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.HomeItemBean
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.widget.HomeItemView

/**
 * 主页适配器
 */
class HomeAdapter : BaseListAdapter<HomeItemBean, HomeItemView>() {
    override fun refreshItemView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }

}