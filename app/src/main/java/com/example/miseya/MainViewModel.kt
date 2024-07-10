package com.example.miseya

import DustItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
//        Log.i("MainViewModel", "Selected City: $city")
        updateAreasForCity(city)
    }

    // 지역 선택 처리
    fun setSelectedArea(area: String) {
        Log.i("MainViewModel", "Selected Area: $area")
        _selectedArea.value = area
        loadDustInfo(area)
    }

    // 선택된 도시 따라 지역 정보 업데이트
    private fun updateAreasForCity(city: String) {
        when (city) {
            "서울" -> _areas.value = listOf("강남구", "서초구", "강서구", "마포구", "동작구")
            "부산" -> _areas.value = listOf("광복동", "초량동", "청룡동", "좌동", "재송동")
            "대구" -> _areas.value = listOf("신암동", "수창동", "지산동", "서호동", "이현동")
            "인천" -> _areas.value = listOf("송도동", "구월동", "부평동", "가좌동", "작전동")
            "광주" -> _areas.value = listOf("산수동", "화정동", "주월동", "문흥동", "송정동")
            "대전" -> _areas.value = listOf("문평동", "구성동", "노은동", "관평동", "대성동")
            "울산" -> _areas.value = listOf("화산리", "상남리", "약사동", "범서읍", "송정동")
            "경기" -> _areas.value = listOf("내동", "보산동", "봉산동", "중앙동", "가평")
            "강원" -> _areas.value = listOf("중앙동", "옥천동", "노학동", "남양동", "홍천읍")
            "충북" -> _areas.value = listOf("흥덕구", "중앙탑면", "중앙동", "보은읍", "옥천읍")
            "충남" -> _areas.value = listOf("동남구", "중동", "대천동", "온양동", "동문동")
            "전북" -> _areas.value = listOf("완산구", "영등동", "금동", "요촌동", "삼례읍")
            "전남" -> _areas.value = listOf("장흥읍", "진도읍", "신안군", "곡성읍", "목포항")
            "경북" -> _areas.value = listOf("우현동", "장흥동", "대도동", "오천읍", "연일읍")
            "경남" -> _areas.value = listOf("경화동", "월영동", "금성면", "동상동", "장유동")
            "제주" -> _areas.value = listOf("이도동", "연동", "조천읍", "한림읍", "애월읍")
            "세종" -> _areas.value = listOf("보람동", "한솔동", "전의면")
            else -> _areas.value = emptyList()
        }
    }

    // 지역의 미세먼지 정보 로드
    fun loadDustInfo(area: String) = viewModelScope.launch {
        _isLoading.value = true
        selectedCity.value?.let { city ->
            try {
                val response = NetWorkClient.dustNetWork.getDust(
                    serviceKey = api_key,
                    returnType = "json",
                    numOfRows = "100",
                    pageNo = "1",
                    sidoName = city,
                    stationName = area,
                    dataTerm = "daily",
                    ver = "1.3"
                )
                if (response.isSuccessful) {
                    response.body()?.let { dustResponse ->
                        dustResponse.response.body.let { body ->
                            val items = body.dustItem
                            if (items != null) {
                                val filteredItems = items.filter { it.stationName == area }
                                Log.i("DustInfo", "Filtered Items for $area, $city: $filteredItems")
                                if (filteredItems.isNotEmpty()) {
                                    val dustItem = filteredItems.first()
                                    _dustData.value = dustItem

                                    // 수치 분류 후 로깅
                                    val classification = classifyAirQuality(
                                        pm10Value = dustItem.pm10Value.toString(),
                                        pm25Value = dustItem.pm25Value.toString(),
                                        o3Value = dustItem.o3Value.toString()
                                    )
                                    _airQualityClassification.value = classification
                                    Log.i("AirQuality", classification)
                                } else {
                                    Log.e("MainViewModel", "No items found for $area, $city")
                                }
                            } else {
                                Log.e("MainViewModel", "No items found for $area, $city")
                            }
                        }
                    }
                } else {
                    Log.e("MainViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // API 호출 파라미터
    private fun setUpDustParameter(sido: String, stationName: String): HashMap<String, String> {
        return hashMapOf(
            "serviceKey" to api_key,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "stationName" to stationName,
            "dataTerm" to "daily",
            "ver" to "1.3"
        )
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
}