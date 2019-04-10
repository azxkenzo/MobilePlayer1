package com.kenzo.mobileplayer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 所有Activity的基类
 */

abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initDate()


    }

    /**
     * 初始化数据
     */
    open protected fun initDate() {

    }

    /**
     * adapter listener
     */
    open protected fun initListener() {

    }


    /**
     * 获取布局ID
     */
    abstract fun getLayoutId(): Int


    protected fun myToast(message: String) {
        runOnUiThread { toast(message) }
    }

    inline fun <reified T:BaseActivity> startActivityAndFinish() {
        startActivity<T>()
        finish()
    }
}