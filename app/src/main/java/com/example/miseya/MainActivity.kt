package com.android.miseya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.miseya.Api.LocationRepository
import com.example.miseya.MainViewModel
import com.example.miseya.MiseyaTheme
import com.example.miseya.location.LocationHelper
import com.example.miseya.ui.MainContent

class MainActivity : ComponentActivity() {
    private lateinit var locationRepository: LocationRepository
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // Compose 의 Composable 함수를 사용하여 UI 설정
            MiseyaTheme {
                Surface( // Compose 레이아웃 컴포넌트 중 하나, 배경 색상과 크기 설정
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF9ED2EC) // 화면 배경색
                ) {
                    MainContent()
                }
            }
        }
        val locationHelper = LocationHelper(this)
        locationRepository = LocationRepository(this, locationHelper, viewModel)

        // 위치 권한 요청
        locationRepository.requestLocationPermission()
    }
}