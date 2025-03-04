package com.example.miseya.retrofit

import DustResponse
import com.example.miseya.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*Retrofit 에서 사용할 API 호출 메서드 정의
@GET 어노테이션을 사용하여, API 엔드포인트와 필요한 파라미터 선언
 */
interface NetWorkInterface {
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY,
        @Query("returnType") returnType:String = "json",
//        @Query("numOfRows") numOfRows: Int,
//        @Query("pageNo") pageNo: Int,
        @Query("sidoName") sidoName: String,
        @Query("stationName") stationName: String,
//        @Query("dataTerm") dataTerm: String,
//        @Query("ver") ver: String
    ): Response<DustResponse>
}

/* 요청 변수
(필수) 서비스키 serviceKey : 공데포 인증키
데이터표출방식 returnType : xml or json
한 페이지 결과 수 numOfRows : 한 페이지 결과 수
페이지 번호 pageNo : 페이지 번호
(필수) 시도명 sidoName : 시도 이름
오퍼레이션 버전 ver : 버전별 상세 결과 참고
*/