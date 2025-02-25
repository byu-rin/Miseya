package com.example.miseya.Api

import com.example.miseya.data.TmCoordinatesResponse
import com.example.miseya.retrofit.KakaoNetworkClient
import retrofit2.Response

class KakaoRepository {
    suspend fun fetchTMCoordinate(
        lat_x: Double,
        lng_y: Double
    ): Response<TmCoordinatesResponse> {
        return KakaoNetworkClient.kakaoNetwork.getTranscoord(
            lat_x = lat_x,
            lng_y = lng_y
        )
    }
}