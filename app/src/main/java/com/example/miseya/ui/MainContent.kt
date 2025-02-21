package com.example.miseya.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.miseya.MainViewModel
import com.example.miseya.R

@Composable
fun MainContent(viewModel: MainViewModel = MainViewModel()) {
    val airQualityClassification by viewModel.airQualityClassification.collectAsState()

    // 이미지 리소스와 배경색을 설정하는 함수
    val (imageResId, backgroundColor) = when (airQualityClassification) {
        "좋음" -> R.drawable.good to Color(0xFF31A4DD) // 좋음
        "보통" -> R.drawable.soso to Color(0xFF2A612C) // 보통
        "나쁨" -> R.drawable.bad to Color(0xFFFF9800) // 나쁨
        "매우 나쁨" -> R.drawable.terrible to Color(0xFFF44336) // 매우 나쁨
        else -> R.drawable.base to Color(0xFF5B60A0) // 기본값
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor // 화면 배경색
    ) {
        // 도시와 구역 스피너를 수평으로 배열
        Column( // vertical layout
            modifier = Modifier
                .fillMaxSize() // column 이 전체 화면을 차지하도록
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CityAreaSpinners(viewModel)
            Spacer(modifier = Modifier.weight(1f)) // 빈 공간 추가하여 레이아웃 조정
            Box(// 컴포넌트 겹쳐서 배치하는 레이아웃
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                MainInfo(viewModel)
            }
            Spacer(Modifier.weight(1f)) // Imoge 아래쪽에 추가 공간을 제공
        }
    }
}