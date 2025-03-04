package com.example.miseya.data
data class MeasureDTO(
    val response: MeasureResponse?
)

data class MeasureResponse(
    val body: Body?,
    val header: Header?
)

data class Body(
    val items: List<MeasureItem>?,
    val numOfRows: Int?,
    val pageNo: Int?,
    val totalCount: Int?
)

data class Header(
    val resultCode: String?,
    val resultMsg: String?
)

data class MeasureItem(
    val addr: String,
    val stationCode: String,
    val stationName: String,
    val tm: Double
)
