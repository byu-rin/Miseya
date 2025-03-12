package com.example.miseya.Api

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.miseya.MainViewModel
import com.example.miseya.location.LocationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationRepository(
    private val context: Context,
    private val locationHelper: LocationHelper,
    private val viewModel: MainViewModel
) {
    private val requestPermissionLauncher =
        (context as ComponentActivity).registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // 권한 허용 시 위치 정보 요청
                getUserLocation()
            } else {
                // 권한 거부 시 사용자에게 메시지 표시
                Log.e("LocationRepository", "위치 권한 거부됨")
            }
        }
    fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
            getUserLocation()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getUserLocation() {
        locationHelper.getLastKnownLocation(
            onLocationReceived = { lat, lng ->
                Log.d("원 좌표 위치", "위치: $lat, $lng")

                CoroutineScope(Dispatchers.IO).launch {
                    val locateResponse = viewModel.fetchTMCoordinate(lat, lng)
                    if (locateResponse.isSuccessful) {
                        val tmResponse = locateResponse.body()
                        val tmDoc = tmResponse?.documents?.firstOrNull()

                        if (tmDoc != null) {
                            val tmX = tmDoc.x
                            val tmY = tmDoc.y
                            Log.d("LocationRepository", "TM 좌표 변환 결과: $tmX, $tmY")

                            val measureResponse = viewModel.fetchMeasureInfo(tmX, tmY)
                            if (measureResponse.isSuccessful) {
                                val measureData = measureResponse.body()
                                val nearestStation =
                                    measureData?.response?.body?.items?.minByOrNull { it.tm }

                                if (nearestStation != null) {
                                    val stationName = nearestStation.stationName
                                    Log.d("LocationRepository", "가장 가까운 측정소: $stationName")
                                } else {
                                    Log.e("LocationRepository", "측정소 정보 없음")
                                }
                            }
                        } else {
                            Log.e("LocationRepository", "TM 좌표 변환 실패: ${locateResponse.code()}")
                        }
                    } else {
                        Log.e("LocationRepository", "TM 좌표 변환 실패: ${locateResponse.code()}")
                    }
                }
            },
            onFailure = { error ->
                Log.e("LocationRepository", "위치를 가져오는 중 오류 발생: $error")
            }
        )
    }
}