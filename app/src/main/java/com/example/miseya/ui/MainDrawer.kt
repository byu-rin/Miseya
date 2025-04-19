package com.example.miseya.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WeatherDrawerScreen() {
    val favoriteAreas = remember {
        listOf(
            Area("금광동", "18°"),
            Area("서울", "15°"),
            Area("신주쿠", "26°", Color.Yellow)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC5E9EA0)) // 반투명한 블루 계열 배경색
            .padding(16.dp)
    ) {
        // 1. 설정 영역
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { /* 설정 이동 */ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "설정",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. 즐겨찾는 지역 영역
        Column {
            Text(
                text = "즐겨찾는 지역",
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { /* 즐겨찾기 버튼 클릭 */ },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("금광동", color = Color.White)
                }

                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "정보",
                    modifier = Modifier.clickable { /* 정보 클릭 */ },
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. 다른 지역 영역 (RecyclerView처럼 LazyColumn)
        Text(
            text = "다른 지역",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(bottom = 16.dp)
//        ) {
//            items(favoriteAreas) { area ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = area.name, color = Color.White)
//                    Text(text = area.temp, color = area.tempColor)
//                }
//            }
//        }
    }
}

data class Area(val name: String, val temp: String, val tempColor: Color = Color.White)

@Preview(showBackground = true)
@Composable
fun WeatherDrawerScreenPreview() {
    WeatherDrawerScreen()
}
