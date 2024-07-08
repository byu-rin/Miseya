package com.example.miseya

import DustItem
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseya.retrofit.NetWorkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val api_key = "OHeogT6EGM6my3ZyT0ATWQAW5BG7aqbnJny3WoYtxLthtOuc8uqK8irZieJUUPxAfLZJugVlo7MN0776O0dZqg=="// BuildConfig.API_KEY

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
            "부산" -> _areas.value = listOf("광복동", "초량동", "대연동", "장림동", "연산동")
            "대구" -> _areas.value = listOf("신암동", "내당동", "대명동", "복현동", "범어동")
            "인천" -> _areas.value = listOf("송도동", "구월동", "부평동", "가좌동", "작전동")
            "광주" -> _areas.value = listOf("산수동", "화정동", "주월동", "문흥동", "송정동")
            "대전" -> _areas.value = listOf("용운동", "은행동", "둔산동", "봉명동", "송촌동")
            "울산" -> _areas.value = listOf("학성동", "삼산동", "방어동", "명촌동", "언양읍")
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
                                    _dustData.value = filteredItems.first()
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

    fun classifyAirQuality(pm10Value: Int?, pm25Value: Int?, o3Value: Double?): String {
        val pm10Grade = when {
            pm10Value == null -> "데이터 없음"
            pm10Value <= 30 -> "좋음"
            pm10Value <= 80 -> "보통"
            pm10Value <= 150 -> "나쁨"
            else -> "매우 나쁨"
        }

        val pm25Grade = when {
            pm25Value == null -> "데이터 없음"
            pm25Value <= 15 -> "좋음"
            pm25Value <= 35 -> "보통"
            pm25Value <= 75 -> "나쁨"
            else -> "매우 나쁨"
        }

        val o3Grade = when {
            o3Value == null -> "데이터 없음"
            o3Value <= 0.030 -> "좋음"
            o3Value <= 0.090 -> "보통"
            o3Value <= 0.150 -> "나쁨"
            else -> "매우 나쁨"
        }
        return "PM10: $pm10Grade, PM2.5: $pm25Grade, O3: $o3Grade"
    }
}