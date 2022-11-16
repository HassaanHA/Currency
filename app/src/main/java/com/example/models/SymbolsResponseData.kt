package com.example.models

import com.google.gson.annotations.SerializedName

data class SymbolsResponseData(
    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("symbols" ) var symbols : Map<String, String>? = mutableMapOf()
)