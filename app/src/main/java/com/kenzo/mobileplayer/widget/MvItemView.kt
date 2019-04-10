package com.kenzo.mobileplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.VideosBean
import com.kenzo.mobileplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.mv_item.view.*

class MvItemView : RelativeLayout {

    fun setData(data: VideosBean) {
        artist.text = data.artistName
        title.text = data.title
        Picasso.with(context).load(data.playListPic).into(bg)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.mv_item, this)
    }
}