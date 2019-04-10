package com.kenzo.mobileplayer.presenter.impl


import com.itheima.player.model.bean.HomeItemBean
import com.kenzo.mobileplayer.base.BaseLisatPresenter
import com.kenzo.mobileplayer.base.BaseView
import com.kenzo.mobileplayer.net.HomeRequest
import com.kenzo.mobileplayer.net.ResponseHandler
import com.kenzo.mobileplayer.presenter.interf.HomePresenter
import com.kenzo.mobileplayer.view.HomeView


class HomePresenterImpl(var homeView: BaseView<List<HomeItemBean>>?) : HomePresenter, ResponseHandler<List<HomeItemBean>> {
    /**
     * 解绑view和presenter
     */
    override fun destoryView() {
        if (homeView != null) homeView = null
    }

    override fun onError(type: Int, msg: String?) {
        homeView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<HomeItemBean>) {
        when (type) {
            BaseLisatPresenter.TYPE_INIT_OR_REFRESH -> homeView?.loadSuccess(result)
            BaseLisatPresenter.TYPE_LOAD_MORE -> homeView?.loadMore(result)
        }
    }

    /**
     * 初始化数据 或者 刷新数据
     */
    override fun loadDatas() {
        //定义request
        HomeRequest(BaseLisatPresenter.TYPE_INIT_OR_REFRESH, 0, this).excute()
        //发送request


//        NetManager.manager.sendRequest(request)


//        val path = URLProviderUtils.getHomeUrl(0, 20)
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(path)
//            .get()
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            /**
//             * 子线程调用
//             */
//            override fun onFailure(call: Call?, e: IOException?) {
//                ThreadUtil.runOnMainThread(object : Runnable {
//                    override fun run() {
//                        //回调到view层处理
//                        homeView.onError(e?.message)
//                    }
//                })
//                //e!!.printStackTrace()
//
//            }
//
//            /**
//             * 子线程调用
//             */
//            override fun onResponse(call: Call?, response: Response?) {
//                val result = response?.body()?.string()
//                //解析数据
//                val gson = Gson()
//                val list = gson.fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
//
//                //刷新列表
//                ThreadUtil.runOnMainThread(object : Runnable {
//                    override fun run() {
//                        //将结果回调到view层
//                        homeView.loadSuccess(list)
//                    }
//
//                })
//            }
//
//        })

    }

    /**
     * 获取更多数据
     */
    override fun loadMore(offset: Int) {

        //定义request
        HomeRequest(BaseLisatPresenter.TYPE_LOAD_MORE, offset, this).excute()
        //发送request


//        NetManager.manager.sendRequest(request)

//        val path = URLProviderUtils.getHomeUrl(offset, 20)
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(path)
//            .get()
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            /**
//             * 子线程调用
//             */
//            override fun onFailure(call: Call?, e: IOException?) {
//                ThreadUtil.runOnMainThread(object : Runnable {
//                    override fun run() {
//                        //回调到view层处理
//                        homeView.onError(e?.message)
//                    }
//                })
//
//            }
//
//            /**
//             * 子线程调用
//             */
//            override fun onResponse(call: Call?, response: Response?) {
//                val result = response?.body()?.string()
//                //解析数据
//                val gson = Gson()
//                val list = gson.fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
//
//                //刷新列表
//                ThreadUtil.runOnMainThread(object : Runnable {
//                    override fun run() {
//                        homeView.loadmore(list)
//                    }
//
//                })
//            }
//
//        })
    }

}