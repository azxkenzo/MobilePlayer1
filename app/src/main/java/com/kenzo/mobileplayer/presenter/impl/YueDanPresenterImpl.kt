package com.kenzo.mobileplayer.presenter.impl

import com.itheima.player.model.bean.YueDanBean
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.base.BaseView
import com.kenzo.mobileplayer.net.ResponseHandler
import com.kenzo.mobileplayer.net.YueDanRequest
import com.kenzo.mobileplayer.presenter.interf.YueDanPresenter
import com.kenzo.mobileplayer.view.YueDanView

class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResponseHandler<YueDanBean> {
    override fun destoryView() {
        if (yueDanView != null) yueDanView = null
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        if (type == BaseLisatPresenter.TYPE_INIT_OR_REFRESH) {
            yueDanView?.loadSuccess(result)
        } else {
            yueDanView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        YueDanRequest(BaseLisatPresenter.TYPE_INIT_OR_REFRESH, 0, this).excute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(BaseLisatPresenter.TYPE_LOAD_MORE, offset,this).excute()
    }
}