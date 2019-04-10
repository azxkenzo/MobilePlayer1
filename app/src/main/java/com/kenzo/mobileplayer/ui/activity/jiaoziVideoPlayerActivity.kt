package com.kenzo.mobileplayer.ui.activity

import com.itheima.player.model.VideoPlayBean
import com.kenzo.mobileplayer.base.BaseActivity
import cn.jzvd.Jzvd
import com.kenzo.mobileplayer.R
import kotlinx.android.synthetic.main.activity_video_player_jiaozi.*
import android.provider.MediaStore
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.support.v4.view.ViewPager
import com.kenzo.mobileplayer.adapter.VideoPagerAdapter


class jiaoziVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_jiaozi
    }

    override fun initDate() {
        val data = intent.data
        println(data)


        if (data == null) {
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            videoplayer.setUp(
                videoPlayBean.url,
                videoPlayBean.title,
                Jzvd.SCREEN_WINDOW_NORMAL
            )
        } else if (data != null) {
            val dataurl = getRealPathFromUri(this, data)
            println(dataurl)
            videoplayer.setUp(
                dataurl.toString(),
                dataurl.toString(),
                Jzvd.SCREEN_WINDOW_NORMAL
            )

        }


    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun initListener() {
        viewPager.adapter = VideoPagerAdapter(supportFragmentManager)

        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1 -> viewPager.setCurrentItem(0)
                R.id.rb2 -> viewPager.setCurrentItem(1)
                R.id.rb3 -> viewPager.setCurrentItem(2)
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> rg.check(R.id.rb1)
                    1 -> rg.check(R.id.rb2)
                    2 -> rg.check(R.id.rb3)
                }
            }

        })
    }

    /**
     * android中转换content://media/external/images/media/539163为/storage/emulated/0/DCIM/Camera/IMG_20160807_
     */
    private fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Video.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }

}
