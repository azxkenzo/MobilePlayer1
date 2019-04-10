package com.kenzo.mobileplayer.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.ui.activity.SettingActivity

/**
 * 所有界面toolbar的管理类
 */

interface ToolBarManager {

    val toolbar: Toolbar

    /**
     * 初始化主界面中的toolbar
     */
    fun initMainToolBar() {

        toolbar.setTitle("Mai音悦")
        toolbar.inflateMenu(R.menu.main)

        //如果java接口中只有一个未实现的方法，则可以直接省略接口对象，直接用 {} 表示未实现的方法
        toolbar.setOnMenuItemClickListener {
            toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))

            true
        }

        /*toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId) {
                    R.id.setting -> {
                        toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
                    }
                }
                return true
            }

        })*/
    }


    /**
     * 处理设置界面的toolbar
     */
    fun initSettingToolBar() {
        toolbar.setTitle("设置")
    }
}