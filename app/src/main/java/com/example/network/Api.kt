package com.example.network

import com.example.models.LatestRates
import com.example.models.SymbolsResponseData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface Api {

    @GET("symbols")
    suspend fun getSymbols(
        @Header("apikey") api: String?,
    ): retrofit2.Response<SymbolsResponseData?>

   @GET("latest")
    suspend fun getLatestRates(
        @Header("apikey") api: String?,
        @Query("symbols") symbols: String?,
        @Query("base") base: String?,
    ): retrofit2.Response<LatestRates?>

}