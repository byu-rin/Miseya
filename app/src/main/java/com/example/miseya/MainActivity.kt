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
import okhttp3.Request

/**
 * registerForActivityResult 등록
 *
 * requestPermissionLauncher는 ActivityResultContracts.RequestPermission()을 사용하여 권한 요청의 결과를 비동기로 받아옵니다.
 * 권한이 허용되면 getUserLocation()이 호출되어 위치 정보를 요청합니다.
 * onCreate에서의 초기화 및 권한 요청
 *
 * setContent { ... }를 통해 Compose UI를 설정한 후,
 * LocationHelper를 초기화하고,
 * requestLocationPermission()을 호출하여 위치 권한 상태를 확인하고, 없으면 요청합니다.
 * requestLocationPermission() 함수
 *
 * ContextCompat.checkSelfPermission()으로 현재 권한 상태를 확인합니다.
 * 권한이 허용되어 있다면 바로 getUserLocation()을 호출합니다.
 * 그렇지 않으면 requestPermissionLauncher.launch()를 호출하여 사용자에게 권한 요청 다이얼로그를 띄웁니다.
 * getUserLocation() 함수
 *
 * locationHelper.getLastKnownLocation()을 호출하여 마지막으로 저장된 위치 정보를 받아옵니다.
 * 성공하면 onLocationReceived 콜백이 호출되고, 실패 시 onFailure 콜백에서 에러 메시지를 처리합니다.
 * 만약 lastLocation으로 null이 반환된다면, 필요에 따라 getCurrentLocation()이나 requestLocationUpdates()와 같이 현재 위치를 새로 요청하는 방식으로 보완하는 것을 고려할 수 있습니다.
 */


class MainActivity : ComponentActivity() {

    private lateinit var locationHelper: LocationHelper

    // 런타임 권한 요청 결과를 처리
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // 권한 허용 시 위치 정보 요청
            getUserLocation()
        } else {
            // 권한 거부 시 사용자에게 메시지 표시
            Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

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
        // LocationHelper 초기화
        locationHelper = LocationHelper(this)

        // 위치 권한 확인 및 요처
        requestLocationPermission()
    }

    // 위치 권한 확인, 없으면 권한 요청
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
            // 권한 허용되어 있을 지 위치 정보 요청
            getUserLocation()
        } else {
            // 권한 없으면 런타임 권한 요청 실행
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // 권한 허용 시 호출되어 위치 정보 가져옴
    private fun getUserLocation() {
        locationHelper.getLastKnownLocation(
            // 성공 시
            onLocationReceived = { lat, lng ->
                Toast.makeText(this, "위치: $lat, $lng", Toast.LENGTH_LONG).show()
            },
            // 실패 시
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