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
    private val api_key = "OHeogT6EGM6my3ZyT0ATWQAW5BG7aqbnJny3WoYtxLthtOuc8uqK8irZieJUUPxAfLZJugVlo7MN0776O0dZqg=="

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
        Log.i("MainViewModel", "Selected City: $city")
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
            "부산" -> _areas.value = listOf("해운대구", "남구", "북구", "서구", "동구")
            "대구" -> _areas.value = listOf("수성구", "동구", "서구", "남구", "북구")
            "인천" -> _areas.value = listOf("중구", "동구", "미추홀구", "연수구", "남동구")
            else -> _areas.value = emptyList()
        }
    }

    // 지역의 미세먼지 정보 로드
    fun loadDustInfo(area: String) = viewModelScope.launch {
        _isLoading.value = true
        selectedCity.value?.let { city ->
            try {
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
}