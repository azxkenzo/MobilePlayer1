package com.kenzo.mobileplayer.util

import android.os.Environment
import java.io.File

object LyricLoader {
    val dir = File(Environment.getExternalStorageDirectory(), "Download/Lyric")

    fun loadLyricFile(display_name: String): File {
        return File(dir, display_name+".lrc")
    }
}