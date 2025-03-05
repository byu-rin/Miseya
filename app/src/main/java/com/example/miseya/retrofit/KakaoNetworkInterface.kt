package com.example.miseya.retrofit

import com.example.miseya.BuildConfig
import com.example.miseya.data.TmCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
// api 요청 정의. Retrofit 에서 사용한 api 요청 형식 정의
interface KakaoNetworkInterface {
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("local/geo/transcoord.json?")
    suspend fun getTranscoord(
        @Query("x") lat_x: Double,
        @Query("y") lng_y: Double,
        @Query("output_coord") output_coord: String = "WTM"
    ): Response<TmCoordinatesResponse>
}