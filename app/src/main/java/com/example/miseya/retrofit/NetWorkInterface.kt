package com.example.miseya.retrofit

import DustResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

// 네트워크 요청을 정의하는 Retrofit 인터페이스
interface NetWorkInterface {

    // 시도별 실시간 측정정보 조회를 위한 GET 요청
    @GET("getCtprvnRltmMesureDnsty") //시도별 실시간 측정정보 조회 주소
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Response<DustResponse>
}