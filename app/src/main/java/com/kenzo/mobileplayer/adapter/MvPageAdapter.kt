package com.kenzo.mobileplayer.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.itheima.player.model.bean.MvAreaBean
import com.kenzo.mobileplayer.ui.fragment.MvPageFragment

class MvPageAdapter(val list: List<MvAreaBean>?, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment = MvPageFragment()
        val bundle = Bundle()
        bundle.putString("args", list?.get(position)?.code)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return list?.size?:0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }
}