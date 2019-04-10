package com.kenzo.mobileplayer.adapter

import android.content.Context
import com.itheima.player.model.bean.YueDanBean
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.widget.YueDanItemView

/**
 * 乐单界面适配器
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun refreshItemView(itemView: YueDanItemView, data: YueDanBean.PlayListsBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): YueDanItemView {
        return YueDanItemView(context)
    }


}