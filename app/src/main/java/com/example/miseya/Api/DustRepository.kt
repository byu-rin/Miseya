package com.example.miseya.Api

import DustResponse
import com.example.miseya.retrofit.NetWorkClient
import retrofit2.Response

// 미세먼지 api 호출 및 관련 데이터 가공

class DustRepository {
    suspend fun fetchDustInfo(
        serviceKey: String,
        city: String,
        area: String
    ): Response<DustResponse> {
        return NetWorkClient.dustNetWork.getDust(
            serviceKey = serviceKey,
            returnType = "json",
            numOfRows = 100,
            pageNo = 1,
            sidoName = city,
            stationName = area,
            dataTerm = "daily",
            ver = "1.0"
        )
    }
}
