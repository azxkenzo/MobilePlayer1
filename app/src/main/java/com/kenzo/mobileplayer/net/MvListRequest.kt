package com.kenzo.mobileplayer.net

import com.itheima.player.model.bean.MvPagerBean
import com.kenzo.mobileplayer.util.URLProviderUtils

class MvListRequest(type: Int, code: String, offset : Int, handler: ResponseHandler<MvPagerBean>) :
    MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code, offset, 20), handler) {
}