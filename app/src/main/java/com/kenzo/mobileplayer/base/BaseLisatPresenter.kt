package com.kenzo.mobileplayer.base

/**
 * 所有具有下拉刷新和上划加载更多列表界面的Presenter基类
 */
interface BaseLisatPresenter {
    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }


    fun loadDatas()
    fun loadMore(offset: Int)
    //解绑presenter和view
    fun destoryView()
}