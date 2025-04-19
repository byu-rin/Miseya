package com.example.miseya.ui

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF263238))
        ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(top = 200.dp)) {
            HourlyForecastCard()
            TenDayForecast()
            AirQualityCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
        FixedTopWeatherHeader()
    }
}

@Composable
fun FixedTopWeatherHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp)
    ) {
        Text("\uD83D\uDCCD집", color = Color.White, fontSize = 16.sp)
        Text("서울시", color = Color.White, fontSize = 20.sp)
        Text("25℃", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Light)
        Text("맑음", color = Color.White, fontSize = 16.sp)
        Text("최고 27℃  최저 18℃", color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun HourlyForecastCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xAA37474F)),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("오후 8시 쯤 흐린 상태가 예상됩니다. 돌풍의 풍속은 최대 8m/s입니다.", color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 0.4.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                repeat(10) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("지금", color = Color.White, fontSize = 12.sp)
//                        Image(painter = painterResource(id = android.R.drawable.ic_menu_compass), contentDescription = null)
                        Text("23℃", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TenDayForecast() {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xAA37474F)),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp
            )) {
            repeat(10) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("오늘", modifier = Modifier.weight(1f), color = Color.White)
                    Text("☀️", modifier = Modifier.weight(1f))
                    Text("18℃", modifier = Modifier.weight(1f), color = Color.White)
                    Box(modifier = Modifier.weight(3f).height(4.dp).background(Color.Gray))
                    Text("27℃", modifier = Modifier.weight(1f), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AirQualityCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xAA37474F)),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("대기질", color = Color.White)
            Text("45", color = Color.White, fontSize = 32.sp)
            Text("보통", color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color.Green)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("대기 예보: 오늘 오후까지 보통 수준 예상", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreen()
}