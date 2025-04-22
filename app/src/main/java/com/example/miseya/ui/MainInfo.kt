package com.example.miseya.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.miseya.MainViewModel
import com.example.miseya.R

@Composable
fun MainInfo(viewModel: MainViewModel) {
    val airQualityClassification by viewModel.airQualityClassification.collectAsState()
    val dustData by viewModel.dustData.collectAsState()
    val selectedArea by viewModel.selectedArea.collectAsState()

    // 이미지 리소스와 배경색을 설정하는 함수
    val imageResId = when (airQualityClassification) {
        "좋음" -> R.drawable.good
        "보통" -> R.drawable.soso
        "나쁨" -> R.drawable.bad
        "매우 나쁨" -> R.drawable.terrible
        else -> R.drawable.base
    }
}

