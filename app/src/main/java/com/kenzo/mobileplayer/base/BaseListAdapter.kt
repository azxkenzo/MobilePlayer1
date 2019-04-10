package com.kenzo.mobileplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kenzo.mobileplayer.widget.LoadMoreView

/**
 * 所有具有下拉刷新和上划加载更多列表界面的adapter基类
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> : RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {
    private var list = ArrayList<ITEMBEAN>()
    /**
     * 更新列表
     */
    fun updateList(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }

    }

    fun loadMore(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {
            return 1
        } else {
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListHolder {
        if (viewType == 1) {
            return BaseListHolder(LoadMoreView(parent?.context))
        } else {
            return BaseListHolder(getItemView(parent.context))
        }
    }


    override fun getItemCount(): Int {
        return list.size + 1
    }


    override fun onBindViewHolder(holder: BaseListAdapter.BaseListHolder, position: Int) {
        if (position == list.size) return

        val data = list.get(position)
        val itemView = holder.itemView as ITEMVIEW
        refreshItemView(itemView, data)

        itemView.setOnClickListener {
            listener?.let {
                it(data)
            }
        }
    }

    var listener:((itemBean : ITEMBEAN)->Unit)? = null
    fun setMyListener(listener: (itemBean : ITEMBEAN)->Unit) {
        this.listener = listener
    }


    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    /**
     * 刷新条目View
     */
    abstract fun refreshItemView(itemView: ITEMVIEW, data: ITEMBEAN)

    /**
     * 获取条目view
     */
    abstract fun getItemView(context: Context?): ITEMVIEW
}