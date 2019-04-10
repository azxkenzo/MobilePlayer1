package com.kenzo.mobileplayer.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.ui.activity.AboutActivity


class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val key = preference?.key
        if ("about".equals(key)) {
            context?.startActivity(Intent(context, AboutActivity::class.java))
        }
        return super.onPreferenceTreeClick(preference)
    }


}