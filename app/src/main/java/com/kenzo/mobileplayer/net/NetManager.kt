package com.kenzo.mobileplayer.net


import com.kenzo.mobileplayer.util.ThreadUtil
import okhttp3.*
import java.io.IOException

/**
 * 发送网络请求类
 */
class NetManager private constructor() {

    val client by lazy { OkHttpClient() }


    companion object {
        val manager by lazy { NetManager() }
    }

    /**
     * 发送网络请求
     */
    fun <RESPONSE> sendRequest(req: MRequest<RESPONSE>) {
        val request = Request.Builder()
            .url(req.url)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 子线程调用
             */
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //回调到view层处理
                        req.handler.onError(req.type, e?.message)
                    }
                })
                //e!!.printStackTrace()

            }

            /**
             * 子线程调用
             */
            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val parseResult = req.parseResult(result)


                //刷新列表
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        //将结果回调到view层
                        req.handler.onSuccess(req.type, parseResult)
                    }

                })
            }

        })

    }
}