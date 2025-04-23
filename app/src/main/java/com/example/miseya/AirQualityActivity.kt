package com.example.miseya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.miseya.Api.LocationRepository
import com.example.miseya.location.LocationHelper
import com.example.miseya.ui.HeyMiseTheme
import com.example.miseya.utils.HeyMiseApp

class AirQualityActivity : ComponentActivity() {
    private lateinit var locationRepository: LocationRepository
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent { // Compose 의 Composable 함수를 사용하여 UI 설정
            HeyMiseTheme {
                HeyMiseApp()
            }
        }
        val locationHelper = LocationHelper(this)
        locationRepository = LocationRepository(this, locationHelper, viewModel)

        // 위치 권한 요청
        locationRepository.requestLocationPermission()
    }
}