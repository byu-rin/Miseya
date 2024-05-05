package com.example.miseya.retrofit

import com.example.miseya.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {

    // 공공데이터포털에서 발급받은 API 키
    private const val DUST_BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/"

    // OkHttpClient를 생성하는 함수
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        // 디버그 모드인 경우 HTTP 로그 레벨을 BODY로 설정하여 디버깅 정보를 출력
        // 릴리즈 모드인 경우 로깅을 비활성화
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        // OkHttpClient를 설정 및 반환
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    // Retrofit 객체를 생성
    private val dustRetrofit = Retrofit.Builder()
        .baseUrl(DUST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(
            createOkHttpClient()
        ).build()

    // Retrofit 인터페이스 구현한 NetWorkInterface 객체를 생성
    val dustNetWork: NetWorkInterface = dustRetrofit.create(NetWorkInterface::class.java)
}