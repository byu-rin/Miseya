package com.example.miseya.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.miseya.AppTypography
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        val (location, date, data, level, image) = createRefs()

        // location 업데이트
        val locationText = dustData?.let { "${it.stationName} $selectedArea" } ?: "지역을 선택해주세요."
        // dataTime 업데이트
        val dateText = dustData?.dataTime ?: ""
        // khaiValue 업데이트
        val dataText = dustData?.khaiValue ?: "0"

        Log.d("MainInfo", "LocationText: $locationText, DateText: $dateText, DataText: $dataText")

        Text(
            text = locationText,
            style = AppTypography.h1,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(location) {
                top.linkTo(parent.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = dateText,
            style = AppTypography.h2,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(location.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Imoji(
            drawableResId = imageResId,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(image) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
        )

        Text(
            text = "$dataText ㎍/㎥",
            style = AppTypography.body2,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(data) {
                bottom.linkTo(parent.bottom, margin = 160.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // 수치 레벨 결과 표시
        Text(
            text = airQualityClassification,
            style = AppTypography.h1,
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(level) {
                top.linkTo(data.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}