package com.example.miseya.Api

import com.example.miseya.data.MeasureDTO
import com.example.miseya.retrofit.MeasurementClient
import retrofit2.Response

// 근접 측정소 api 를 요청
class MeasureRepository {
    suspend fun fetchMeasureInfo(
        tmX: Double?,
        tmY: Double?,
    ): Response<MeasureDTO> {
        return MeasurementClient.measureNetWork.getMeasure(
            tmX = tmX,
            tmY = tmY
        )
    }
}