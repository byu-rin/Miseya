package com.android.miseya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.miseya.MainViewModel
import com.example.miseya.api.LocationRepository
import com.example.miseya.location.LocationHelper

class MainActivity : ComponentActivity() {
    private lateinit var locationRepository: LocationRepository
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationHelper = LocationHelper(this)
        locationRepository = LocationRepository(this, locationHelper, viewModel)

        // 위치 권한 요청
        locationRepository.requestLocationPermission()
    }
}