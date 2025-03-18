package com.example.miseya.api

import DustResponse
import com.example.miseya.retrofit.NetWorkClient
import retrofit2.Response
// 미세먼지 api 호출 및 관련 데이터 가공

class DustRepository {
    suspend fun fetchDustInfo(
        area: String
    ): Response<DustResponse> {
        return NetWorkClient.dustNetWork.getDust(
            stationName = area
        )
    }
}