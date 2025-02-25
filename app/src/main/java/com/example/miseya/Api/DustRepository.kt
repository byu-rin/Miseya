package com.example.miseya.Api

import DustResponse
import com.example.miseya.retrofit.NetWorkClient
import retrofit2.Response

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
