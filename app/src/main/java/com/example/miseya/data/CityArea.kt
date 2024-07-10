package com.example.miseya.data

data class CityArea(
    val city: String,
    val areas: List<String>
)

val cityAreas = listOf(
    CityArea("서울", listOf("강남구", "서초구", "강서구", "마포구", "동작구")),
    CityArea("부산", listOf("광복동", "초량동", "청룡동", "좌동", "재송동")),
    CityArea("대구", listOf("신암동", "수창동", "지산동", "서호동", "이현동")),
    CityArea("인천", listOf("송도동", "구월동", "부평동", "가좌동", "작전동")),
    CityArea("광주", listOf("산수동", "화정동", "주월동", "문흥동", "송정동")),
    CityArea("대전", listOf("문평동", "구성동", "노은동", "관평동", "대성동")),
    CityArea("울산", listOf("화산리", "상남리", "약사동", "범서읍", "송정동")),
    CityArea("경기", listOf("내동", "보산동", "봉산동", "중앙동", "가평")),
    CityArea("강원", listOf("중앙동", "옥천동", "노학동", "남양동", "홍천읍")),
    CityArea("충북", listOf("흥덕구", "중앙탑면", "중앙동", "보은읍", "옥천읍")),
    CityArea("충남", listOf("동남구", "중동", "대천동", "온양동", "동문동")),
    CityArea("전북", listOf("완산구", "영등동", "금동", "요촌동", "삼례읍")),
    CityArea("전남", listOf("장흥읍", "진도읍", "신안군", "곡성읍", "목포항")),
    CityArea("경북", listOf("우현동", "장흥동", "대도동", "오천읍", "연일읍")),
    CityArea("경남", listOf("경화동", "월영동", "금성면", "동상동", "장유동")),
    CityArea("제주", listOf("이도동", "연동", "조천읍", "한림읍", "애월읍")),
    CityArea("세종", listOf("보람동", "한솔동", "전의면"))
)
