package com.kenzo.mobileplayer.presenter.impl

import com.itheima.player.model.bean.MvPagerBean
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.net.MvListRequest
import com.kenzo.mobileplayer.net.ResponseHandler
import com.kenzo.mobileplayer.presenter.interf.MvListPresenter
import com.kenzo.mobileplayer.view.MvListView

class MvListPresenterImpl(var code : String, var mvListView : MvListView?) : MvListPresenter,
    ResponseHandler<MvPagerBean> {
    override fun onError(type: Int, msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: MvPagerBean) {
        if (type == BaseLisatPresenter.TYPE_INIT_OR_REFRESH) {
            mvListView?.loadSuccess(result)
        } else if (type == BaseLisatPresenter.TYPE_LOAD_MORE) {
            mvListView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        MvListRequest(BaseLisatPresenter.TYPE_INIT_OR_REFRESH, code, 0, this).excute()
    }

    override fun loadMore(offset: Int) {
        MvListRequest(BaseLisatPresenter.TYPE_LOAD_MORE, code, offset, this).excute()
    }

    override fun destoryView() {
        if (mvListView != null) mvListView = null
    }
}