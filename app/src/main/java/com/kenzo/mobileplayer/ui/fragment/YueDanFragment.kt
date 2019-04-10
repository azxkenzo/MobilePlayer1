package com.kenzo.mobileplayer.ui.fragment


import com.itheima.player.model.bean.YueDanBean
import com.kenzo.mobileplayer.adapter.YueDanAdapter
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.base.BaseListFragment
import com.kenzo.mobileplayer.presenter.impl.YueDanPresenterImpl
import com.kenzo.mobileplayer.widget.YueDanItemView

/**
 * 乐单界面
 */
class YueDanFragment : BaseListFragment<YueDanBean, YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getSpecialPresenter(): BaseLisatPresenter {
        return YueDanPresenterImpl(this)
    }

    override fun getList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists
    }




}