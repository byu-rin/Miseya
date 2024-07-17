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
    private val api_key = "OHeogT6EGM6my3ZyT0ATWQAW5BG7aqbnJny3WoYtxLthtOuc8uqK8irZieJUUPxAfLZJugVlo7MN0776O0dZqg==" // BuildConfig.API_KEY

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

    // 공기 질 분류 결과를 저장하는 MutableStateFlow
    private val _airQualityClassification = MutableStateFlow("")
    val airQualityClassification: StateFlow<String> = _airQualityClassification.asStateFlow()


    // 도시가 선택 시, 선택 도시 업데이트 후 구역 정보 업데이트
    fun setSelectedCity(city: String) {
        _selectedCity.value = city
        updateAreasForCity(city)
    }

    // 지역 선택 처리, 업데이트 후 미세먼지 정보 로드
    fun setSelectedArea(area: String) {
        _selectedArea.value = area
        loadDustInfo(area)
    }

    // 선택된 도시 따라 지역 정보 업데이트
    private fun updateAreasForCity(city: String) {
        val cityArea = cityAreas.find { it.city == city }
        _areas.value = cityArea?.areas ?: emptyList()
    }


    /*
    TODO: 결과 따라 화면 색 및 이모지 업데이트
    */

    // 지역의 미세먼지 정보 로드
    fun loadDustInfo(area: String) = viewModelScope.launch {
        // 로딩 상테를 true 로 설정하여 UI 에 로딩 중임을 알림
        _isLoading.value = true
        // 현재 선택된 도시가 null 이 아닌 경우 실행
        selectedCity.value?.let { city ->
            try {
                // API 호출
                val response = fetchDustInfo(api_key, city, area)

                // API 응답 성공 시
                if (response.isSuccessful) {
                    // 응답 본문
                    response.body()?.let { dustResponse ->
                        dustResponse.response.body.let { body ->
                            // 응답에서 미세먼지 항목들을 가져옴
                            val items = body.dustItem

                            // 선택된 지역과 일치하는 항목 필터링
                            val matchingItems = items?.filter { it.stationName == area }
//                            Log.i("MainViewModel", "Matching items: $area : $matchingItems")

                            // 미세먼지 항목이 비어있지 않은 경우 실행
                            if (!items.isNullOrEmpty()) {
                                // 첫 번째 일치하는 미세먼지 항목을 가져옴
                                val dustItem = matchingItems?.first()
                                // 가져온 미세먼지 항목을 StateFlow 에 설정
                                _dustData.value = dustItem

                                // 수치 분류 후 로깅
                                val classification = classifyAirQuality(
                                    pm10Value = dustItem?.pm10Value.toString(),
                                    pm25Value = dustItem?.pm25Value.toString(),
                                    o3Value = dustItem?.o3Value.toString()
                                )
                                // 분류 결과를 StateFlow 에 설정
                                _airQualityClassification.value = classification
                                Log.i("AirQuality", classification)
                            } else {
                                // 미세먼지 항목 없는 경우
                                Log.e("MainViewModel", "No items found for $area, $city")
                            }
                        }
                    }
                } else {
                    // API 응답 실패
                    Log.e("MainViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
            } finally {
                // 로딩 상태를 false 로 설정하여 UI 에 로딩 완료 알림
                _isLoading.value = false
            }
        }
    }

    // 미세먼지 수치 분류 후 레벨 반환
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

    // 수치를 API 에서 가져오는 함수
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