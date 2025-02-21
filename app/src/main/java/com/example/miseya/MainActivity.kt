package com.android.miseya

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.miseya.MiseyaTheme
import com.example.miseya.location.LocationHelper
import com.example.miseya.ui.MainContent

class MainActivity : ComponentActivity() {
    private lateinit var locationHelper: LocationHelper

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
        locationHelper = LocationHelper(this)

    }
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUserLocation()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            getUserLocation()
        } else {
            Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserLocation() {
        locationHelper.getLastKnownLocation(
            onLocationReceived = { lat, lng ->
                Toast.makeText(this, "위치: $lat, $lng", Toast.LENGTH_LONG).show()
            },
            onFailure = { error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    MiseyaTheme {
        MainContent()
    }
}