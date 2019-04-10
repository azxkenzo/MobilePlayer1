package com.kenzo.mobileplayer.util

import com.itheima.player.model.LyricBean
import java.io.*
import java.nio.charset.Charset

object LyricUtil {
    fun parseLyric(file: File): List<LyricBean> {
        //创建集合
        val list = ArrayList<LyricBean>()
        //判断歌词是否为空
        if (!file.exists()) {
            list.add(LyricBean(0, "歌词加载失败"))
            return list
        } else {
            //解析歌词文件   添加到集合中
            val linesList = file.readLines(Charset.forName("gbk"))   //读取歌词文件，返回每一行歌词集合
            for (line in linesList) {
                //解析一行
                val lineList: List<LyricBean> = parseLine(line)
                //添加到集合中
                list.addAll(lineList)
            }

        }
        return list

    }

    /**
     * 解析一行歌词
     */
    private fun parseLine(line: String): List<LyricBean> {
        //创建集合
        val list = ArrayList<LyricBean>()
        //解析当前行
        val arr = line.split("]")
        //获取歌词内容
        val content = arr.get(arr.size - 1)
        for (index in 0 until arr.size - 1) {
            val startTime: Int = parseTime(arr.get(index))
            list.add(LyricBean(startTime, content))
        }
        //歌词排序
        list.sortBy { it.startTime }
        return list
    }

    /**
     * 解析时间
     */
    private fun parseTime(get: String): Int {
        val timeS = get.substring(1)
        val list = timeS.split(":")
        var hour = 0
        var min = 0
        var sec = 0f
        if (list.size == 3) {
            hour = (list[0].toInt()) * 60 * 60 * 1000
            min = (list[1].toInt()) * 60 * 1000
            sec = (list[2].toFloat()) * 1000
        } else {
            min = (list[1].toInt()) * 60 * 1000
            sec = (list[2].toFloat()) * 1000
        }
        return (hour + min + sec).toInt()
    }
}