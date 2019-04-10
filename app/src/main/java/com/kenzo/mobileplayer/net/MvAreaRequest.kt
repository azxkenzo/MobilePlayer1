package com.kenzo.mobileplayer.net

import com.itheima.player.model.bean.MvAreaBean
import com.kenzo.mobileplayer.util.URLProviderUtils

/**
 * MV区域数据请求类
 */
class MvAreaRequest(handler: ResponseHandler<List<MvAreaBean>>) :
    MRequest<List<MvAreaBean>>(0, URLProviderUtils.getMVareaUrl(), handler) {

}