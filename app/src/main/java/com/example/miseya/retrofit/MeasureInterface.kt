package com.example.miseya.retrofit

import com.example.miseya.BuildConfig
import com.example.miseya.data.MeasureDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeasureInterface {
    @GET("getNearbyMsrstnList")
    suspend fun getMeasure(
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY,
        @Query("returnType") returnType: String = "json",
        @Query("tmX") tmX: Double?,
        @Query("tmY") tmY: Double?,
    ): Response<MeasureDTO>
}