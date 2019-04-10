package com.kenzo.mobileplayer.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.kenzo.mobileplayer.R
import org.jetbrains.anko.find

class PlayListPopWindow(context: Context, adapter: BaseAdapter, listener: AdapterView.OnItemClickListener,val window: Window) : PopupWindow() {
    var alpha: Float = 0f
    init {
        alpha = window.attributes.alpha

        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        //获取listview
        val listView = view.find<ListView>(R.id.listView)
        //适配
        listView.adapter = adapter
        //设置列表条目点击事件
        listView.setOnItemClickListener(listener)
        contentView = view
        //设置高度宽度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val windowH = point.y
        height = (windowH / 5) * 3
        //设置获取焦点
        isFocusable = true
        //设置外部点击
        isOutsideTouchable = true
        //能够响应返回按钮
        setBackgroundDrawable(ColorDrawable())
        //处理动画
        animationStyle = R.style.pop
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val attributes = window.attributes
        attributes.alpha = 0.4f
        //设置到应用程序窗体上
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        //恢复透明度
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes

    }
}