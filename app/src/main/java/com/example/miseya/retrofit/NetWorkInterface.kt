package com.example.miseya.retrofit

import DustResponse
import com.example.miseya.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/*
Retrofit 에서 사용할 API 호출 메서드 정의
@GET 어노테이션을 사용하여, API 엔드포인트와 필요한 파라미터 선언
 */
interface NetWorkInterface {
    @GET("getMsrstnAcctoRltmMesureDnsty")
    suspend fun getDust(
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY,
        @Query("returnType") returnType:String = "json",
        @Query("stationName") stationName: String,
        @Query("dataTerm") dataTerm: String = "DAILY",
    ): Response<DustResponse>
}