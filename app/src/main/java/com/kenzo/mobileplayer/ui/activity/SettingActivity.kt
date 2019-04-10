package com.kenzo.mobileplayer.ui.activity

import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseActivity
import com.kenzo.mobileplayer.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * 设置界面
 */

class SettingActivity : BaseActivity(), ToolBarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }


    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initDate() {
        initSettingToolBar()

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val push = sp.getBoolean("push", false)
        println(push)


    }
}