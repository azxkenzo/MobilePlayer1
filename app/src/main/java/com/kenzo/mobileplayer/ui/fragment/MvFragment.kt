package com.kenzo.mobileplayer.ui.fragment


import android.view.View
import com.itheima.player.model.bean.MvAreaBean
import com.kenzo.mobileplayer.R
import com.kenzo.mobileplayer.adapter.MvPageAdapter
import com.kenzo.mobileplayer.base.BaseFragment
import com.kenzo.mobileplayer.presenter.impl.MvPresenterImpl
import com.kenzo.mobileplayer.view.MvView
import kotlinx.android.synthetic.main.fragment_mv.*

class MvFragment : BaseFragment(), MvView {
    override fun onError(msg: String?) {
        myToast("加载数据失败")
    }

    override fun onSuccess(result: List<MvAreaBean>) {
        val adapter = MvPageAdapter(result, childFragmentManager)
        viewPage.adapter = adapter
        tabLayout.setupWithViewPager(viewPage)
    }

    val presenter by lazy { MvPresenterImpl(this) }


    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_mv, null)
    }

    override fun initListener() {

    }

    override fun initDate() {
        //加载区域数据
        presenter.loadDatas()
    }
}