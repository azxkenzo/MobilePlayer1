package com.kenzo.mobileplayer.ui.activity

import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseActivity
import org.jetbrains.anko.info

class AboutActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        info("aboutactivity")
        return R.layout.activty_about
    }

}