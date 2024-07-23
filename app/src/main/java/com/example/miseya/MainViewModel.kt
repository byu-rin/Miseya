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
    val selectedCity: StateFlow<String?> = _selectedCity.asStateFlow()

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

    // Load dust information for the area
    fun loadDustInfo(area: String) = viewModelScope.launch {
        // Set the loading state to true to notify the UI that it is loading
        _isLoading.value = true
        // execute if the currently selected city is not null
        selectedCity.value?.let { city ->
            try {
                // call the API
                val response = fetchDustInfo(api_key, city, area)

                // Successful API response
                if (response.isSuccessful) {
                    // Response body
                    response.body()?.let { dustResponse ->
                        dustResponse.response.body.let { body ->
                            // Load the dust items from the response
                            val items = body.dustItem

                            // Filter items that match the selected area
                            val matchingItems = items?.filter { it.stationName == area }

                            // execute if the dust item is not empty
                            if (!items.isNullOrEmpty()) {
                                // Get the first matching dust item
                                val dustItem = matchingItems?.first()
                                // Set the acquired dust item to StateFlow
                                _dustData.value = dustItem

                                // Logging after classifying the value
                                val classification = classifyAirQuality(
                                    pm10Value = dustItem?.pm10Value,
                                    pm25Value = dustItem?.pm25Value,
                                    o3Value = dustItem?.o3Value
                                )
                                // Set the classification result to StateFlow
                                _airQualityClassification.value = classification
                                Log.i("AirQuality", classification)
                            } else {
                                // Failed to get dust information
                                Log.e("MainViewModel", "Error: ${response.errorBody()?.string()}")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching dust info for $area, $city", e)
            } finally {
                // Set the loading state to false to notify the UI that loading is complete
                _isLoading.value = false
            }
        }
    }

    // Return the level after classifying the fine dust value
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

    // Fetch the value from the API
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