package com.example.network

data class Response<T>(
    var success: Boolean? = null,
    var data: T? = null,
)