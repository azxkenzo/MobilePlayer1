package com.kenzo.mobileplayer.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kenzo.mobileplayer.R

import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 所有具有下拉刷新和上划加载更多列表界面的基类
 */
abstract class BaseListFragment<RESPONSE, ITEMBEAN, ITEMVIEW : View> : BaseFragment(), BaseView<RESPONSE> {

    override fun loadMore(response: RESPONSE?) {
        adapter.loadMore(getList(response))
    }

    override fun onError(message: String?) {
        myToast("加载数据失败")
        println(message)
    }

    override fun loadSuccess(response: RESPONSE?) {
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
        //刷新列表
        adapter.updateList(getList(response))
    }


    //适配
    val adapter by lazy { getSpecialAdapter() }

    val presenter by lazy { getSpecialPresenter() }


    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }

    override fun initListener() {
        //初始化recycler_view
        recycler_view.layoutManager = LinearLayoutManager(context)

        recycler_view.adapter = adapter

        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            presenter.loadDatas()
        }
        //监听列表滑动
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                /*when(newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> println("idel")
                    RecyclerView.SCROLL_STATE_DRAGGING -> println("dragging")
                    RecyclerView.SCROLL_STATE_SETTLING -> println("settling")

                }*/

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //是否最后一条已经显示
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        val lastPosition = manager.findLastVisibleItemPosition()
                        if (lastPosition == adapter.itemCount - 1) {
                            //最后一条已经显示
                            presenter.loadMore(adapter.itemCount - 1)
                        }
                    }
                }
            }

            /*override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                println("onscrolled dx=$dx dy=$dy")
            }*/
        })
    }

    override fun initDate() {
        //初始化数据
        presenter.loadDatas()
    }

    /**
     * 获取适配器adapter
     */
    abstract fun getSpecialAdapter(): BaseListAdapter<ITEMBEAN, ITEMVIEW>

    /**
     * 获取presenter
     */
    abstract fun getSpecialPresenter(): BaseLisatPresenter

    /**
     * 从返回结果中获取列表数据集合
     */
    abstract fun getList(response: RESPONSE?): List<ITEMBEAN>?
}