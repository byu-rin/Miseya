package com.example.miseya.api

import com.example.miseya.data.TmCoordinatesResponse
import com.example.miseya.retrofit.KakaoNetworkClient
import retrofit2.Response

// TM 좌표 변환 api 호출 (데이터를 가져오는 중간역할)
class KakaoRepository {
    suspend fun fetchTMCoordinate(
        lat: Double,
        lng: Double
    ): Response<TmCoordinatesResponse> {
        return KakaoNetworkClient.kakaoNetwork.getTranscoord(
            lat_x = lng,
            lng_y = lat
        )
    }
}