package com.kenzo.mobileplayer.ui.activity

import com.itheima.player.model.VideoPlayBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_player
    }

    override fun initDate() {
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        videoView.setVideoPath(videoPlayBean.url)
        videoView.setOnPreparedListener {
            videoView.start()
        }
    }
}