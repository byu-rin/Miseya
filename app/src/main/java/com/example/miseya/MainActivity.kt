package com.android.miseya

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.miseya.MainViewModel
import com.example.miseya.MiseyaTheme
import com.example.miseya.location.LocationHelper
import com.example.miseya.ui.MainContent
import kotlinx.coroutines.launch
import kotlin.text.Typography.tm

class MainActivity : ComponentActivity() {
    private lateinit var locationHelper: LocationHelper
    private val viewModel: MainViewModel by viewModels()

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
                Log.d("원 좌표 위치", "위치: $lat, $lng")

                lifecycleScope.launch {
                    val locateResponse = viewModel.fetchTMCoordinate(lat, lng)
                    if (locateResponse.isSuccessful) {
                        val tmResponse = locateResponse.body()
                        val tmDoc = tmResponse?.documents?.firstOrNull()

                        if (tmDoc != null) {
                            val tmX = tmDoc.x
                            val tmY = tmDoc.y
                            Log.d("MainActivity", "TM 좌표 변환 결과: $tmX, $tmY")

                            // tm좌표 측정소 정보 요청
                            val measureResponse = viewModel.fetchMeasureInfo(tmX, tmY)

                            if (measureResponse.isSuccessful) {
                                val measureData = measureResponse.body()
                                val nearestStation = measureData?.response?.body?.items?.minByOrNull { it.tm }

                                if (nearestStation != null) {
                                    val stationName = nearestStation.stationName
                                    Log.d("측정소 정보", "가장 가까운 측정소: $stationName")
                                } else {
                                    Log.e("MainActivity", "측정소 정보 없응")
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "TM 좌표 변환 실패: ${locateResponse.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "TM 좌표 변환 실패: ${locateResponse.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("MainActivity", "TM 좌표 변환 실패: ${locateResponse.code()}")
                    }
                }
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