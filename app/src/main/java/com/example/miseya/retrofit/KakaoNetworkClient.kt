package com.example.miseya.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoNetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/v2/") // base URL 설정
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val kakaoNetwork: KakaoNetworkInterface = retrofit.create(KakaoNetworkInterface::class.java)
}