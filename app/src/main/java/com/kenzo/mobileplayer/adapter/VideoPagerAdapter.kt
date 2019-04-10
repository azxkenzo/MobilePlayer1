package com.kenzo.mobileplayer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kenzo.mobileplayer.ui.fragment.DefaultFragment

class VideoPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return DefaultFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}