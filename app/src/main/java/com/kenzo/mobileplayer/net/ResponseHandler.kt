package com.kenzo.mobileplayer.net

/**
 * 请求的回调
 */
interface ResponseHandler<RESPONSE> {

    fun onError(type: Int, msg: String?)

    fun onSuccess(type: Int, result: RESPONSE)
}