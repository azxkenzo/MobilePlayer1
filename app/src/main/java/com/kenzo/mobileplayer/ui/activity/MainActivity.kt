package com.kenzo.mobileplayer.ui.activity


import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseActivity
import com.kenzo.mobileplayer.util.FragmentUtil
import com.kenzo.mobileplayer.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.info


class MainActivity : BaseActivity(), ToolBarManager {


    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initDate() {
        initMainToolBar()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FragmentUtil.fragmentUtil.getFragment(R.id.action_home)!!)
            .show(FragmentUtil.fragmentUtil.getFragment(R.id.action_home)!!).commit()


        info("mainactivity")
    }


    override fun initListener() {
        //设置tab切换监听
        navigation.setOnNavigationItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, FragmentUtil.fragmentUtil.getFragment(it.itemId)!!)
                .show(FragmentUtil.fragmentUtil.getFragment(it.itemId)!!).commit()
            true
        }


    }


    /**
     * 监听back键事件    设置点击back键时不关闭Activity
     */
    override fun onBackPressed() {
        //super.onBackPressed()
        val i = Intent(Intent.ACTION_MAIN)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addCategory(Intent.CATEGORY_HOME)
        startActivity(i)

    }


}
