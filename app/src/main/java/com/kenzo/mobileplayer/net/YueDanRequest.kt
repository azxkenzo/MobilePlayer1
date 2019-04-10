package com.kenzo.mobileplayer.net

import com.itheima.player.model.bean.YueDanBean
import com.kenzo.mobileplayer.util.URLProviderUtils

/**
 * 乐单界面网络请求
 */
class YueDanRequest(type: Int, offset: Int, handler: ResponseHandler<YueDanBean>) :
    MRequest<YueDanBean>(type, URLProviderUtils.getYueDanUrl(offset, 20),handler) {

}