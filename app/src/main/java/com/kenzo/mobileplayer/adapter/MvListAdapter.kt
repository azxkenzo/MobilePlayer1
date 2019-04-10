package com.kenzo.mobileplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.VideosBean
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.widget.MvItemView

class MvListAdapter : BaseListAdapter<VideosBean, MvItemView>() {
    override fun refreshItemView(itemView: MvItemView, data: VideosBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }
}