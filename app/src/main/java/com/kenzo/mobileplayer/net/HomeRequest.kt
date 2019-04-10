package com.kenzo.mobileplayer.net

import com.itheima.player.model.bean.HomeItemBean
import com.kenzo.mobileplayer.util.URLProviderUtils

/**
 * 首页数据请求类
 */
class HomeRequest(type: Int, offset: Int, handler: ResponseHandler<List<HomeItemBean>>) :
    MRequest<List<HomeItemBean>>(type, URLProviderUtils.getHomeUrl(offset, 20), handler) {
}