package com.example.miseya.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* Retrofit 인스턴스를 생성, URL과 Gson 컨버터
NetworkInterface를 구현한 객체(dustNetWork)를 제공하여 API 호출
*/

object NetWorkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dustNetWork: NetWorkInterface = retrofit.create(NetWorkInterface::class.java)
}

