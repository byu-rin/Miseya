package com.example.miseya

import DustItem
import DustResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseya.Api.DustRepository
import com.example.miseya.Api.KakaoRepository
import com.example.miseya.Api.MeasureRepository
import com.example.miseya.data.AirQualityClassifier.classifyAirQuality
import com.example.miseya.data.MeasureDTO
import com.example.miseya.data.TmCoordinatesResponse
import com.example.miseya.data.cityAreas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val dustRepository: DustRepository = DustRepository(),
    private val kakaoRepository: KakaoRepository = KakaoRepository(),
    private val measureRepository: MeasureRepository = MeasureRepository(),
) : ViewModel() {
    // private val api_key = BuildConfig.API_KEY

    // South Korea cities list
    val cities = listOf(
        "서울", "부산", "대구", "인천", "광주", "대전", "울산",
        "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"
    )
    // Manage the list of areas for the selected city
    private val _areas = MutableStateFlow<List<String>>(emptyList())
    val areas: StateFlow<List<String>> = _areas.asStateFlow()

    // Save the currently selected city
    private val _selectedCity = MutableStateFlow<String?>(null)
    private val selectedCity: StateFlow<String?> = _selectedCity.asStateFlow()

    // Save the currently selected area
    private val _selectedArea = MutableStateFlow<String?>(null)
    val selectedArea: StateFlow<String?> = _selectedArea.asStateFlow()

    private val _isLoading = MutableStateFlow(false)

    // Save the data received from the API
    private val _dustData = MutableStateFlow<DustItem?>(null)
    val dustData: StateFlow<DustItem?> = _dustData.asStateFlow()

    // Save the classification result of air quality
    private val _airQualityClassification = MutableStateFlow("")
    val airQualityClassification: StateFlow<String> = _airQualityClassification.asStateFlow()

    // Update the selected city when a city is selected
    fun setSelectedCity(city: String) {
        _selectedCity.value = city
        updateAreasForCity(city)
    }

    // Process area selection, update, and load dust information
    fun setSelectedArea(area: String) {
        _selectedArea.value = area
        loadDustInfo(area)
    }

    // Update area information according to the selected city
    private fun updateAreasForCity(city: String) {
        val cityArea = cityAreas.find { it.city == city }
        _areas.value = cityArea?.areas ?: emptyList()
    }

    private fun loadDustInfo(area: String) = viewModelScope.launch {
        _isLoading.value = true

        selectedCity.value?.let { city ->
            try {
                val dust_response = dustRepository.fetchDustInfo(city, area)
                processDustResponse(dust_response, area)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 1. API 요청
    private suspend fun fetchDustData(city: String, area: String): Response<DustResponse> {
        Log.d("MainViewModel", "Api 요청 시작 : city=$city, area=$area")
        return dustRepository.fetchDustInfo(city, area)
    }

    // 2. 응답 데이터 처리
    private fun processDustResponse(response: Response<DustResponse>, area: String)
    {
        if (response.isSuccessful) {
            response.body()?.let { dustResponse ->
                val items = dustResponse.response.body.dustItem
                val matchingItem = items?.find { it.stationName == area }

                if (matchingItem != null) {
                    _dustData.value = matchingItem
                    classifyAndUpdateAirQuality(matchingItem)
                } else {
                    Log.e("MainViewModel", "일치하는 지역 데이터 없음.")
                }
            }
        } else {
            Log.e("MainViewModel", "API 호출 실패: ${response.errorBody()?.string()}")
        }
    }

    private fun classifyAndUpdateAirQuality(dustItem: DustItem) {
        val classification = classifyAirQuality(
            pm10Value = dustItem.pm10Value,
            pm25Value = dustItem.pm25Value,
            o3Value = dustItem.o3Value
        )
        _airQualityClassification.value = classification
        Log.i("AirQuality", classification)
    }
    // Load dust information for the area
//    private fun loadDustInfo(area: String) = viewModelScope.launch {
//        // 1. UI 에게 로딩 상태 알림 (스피너)
//        _isLoading.value = true
//        // 2. selectedCity 가 null 이 아니라면 api 호출 진행
//        selectedCity.value?.let { city ->
//            try {
//                // 3. 호출 시작 전 로그 출력
//                Log.d("MainViewModel", "Api 요청 시작 : city=$city, area=$area")
//                val dust_response = dustRepository.fetchDustInfo(city, area) // 4. api 호출
//                // 5. api 호출 성공했는지 확인 (HTTP 상태 코드)
//                if (dust_response.isSuccessful) {
//                    // 6. 응답 본문이 null 이 아닌 경우 처리
//                    dust_response.body()?.let { dustResponse ->
//                        // 7. 응답 body 부분에서 dust 항목 가져옴.
//                        dustResponse.response.body.let { body ->
//                            // 8. body 의 dustItem 리스트에서 선택한 area 와 매칭되는 항목 필터링
//                            val items = body.dustItem
//                            val matchingItems = items?.filter { it.stationName == area }
//
//                            // 9. dustItem 리스트가 비어있지 않다면
//                            if (!items.isNullOrEmpty()) {
//                                // 10. 첫 번째 일치 항목 선택
//                                val dustItem = matchingItems?.first()
//                                // 11. UI 에 보여줄 데이터로 stateFlow(_dustData) 에 할당
//                                _dustData.value = dustItem
//
//                                // 12. 대기질 분류를 위한 로직 호출
//                                val classification = classifyAirQuality(
//                                    pm10Value = dustItem?.pm10Value,
//                                    pm25Value = dustItem?.pm25Value,
//                                    o3Value = dustItem?.o3Value
//                                )
//                                // 13. 대기질 분류 결과를 StateFlow(_airQualityClassification) 에 할당
//                                _airQualityClassification.value = classification
//                                // 14. 대기질 분류 결과 로그 출력
//                                Log.i("AirQuality", classification)
//                            } else {
//                                // dustItem 리스트가 비어있을 경우, 로그
//                                Log.e("MainViewModel", "Error: ${dust_response.errorBody()?.string()}")
//                            }
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                // 15. API 호출 도중 예외 발생 시 catch 블록에서 로그
//                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
//            } finally {
//                // 16. try-catch 블록 끝나면 항상 실행 : 로딩 상태 종료 알림
//                _isLoading.value = false
//            }
//        }
//    }

    suspend fun fetchTMCoordinate(
        lat_x: Double,
        lng_y: Double
    ): Response<TmCoordinatesResponse> {
        return kakaoRepository.fetchTMCoordinate(lat_x, lng_y)
    }

    suspend fun fetchMeasureInfo(
        tmX: Double?,
        tmY: Double?
    ): Response<MeasureDTO> {
        return measureRepository.fetchMeasureInfo(tmX, tmY)
    }
}