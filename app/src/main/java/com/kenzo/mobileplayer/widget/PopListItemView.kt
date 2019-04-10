package com.kenzo.mobileplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.itheima.player.model.AudioBean
import com.kenzo.mobileplayer.R
import kotlinx.android.synthetic.main.item_pop.view.*

class PopListItemView: RelativeLayout {
    fun setData(data: AudioBean) {
        title.text  = data.display_name
        artist.text = data.artist
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_pop, this)
    }
}