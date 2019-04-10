package com.kenzo.mobileplayer.base


/**
 * 所有具有下拉刷新和上划加载更多列表界面的View基类
 */
interface BaseView<RESPONSE> {
    /**
     * onError -> 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 初始化数据 / 刷新数据
     */
    fun loadSuccess(response: RESPONSE?)

    /**
     * 加载更多数据
     */
    fun loadMore(response: RESPONSE?)
}