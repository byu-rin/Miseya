import com.google.gson.annotations.SerializedName
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

// 대기 오염 정보 응답에서 Body에 대한 데이터 클래스
data class DustBody(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val dustItem: List<DustItem>?, // 대기 오염 항목들을 저장하는 리스트
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("numOfRows")
    val numOfRows: Int
)

// 대기 오염 정보 응답에서 Header에 대한 데이터 클래스
data class DustHeader(
    val resultCode: String,
    val resultMsg: String
)

// 대기 오염 항목에 대한 데이터 클래스
data class DustItem(
    @SerializedName("stationName") val stationName: String,
    @SerializedName("khaiValue") val khaiValue: String?, // 여기서 Int -> String으로 변경
    @SerializedName("khaiGrade") val khaiGrade: String?,
    @SerializedName("pm10Value") val pm10Value: String?,
    @SerializedName("pm10Grade") val pm10Grade: String?,
    @SerializedName("pm25Value") val pm25Value: String?,
    @SerializedName("pm25Grade") val pm25Grade: String?,
    @SerializedName("o3Value") val o3Value: String?,
    @SerializedName("o3Grade") val o3Grade: String?,
    @SerializedName("no2Value") val no2Value: String?,
    @SerializedName("no2Grade") val no2Grade: String?,
    @SerializedName("coValue") val coValue: String?,
    @SerializedName("coGrade") val coGrade: String?,
    @SerializedName("so2Value") val so2Value: String?,
    @SerializedName("so2Grade") val so2Grade: String?
)
// 대기 오염 정보 응답에 대한 데이터 클래스

