import com.google.gson.annotations.SerializedName

// 대기 오염 정보를 담는 최상위 데이터 클래스
data class Dust(
    @SerializedName("response")
    val response: DustResponse
)

// 대기 오염 정보 응답에 대한 데이터 클래스
data class DustResponse(
    @SerializedName("body")
    val dustBody: DustBody,
    @SerializedName("header")
    val dustHeader: DustHeader
)

// 대기 오염 정보 응답에서 Body에 대한 데이터 클래스
data class DustBody(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val dustItem: MutableList<DustItem>?, // 대기 오염 항목들을 저장하는 리스트
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
    val so2Grade: String,
    val coFlag: String?,
    val khaiValue: String,
    val so2Value: String,
    val coValue: String,
    val pm25Flag: String?,
    val pm10Flag: String?,
    val o3Grade: String,
    val pm10Value: String,
    val khaiGrade: String,
    val pm25Value: String,
    val sidoName: String,
    val no2Flag: String?,
    val no2Grade: String,
    val o3Flag: String?,
    val pm25Grade: String,
    val so2Flag: String?,
    val dataTime: String,
    val coGrade: String,
    val no2Value: String,
    val stationName: String,
    val pm10Grade: String,
    val o3Value: String
)
