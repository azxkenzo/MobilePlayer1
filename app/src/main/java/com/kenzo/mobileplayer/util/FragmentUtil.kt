package com.kenzo.mobileplayer.util

import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseFragment
import com.kenzo.mobileplayer.ui.fragment.HomeFragment
import com.kenzo.mobileplayer.ui.fragment.MvFragment
import com.kenzo.mobileplayer.ui.fragment.VbangFragment
import com.kenzo.mobileplayer.ui.fragment.YueDanFragment

class FragmentUtil private constructor() {    //私有化构造方法

    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { MvFragment() }
    val vbangFragment by lazy { VbangFragment() }
    val yuedanFragment by lazy { YueDanFragment() }

    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }

    /**
     * 根据tabid获取对应的fragment
     */
    fun getFragment(tabId: Int): BaseFragment? {
        when (tabId) {
            R.id.action_home -> return homeFragment
            R.id.action_mv -> return mvFragment
            R.id.action_vbang -> return vbangFragment
            R.id.action_yuedan -> return yuedanFragment
        }
        return null
    }


}