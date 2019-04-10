package com.kenzo.mobileplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.itheima.player.model.AudioBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.adapter.PopAdapter
import com.kenzo.mobileplayer.base.BaseActivity
import com.kenzo.mobileplayer.service.AudioService
import com.kenzo.mobileplayer.service.IService
import com.kenzo.mobileplayer.util.StringUtil
import com.kenzo.mobileplayer.widget.LyricView
import com.kenzo.mobileplayer.widget.PlayListPopWindow
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.info

class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    AdapterView.OnItemClickListener {

    /**
     * pop条目点击事件
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //播放点击的歌曲
        iService?.playPosition(position)
    }

    /**
     * 进度改变回调
     * progress 改变后的进度
     * fromUser  true--用户手动改变  false--通过代码改变
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        //判断是否是用户手动改变
        if (!fromUser) return
        //更新播放进度
        iService?.seekTO(progress)
        //更新进度界面显示
        updateProgress(progress)
    }

    /**
     * 手指触摸回调
     */
    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    var audioBean: AudioBean? = null

    var drawable: AnimationDrawable? = null

    var duration: Int = 0

    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }

    val MSG_PROGRESS = 0

    /**
     * 点击事件监听
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()

        }
    }

    /**
     * 显示播放列表
     */
    private fun showPlayList() {
        val list = iService?.getPlayList()
        list?.let {
            val adapter = PopAdapter(it)
            val btmH = audio_player_bottom.height
            val popWindow = PlayListPopWindow(this, adapter, this, window)
            popWindow.showAsDropDown(audio_player_bottom, 0, btmH)
        }
    }

    /**
     * 切换播放模式
     */
    private fun updatePlayMode() {
        //修改service中的mode
        iService?.updatePlayMode()
        //修改按钮图标
        updatePlayModeBtn()
    }

    /**
     * 更新切换模式按钮图标
     */
    private fun updatePlayModeBtn() {
        iService?.let {
            //获取播放模式
            val modei: Int = it.getPlayMode()
            //设置图标
            when (modei) {
                AudioService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
                AudioService.MODE_SINGLE -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
                AudioService.MODE_RANDOW -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
        }
    }

    /**
     * 接收eventbus
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public fun updateView(itemBean: AudioBean) {
        lyricView.setSongName(itemBean.display_name)
        //记录
        this.audioBean = itemBean
        //更新视图
        audio_title.text = itemBean.display_name
        artist.text = itemBean.artist
        //动画播放
        drawable = audio_anim.drawable as AnimationDrawable
        //设置歌词播放总进度
        drawable?.start()
        //获取总进度
        duration = iService?.getDuration() ?: 0
        lyricView.setSongDuration(duration)
        //设置进度条最大值
        progress_sk.max = duration
        //更新播放进度
        startUpdateProgress()
        //更新播放模式图标
        updatePlayModeBtn()
        //更新播放状态图标
        updatePlayStateBtn()


    }

    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        //获取当前进度
        val progres = iService?.getProgress() ?: 0
        //更新进度
        updateProgress(progres)
        //定时更新进度
        handler.sendEmptyMessage(MSG_PROGRESS)

    }

    /**
     * 根据当前进度数据更新进度条
     */
    private fun updateProgress(progres: Int) {
        //更新进度数值
        progress.text = StringUtil.parseDurion(progres) + "/" + StringUtil.parseDurion(duration)
        //更新进度条
        progress_sk.setProgress(progres)
        //更新歌词播放进度
        lyricView.uodateProgress(progres)

    }

    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新播放状态
        iService?.updatePlayState()
        //更新播放状态图标
        updatePlayStateBtn()

    }

    /**
     * 更新播放状态图标
     */
    private fun updatePlayStateBtn() {
        //获取播放状态
        val isPlaying = iService?.isPlaying()
        //更新图标
        isPlaying?.let {
            if (it) {
                state.setImageResource(R.drawable.selector_btn_audio_play)
                drawable?.start()
                handler.sendEmptyMessage(MSG_PROGRESS)
            } else {
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                drawable?.stop()
                //停止更新进度条
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    val conn by lazy { AudioConnection() }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        //返回按钮点击事件
        back.setOnClickListener { finish() }
        //拖动进度条更新事件监听
        progress_sk.setOnSeekBarChangeListener(this)
        //切换模式按钮事件监听
        mode.setOnClickListener(this)
        //上、下一曲点击事件
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)
        //歌词拖进的进度更新监听
        lyricView.setProgressListener {
            //更新播放进度
            iService?.seekTO(it)
            //更新进度显示
            startUpdateProgress()
        }
    }

    override fun initDate() {
        //注册Eventbus
        EventBus.getDefault().register(this)


//        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
//        val position = intent.getIntExtra("position", -1)

        //通过AudioService播放音乐
//        val intent = Intent(this, AudioService::class.java)
        //通过intent讲list和position传递过去
//        intent.putExtra("list", list)
//        intent.putExtra("position", position)

        val intent = intent
        intent.setClass(this, AudioService::class.java)

        //先绑定
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        //在开启
        startService(intent)

//        //播放音乐
//        val mediaPlayer = MediaPlayer()
//        mediaPlayer.setOnPreparedListener {
//            mediaPlayer.start()
//        }
//        mediaPlayer.setDataSource(list.get(position).data)
//        mediaPlayer.prepareAsync()

        info("audioactivity")
    }

    var iService: IService? = null

    inner class AudioConnection : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as IService
        }

        /**
         * 意外断开连接时
         */
        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑服务
        unbindService(conn)
        //反注册eventbus
        EventBus.getDefault().unregister(this)
        //清空handler发送的所有消息   防止内存泄漏
        handler.removeCallbacksAndMessages(null)
    }
}