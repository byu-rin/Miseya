package com.android.miseya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miseya.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF9ED2EC) // 화면 배경색
            ) {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel = MainViewModel()) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // 도시와 구역 스피너를 수평으로 배열
        Row(modifier = Modifier.fillMaxWidth()) {
            CitySpinner(
                cities = viewModel.cities,
                onCitySelected = { city ->
                    viewModel.setSelectedCity(city) // 도시 선택 시 처리
                },
                modifier = Modifier.weight(1f)
            )
            AreaSpinner(
                areas = viewModel.areas.collectAsState().value,
                onAreaSelected = { area ->
                    viewModel.setSelectedArea(area) // 구역 선택 시 처리
                    viewModel.loadDustInfo(area) // 미세먼지 정보 로드
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(24.dp)
    )
}

@Composable
fun CitySpinner(
    cities: List<String>,
    onCitySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("도시 선택") }

    Box(
        modifier = modifier
            .fillMaxWidth()  // 필수: weight가 올바르게 동작하려면 필요
            .padding(10.dp)
            .clickable(onClick = { expanded = true })
            .background(Color(0xFFD6A478)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedOptionText,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(10.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { city ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = city
                    expanded = false
                    onCitySelected(city)
                }) {
                    Text(text = city)
                }
            }
        }
    }
}

@Composable
fun AreaSpinner(
    areas: List<String>,
    onAreaSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("지역 선택") }

    Box(
        modifier = modifier
            .fillMaxWidth()  // 필수: weight가 올바르게 동작하려면 필요
            .padding(10.dp)
            .clickable(onClick = { expanded = true })
            .background(Color(0xFFFF9800)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedOptionText,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(10.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            areas.forEach { area ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = area
                    expanded = false
                    onAreaSelected(area)
                }) {
                    Text(text = area)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF9ED2EC)  // 프리뷰에서도 배경색 확인
    ) {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCitySpinner() {
    val cities = listOf("서울", "부산", "대구")
    CitySpinner(cities, onCitySelected = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewAreaSpinner() {
    val areas = listOf("강남구", "해운대구", "수성구")
    AreaSpinner(areas, onAreaSelected = {})
}