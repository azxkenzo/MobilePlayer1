package com.kenzo.mobileplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.bean.YueDanBean
import com.kenzo.mobileplayer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * 乐单界面每个条目的自定义view
 */
class YueDanItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan, this)
    }

    fun setData(data: YueDanBean.PlayListsBean) {
        title.text = data.title
        author_name.text = data.creator?.nickName
        count.text = data.videoCount.toString()
        Picasso.with(context).load(data.playListBigPic).into(bg)
        Picasso.with(context).load(data.creator?.largeAvatar).into(author_image)
    }
}