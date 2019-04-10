package com.kenzo.mobileplayer.ui.fragment


import com.itheima.player.model.bean.HomeItemBean
import com.kenzo.mobileplayer.adapter.HomeAdapter
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.base.BaseListFragment
import com.kenzo.mobileplayer.presenter.impl.HomePresenterImpl
import com.kenzo.mobileplayer.widget.HomeItemView

/**
 * 主页界面
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {
    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getSpecialPresenter(): BaseLisatPresenter {
        return HomePresenterImpl(this)
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑presenter
        presenter.destoryView()
    }

}