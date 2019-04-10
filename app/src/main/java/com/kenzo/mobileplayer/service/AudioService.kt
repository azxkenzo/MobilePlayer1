package com.kenzo.mobileplayer.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.RemoteViews
import com.itheima.player.model.AudioBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.ui.activity.AboutActivity
import com.kenzo.mobileplayer.ui.activity.AudioPlayerActivity
import com.kenzo.mobileplayer.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import kotlin.random.Random

class AudioService : Service(), AnkoLogger {
    var managerCompat: NotificationManagerCompat? = null
    var manager: NotificationManager? = null
    var mediaPlayer: MediaPlayer? = null
    var list: ArrayList<AudioBean>? = null
    var position: Int = -3
    var notification: Notification? = null

    val channelId = "Mai Player"
    val channelName = "music play"
    val FROM_PRE = 1
    val FROM_NEXT = 2
    val FROM_STATE = 3
    val FROM_CONTENT = 4
    val binder by lazy { AudioBinder() }
    val sp by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }


    companion object {
        val MODE_ALL = 1
        val MODE_SINGLE = 2
        val MODE_RANDOW = 3
    }

    var mode = MODE_ALL

    override fun onCreate() {
        super.onCreate()
        mode = sp.getInt("mode", MODE_ALL)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //判断从哪里进入的service   通知栏点击事件
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_PRE -> {
                binder.playPre()
            }
            FROM_NEXT -> {
                binder.playNext()
            }
            FROM_STATE -> {
                binder.updatePlayState()
                binder.notifyUpdateUi()
            }
            FROM_CONTENT -> {
                binder.notifyUpdateUi()
            }
            else -> {
                val pos = intent?.getIntExtra("position", -1) ?: -1
                if (pos != position) {
                    position = pos
                    //获取list和position
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    //开始播放音乐
                    binder.playItem()
                } else {
                    //主动通知界面更新
                    binder.notifyUpdateUi()
                }
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class AudioBinder : Binder(), IService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         * 播放 播放列表内的的歌曲
         */
        override fun playPosition(position: Int) {
            this@AudioService.position = position
            playItem()
        }

        override fun getPlayList(): List<AudioBean>? {
            return list
        }

        override fun playPre() {
            list?.let {
                when (mode) {
                    MODE_RANDOW -> position = Random.nextInt(it.size - 1)
                    else -> if (position == 0) {
                        position = it.size - 1
                    } else position--
                }
                playItem()
            }
        }

        override fun playNext() {
            list?.let {
                when (mode) {
                    MODE_RANDOW -> position = Random.nextInt(it.size - 1)
                    else -> position = (position + 1) % it.size
                }
                playItem()
            }
        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode(): Int {
            return mode
        }

        /**
         * 修改播放模式
         */
        override fun updatePlayMode() {
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOW
                MODE_RANDOW -> mode = MODE_ALL
            }
            sp.edit().putInt("mode", mode).commit()
        }

        /**
         * 歌曲播放完成回调
         */
        override fun onCompletion(mp: MediaPlayer?) {
            autoPlayNext()
        }

        /**
         * 根据播放模式自动播放下一曲
         */
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> list?.let {
                    position = (position + 1) % it.size
                }
                //MODE_SINGLE ->
                MODE_RANDOW -> list?.let {
                    position = Random.nextInt(it.size)
                }
            }
            playItem()
        }

        /**
         * 更新播放进度
         */
        override fun seekTO(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        /**
         * 获取当前进度
         */
        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        /**
         * 获取总进度
         */
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        /**
         * 更新播放状态
         */
        override fun updatePlayState() {
            //获取当前播放状态
            val isPlaying = isPlaying()
            //切换播放状态
            isPlaying?.let {
                if (it) {
                    mediaPlayer?.pause()
                    notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
                    managerCompat!!.notify(1, notification!!)
                } else {
                    mediaPlayer?.start()
                    notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
                    managerCompat!!.notify(1, notification!!)
                }
            }
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
            //通知界面更新
            notifyUpdateUi()
            //显示通知
            showNotification()
        }

        /**
         * 显示通知
         */
        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager?.createNotificationChannel(getChannel())
            notification = getNotification()
            managerCompat = NotificationManagerCompat.from(this@AudioService)
            managerCompat?.notify(1, notification!!)
        }

        private fun getChannel(): NotificationChannel {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            println("info")
            info(channel.toString())
            return channel
        }

        private fun getNotification(): Notification {
            var notification = NotificationCompat.Builder(this@AudioService, channelId)
                .setTicker("正在播放:${list?.get(position)?.display_name}")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("${list?.get(position)?.display_name}")
                .setContentText("${list?.get(position)?.artist}")
                .setCustomContentView(getRemoteViews())  //自定义通知栏view
                //.setWhen(System.currentTimeMillis())
                .setOngoing(true)      //设置不能滑动删除通知
                .setContentIntent(getPendingIntent())    //通知栏主体点击事件
                .build()

            return notification
        }

        /**
         * 通知栏主体点击事件
         */
        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService, MainActivity::class.java)
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)
            // .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            intentA.putExtra("from", FROM_CONTENT)
            val intents = arrayOf(intentM, intentA)

            val pendingIntent =
                PendingIntent.getActivities(this@AudioService, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT)

            return pendingIntent
        }

        /**
         * 自定义通知栏view
         */
        private fun getRemoteViews(): RemoteViews? {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            //修改标题和内容
            remoteViews.setTextViewText(R.id.title, list?.get(position)?.display_name)
            remoteViews.setTextViewText(R.id.artist, list?.get(position)?.artist)
            //处理上一曲 下一曲 播放暂停 点击事件
            remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
            return remoteViews
        }

        /**
         * 通知栏下一曲
         */
        private fun getNextPendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_NEXT)
            val pendingIntent =
                PendingIntent.getService(this@AudioService, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 通知栏播放 暂停
         */
        private fun getStatePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_STATE)
            val pendingIntent =
                PendingIntent.getService(this@AudioService, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 通知栏上一曲
         */
        private fun getPrePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_PRE)
            val pendingIntent =
                PendingIntent.getService(this@AudioService, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }


        /**
         * 通知更新UI
         */
        fun notifyUpdateUi() {
            //发送端
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            if (mediaPlayer != null) {
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }

            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
            }
        }
    }


}