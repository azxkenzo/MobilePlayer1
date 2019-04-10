package com.kenzo.mobileplayer.util

import android.os.Handler
import android.os.Looper


object ThreadUtil {
    val handler = Handler(Looper.getMainLooper());
    /**
     * 在主线程调用
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}