package com.kenzo.mobileplayer.presenter.impl

import com.itheima.player.model.bean.MvAreaBean
import com.kenzo.mobileplayer.net.MvAreaRequest
import com.kenzo.mobileplayer.net.ResponseHandler
import com.kenzo.mobileplayer.presenter.interf.MvPresenter
import com.kenzo.mobileplayer.view.MvView

class MvPresenterImpl(var mvView: MvView) : MvPresenter, ResponseHandler<List<MvAreaBean>> {

    override fun onError(type: Int, msg: String?) {
        mvView.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
        mvView.onSuccess(result)
    }

    override fun loadDatas() {
        MvAreaRequest(this).excute()
    }
}