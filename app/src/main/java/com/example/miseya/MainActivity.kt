package com.android.miseya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.miseya.MainViewModel
import com.example.miseya.R

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
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFF9ED2EC) // 화면 배경색
    ) {
        // 도시와 구역 스피너를 수평으로 배열
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CityAreaSpinners(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Imoge(viewModel)
            }
            Spacer(Modifier.weight(1f)) // 이 스페이서는 ImogeExample 아래쪽에 추가 공간을 제공합니다.
        }
    }
}

@Composable
fun CityAreaSpinners(viewModel: MainViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spinner(
            items = viewModel.cities,
            label = "도시 선택",
            onItemSelected = viewModel::setSelectedCity,
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

@Composable
fun Spinner(
    items: List<String>,
    label: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(label) }

    Box(
        modifier = modifier
            .fillMaxWidth()  // 필수: weight가 올바르게 동작하려면 필요
            .padding(10.dp)
            .clickable(onClick = { expanded = true })
            .background(Color(0xFFD6A478)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = selectedOptionText, color = Color.White, modifier = Modifier.padding(10.dp))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedOptionText = item
                    expanded = false
                    onItemSelected(item)
                }) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun Imoge(viewModel: MainViewModel) {
    val airQualityClassification by viewModel.airQualityClassification.collectAsState()
    val dustData by viewModel.dustData.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (location, date, data, level) = createRefs()

        // API 응답에서 받은 정보 표시
        val locationText = if (dustData != null) {
            "${dustData?.sidoName} ${dustData?.stationName}"
        } else {
            "location"
        }

        Text(
            text = locationText,
            color = Color.Black,
            modifier = Modifier.constrainAs(location) {
                top.linkTo(parent.top, margin = 80.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "date",
            color = Color.Black,
            modifier = Modifier.constrainAs(date) {
                top.linkTo(location.bottom, margin = 50.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "data",
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
            color = Color.Black,
            modifier = Modifier.constrainAs(level) {
                bottom.linkTo(parent.bottom, margin = 80.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
    Box(
        modifier = Modifier.size(200.dp, 200.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.mise1),
            contentDescription = "Imoge",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    MainContent()
}