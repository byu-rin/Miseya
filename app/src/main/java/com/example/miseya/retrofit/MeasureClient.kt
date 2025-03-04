package com.example.miseya.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MeasurementClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val measureNetWork: MeasureInterface = retrofit.create(MeasureInterface::class.java)
}