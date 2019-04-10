package com.kenzo.mobileplayer.view

import com.itheima.player.model.bean.MvAreaBean

interface MvView {

    fun onError(msg: String?)

    fun onSuccess(result: List<MvAreaBean>)

}