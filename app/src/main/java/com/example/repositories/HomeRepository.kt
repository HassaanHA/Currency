package com.example.repositories

import com.example.models.LatestRates
import com.example.models.SymbolsResponseData
import com.example.network.Api
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository  @Inject constructor(
    private val retrofit: Retrofit,
){
    suspend fun getSymbols(
        apikey: String?,
    ): retrofit2.Response<SymbolsResponseData?> {
        val response = retrofit.create(Api::class.java).getSymbols(
           apikey
        )
        return response
    }
    suspend fun getLatestRates(
        apikey: String?,
        base: String?,
        symbol: String?
    ): retrofit2.Response<LatestRates?> {
        val response = retrofit.create(Api::class.java).getLatestRates(
           apikey, base = base, symbols = symbol
        )
        return response
    }
}