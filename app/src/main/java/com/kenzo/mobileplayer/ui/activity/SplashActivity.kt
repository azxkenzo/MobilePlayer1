package com.kenzo.mobileplayer.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), ViewPropertyAnimatorListener {

    override fun onAnimationEnd(p0: View?) {
        //进入主界面
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(p0: View?) {
    }

    override fun onAnimationStart(p0: View?) {
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initDate() {
        ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f).setListener(this).setDuration(2000)
    }
}