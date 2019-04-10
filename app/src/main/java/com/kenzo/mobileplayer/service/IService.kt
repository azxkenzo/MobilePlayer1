package com.kenzo.mobileplayer.service

import com.itheima.player.model.AudioBean

interface IService {
    fun updatePlayState()

    fun isPlaying(): Boolean?

    fun getDuration(): Int

    fun getProgress(): Int

    fun seekTO(progress: Int)

    fun updatePlayMode()

    fun getPlayMode(): Int

    fun playPre()

    fun playNext()

    fun getPlayList(): List<AudioBean>?

    fun playPosition(position: Int)
}