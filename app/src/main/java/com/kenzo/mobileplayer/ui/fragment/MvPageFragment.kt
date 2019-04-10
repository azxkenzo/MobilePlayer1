package com.kenzo.mobileplayer.ui.fragment


import com.itheima.player.model.VideoPlayBean
import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean
import com.kenzo.mobileplayer.adapter.MvListAdapter
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.base.BaseListAdapter
import com.kenzo.mobileplayer.base.BaseListFragment
import com.kenzo.mobileplayer.presenter.impl.MvListPresenterImpl
import com.kenzo.mobileplayer.ui.activity.VideoPlayerActivity
import com.kenzo.mobileplayer.ui.activity.jiaoziVideoPlayerActivity
import com.kenzo.mobileplayer.view.MvListView
import com.kenzo.mobileplayer.widget.MvItemView
import org.jetbrains.anko.support.v4.startActivity

class MvPageFragment : BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), MvListView {
    var code: String? = null
    override fun init() {
        code = arguments?.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseLisatPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun getList(response: MvPagerBean?): List<VideosBean>? {
        return response?.videos
    }

    override fun initListener() {
        super.initListener()

        adapter.setMyListener{
            val videoPlayerBean = VideoPlayBean(it.id, it.title, it.url)
            startActivity<jiaoziVideoPlayerActivity>("item" to videoPlayerBean)
        }
    }

}