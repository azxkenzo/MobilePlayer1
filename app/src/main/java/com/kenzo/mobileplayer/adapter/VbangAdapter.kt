package com.kenzo.mobileplayer.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.itheima.player.model.AudioBean
import com.kenzo.mobileplayer.widget.VbangItemView

class VbangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return VbangItemView(context)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val itemView = view as VbangItemView
        val itemBean = AudioBean.getAudioBean(cursor)
        itemView.setData(itemBean)
    }
}