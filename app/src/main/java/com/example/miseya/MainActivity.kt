package com.android.miseya

import DustItem
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.miseya.databinding.ActivityMainBinding
import com.example.miseya.retrofit.NetWorkClient
import com.example.miseya.R
import com.skydoves.powerspinner.IconSpinnerAdapter
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // DustItem 객체를 저장하는 리스트
    var items = mutableListOf<DustItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 스피너 아이템 선택 리스너 설정
        binding.spinnerViewSido.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            communicateNetWork(setUpDustParameter(text))
        }

        // 스피너(binging.spinnerViewGoo) 아이템 선택 리스너 설정
        binding.spinnerViewGoo.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->

            // 선택된 항목의 이름 로그로 출력
            Log.d("miseya", "selectedItem: spinnerViewGoo selected >  $text")

            // 선택된 항목의 이름으로 DustItem 객체를 찾아서 selectedItem에 저장
            var selectedItem = items.filter { f -> f.stationName == text }

            // selectedItem의 데이터 로그로 출력
            Log.d("miseya", "selectedItem: sidoName > " + selectedItem[0].sidoName)
            Log.d("miseya", "selectedItem: pm10Value > " + selectedItem[0].pm10Value)

            // UI 구성 요소를 선택된 항목의 속성에 따라 업데이트
            binding.tvCityname.text = selectedItem[0].sidoName + "  " + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvP10value.text = selectedItem[0].pm10Value + " ㎍/㎥"

            when (getGrade(selectedItem[0].pm10Value)) {
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.mise1)
                    binding.tvP10grade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.mise2)
                    binding.tvP10grade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.mise3)
                    binding.tvP10grade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.mise4)
                    binding.tvP10grade.text = "매우나쁨"
                }
            }
        }
    }

    // 네트워크 통신 함수. Coroutine을 사용하여 비동기적으로 실행됨.
    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
        // Retrofit 사용하여 서버에서 데이터 가져옴
        val responseData = NetWorkClient.dustNetWork.getDust(param)
        // 가져온 데이터를 로그에 출력합니다.
        Log.d("Parsing Dust ::", responseData.toString())

        // 가져온 데이터에서 DustItem 리스트를 추출하여 items에 저장
        items = responseData.response.dustBody.dustItem!!

        // 스피너에 표시할 도시 이름을 저장하는 ArrayList를 생성
        val goo = ArrayList<String>()

        // items 리스트 순회하면서 각 항목의 도시 이름을 로그에 출력하고 goo 리스트에 추가
        items.forEach {
            Log.d("add Item :", it.stationName)
            goo.add(it.stationName)
        }

        // UI 업데이트는 메인에서. 따라서 runOnUiThread를 사용하여 스피너 업데이트
        runOnUiThread {
            binding.spinnerViewGoo.setItems(goo)
        }
    }

    // 미세먼지 데이터를 가져오기 위한 파라미터를 설정하는 함수
    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        // 서비스에 사용될 키 값
        val authKey ="secret-api-key"

        // HashMap 사용하여 파라미터를 설정, 반환
        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    // 미세먼지 등급을 가져오는 함수
    fun getGrade(value: String): Int {

        // 문자열 형태의 값을 정수로 변환
        val mValue = value.toInt()

        // 미세먼지 등급을 계산하고 반환
        var grade = 1
        grade = if (mValue >= 0 && mValue <= 30) {
            1
        } else if (mValue >= 31 && mValue <= 80) {
            2
        } else if (mValue >= 81 && mValue <= 100) {
            3
        } else 4
        return grade
    }
}