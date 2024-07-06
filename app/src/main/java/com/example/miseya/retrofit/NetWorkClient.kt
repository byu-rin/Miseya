package com.example.miseya.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dustNetWork: NetWorkInterface = retrofit.create(NetWorkInterface::class.java)
}
