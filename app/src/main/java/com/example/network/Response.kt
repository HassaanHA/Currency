package com.yalla.yallatablet.network

data class Response<T>(
    var status_code: Int = 0,
    var data: T? = null,
    var message: String? = null
)