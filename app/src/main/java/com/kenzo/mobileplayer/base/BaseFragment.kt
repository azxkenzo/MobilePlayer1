package com.kenzo.mobileplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.support.v4.toast

/**
 * 所有fragment的基类
 */

abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initDate()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return initView()
    }

    /**
     * fragment初始化
     */
    protected open fun init() {

    }

    /**
     * 获取布局view
     */
    abstract fun initView(): View?


    /**
     * 数据初始化
     */
    protected open fun initDate() {

    }

    /**
     * adapter listener
     */
    protected open fun initListener() {

    }

    fun myToast(msg: String) {
        context!!.runOnUiThread { toast(msg) }      //!!
    }
}