package com.example.miseya.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.miseya.MainViewModel

@Composable
fun CityAreaSpinners(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()) { // 수평으로 배열
        Spinner(
            items = viewModel.cities, // 스피너에 표시될 항목 리스트
            label = "도시 선택", // 스피너 기본 텍스트
            onItemSelected = viewModel::setSelectedCity, // 항목 선택 시 호출할 함수
            modifier = Modifier.weight(1f) // Row 내에서 공간을 공평하게 나누기
        )

        Spinner(
            items = viewModel.areas.collectAsState().value,
            label = "지역 선택",
            onItemSelected = viewModel::setSelectedArea,
            modifier = Modifier.weight(1f) // Row 내에서 공간을 공평하게 나누기
        )
    }
}