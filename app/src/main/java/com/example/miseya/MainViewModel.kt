package com.example.miseya

import DustItem
import DustResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseya.data.cityAreas
import com.example.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val api_key = BuildConfig.API_KEY

    // 대한민국 주요 도시 목록
    val cities = listOf(
        "서울", "부산", "대구", "인천", "광주", "대전", "울산",
        "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"
    )

    // 각 도시의 구역 정보를 관리하는 MutableStateFlow
    private val _areas = MutableStateFlow<List<String>>(emptyList())
    val areas: StateFlow<List<String>> = _areas.asStateFlow()

    // 현재 선택된 도시를 저장하는 MutableStateFlow
    private val _selectedCity = MutableStateFlow<String?>(null)
    val selectedCity: StateFlow<String?> = _selectedCity.asStateFlow()

    // 현재 선택된 지역을 저장하는 MutableStateFlow
    private val _selectedArea = MutableStateFlow<String?>(null)
    val selectedArea: StateFlow<String?> = _selectedArea.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // API로부터 받은 데이터를 저장하는 MutableStateFlow
    private val _dustData = MutableStateFlow<DustItem?>(null)
    val dustData: StateFlow<DustItem?> = _dustData.asStateFlow()

    private val _airQualityClassification = MutableStateFlow("Loading...")
    val airQualityClassification: StateFlow<String> = _airQualityClassification.asStateFlow()


    // 도시가 선택 시 도시에 따른 지역 정보 업데이트
    fun setSelectedCity(city: String) {
        _selectedCity.value = city
        updateAreasForCity(city)
    }

    // 지역 선택 처리
    fun setSelectedArea(area: String) {
        _selectedArea.value = area
        loadDustInfo(area)
    }

    // 선택된 도시 따라 지역 정보 업데이트
    private fun updateAreasForCity(city: String) {
        val cityArea = cityAreas.find { it.city == city }
        _areas.value = cityArea?.areas ?: emptyList()
    }

    // 지역의 미세먼지 정보 로드
    fun loadDustInfo(area: String) = viewModelScope.launch {
        val startTime = System.currentTimeMillis()
        _isLoading.value = true
        selectedCity.value?.let { city ->
            try {
                val apiCallStartTime = System.currentTimeMillis()
                val response = fetchDustInfo(api_key, city, area)
                val apiCallEndTime = System.currentTimeMillis()

                if (response.isSuccessful) {
                    val responseBodyStartTime = System.currentTimeMillis()
                    response.body()?.let { dustResponse ->
                        dustResponse.response.body.let { body ->
                            val items = body.dustItem
                            val responseBodyEndTime = System.currentTimeMillis()

                            if (!items.isNullOrEmpty()) {
                                val dustItem = items.first()
                                val dustItemProcessingStartTime = System.currentTimeMillis()
                                _dustData.value = dustItem

                                // 수치 분류 후 로깅
                                val classification = classifyAirQuality(
                                    pm10Value = dustItem.pm10Value.toString(),
                                    pm25Value = dustItem.pm25Value.toString(),
                                    o3Value = dustItem.o3Value.toString()
                                )
                                _airQualityClassification.value = classification
                                Log.i("AirQuality", classification)
                                val dustItemProcessingEndTime = System.currentTimeMillis()
                                Log.i("Performance", "Dust item processing time: ${dustItemProcessingEndTime - dustItemProcessingStartTime} ms")
                            } else {
                                Log.e("MainViewModel", "No items found for $area, $city")
                            }
                            Log.i("Performance", "Response body processing time: ${responseBodyEndTime - responseBodyStartTime} ms")
                        }
                    }
                } else {
                    Log.e("MainViewModel", "Error: ${response.errorBody()?.string()}")
                }
                Log.i("Performance", "API call time: ${apiCallEndTime - apiCallStartTime} ms")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
            } finally {
                _isLoading.value = false
                val endTime = System.currentTimeMillis()
                Log.i("Performance", "Total loadDustInfo execution time: ${endTime - startTime} ms")
            }
        }
    }

    fun classifyAirQuality(pm10Value: String?, pm25Value: String?, o3Value: String?): String {
        val pm10Int = pm10Value?.toIntOrNull()
        val pm25Int = pm25Value?.toIntOrNull()
        val o3Double = o3Value?.toDoubleOrNull()

        val pm10Grade = when {
            pm10Int == null -> 0
            pm10Int <= 30 -> 1
            pm10Int <= 80 -> 2
            pm10Int <= 150 -> 3
            else -> 4
        }

        val pm25Grade = when {
            pm25Int == null -> 0
            pm25Int <= 15 -> 1
            pm25Int <= 35 -> 2
            pm25Int <= 75 -> 3
            else -> 4
        }

        val o3Grade = when {
            o3Double == null -> 0
            o3Double <= 0.030 -> 1
            o3Double <= 0.090 -> 2
            o3Double <= 0.150 -> 3
            else -> 4
        }
        val averageGrade = (pm10Grade + pm25Grade + o3Grade) / 3.0

        return when {
            averageGrade <= 1 -> "좋음"
            averageGrade <= 2 -> "보통"
            averageGrade <= 3 -> "나쁨"
            else -> "매우 나쁨"
        }
    }

    suspend fun fetchDustInfo(
        serviceKey: String,
        city: String,
        area: String
    ): Response<DustResponse> {
        return NetWorkClient.dustNetWork.getDust(
            serviceKey = serviceKey,
            returnType = "json",
            numOfRows = "100",
            pageNo = "1",
            sidoName = city,
            stationName = area,
            dataTerm = "daily",
            ver = "1.3"
        )
    }
}