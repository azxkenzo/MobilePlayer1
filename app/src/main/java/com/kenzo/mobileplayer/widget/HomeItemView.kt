package com.kenzo.mobileplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.HomeItemBean
import com.kenzo.mobileplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * 主页每个条目的自定义View
 */
class HomeItemView : RelativeLayout {
    /**
     * 刷新条目View数据
     */
    fun setData(data: HomeItemBean) {
        //歌曲名称
        title.setText(data.title)
        //简介
        desc.setText(data.description)
        //背景图片
        Picasso.with(context).load(data.posterPic).into(bg)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_home, this)
    }
}