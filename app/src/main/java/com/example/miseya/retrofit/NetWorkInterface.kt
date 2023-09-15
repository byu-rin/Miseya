package com.example.miseya.retrofit

import Dust
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty") //시도별 실시간 측정정보 조회 주소
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust
}