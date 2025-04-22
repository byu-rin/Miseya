import com.google.gson.annotations.SerializedName
/*
API에서 받아오는 JSON 응답을 Kotlin 객체로 변환
 */
data class DustResponse(
    @SerializedName("response")
    val response: ApiResponse
)

data class ApiResponse(
    @SerializedName("body")
    val body: DustBody,
    @SerializedName("header")
    val header: DustHeader
)

// Data class of Body in response
data class DustBody(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val dustItem: List<DustItem>?, // Save the list of Air pollution information
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("numOfRows")
    val numOfRows: Int
)

// Data class of Header in response
data class DustHeader(
    val resultCode: String,
    val resultMsg: String
)

 // Data class for air pollution items
data class DustItem(
    @SerializedName("stationName") val stationName: String, // 측정소명
    @SerializedName("sidoName") val sidoName: String, // 시도명
    @SerializedName("dataTime") val dataTime: String, //
    @SerializedName("khaiValue") val khaiValue: String?, // 여기서 Int -> String으로 변경
    @SerializedName("pm10Value") val pm10Value: String?,
    @SerializedName("pm25Value") val pm25Value: String?,
    @SerializedName("o3Value") val o3Value: String?
 )

/* 출력 결과
결과코드 resultCode : 결과코드
결과메시지 resultMsg : 결과메시지
...https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073861
* */