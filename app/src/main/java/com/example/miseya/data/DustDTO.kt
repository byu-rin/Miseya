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
    val so2Grade: String, // 아황산가스 플래그
    val coFlag: String?, // 일산화탄소
    val khaiValue: String, // 통합대기환경수치
    val so2Value: String, // 아황산가스 농도
    val coValue: String, // 일산화탄소 농도
    val pm25Flag: String?, // 미세먼지
    val pm10Flag: String?, // 미세먼지
    val o3Grade: String, // 오존 지수
    val pm10Value: String, // 미세먼지 농도
    val khaiGrade: String, // 통합대기환경지수
    val pm25Value: String, // 미세먼지 농도
    val sidoName: String, // 시도 명
    val no2Flag: String?, // 이산화질소 플래그
    val no2Grade: String, // 이산화질소 지수
    val o3Flag: String?, // 오존 플래그
    val pm25Grade: String, // 미세먼지 24시간 등급
    val so2Flag: String?, // 아황산가스 플래그
    val dataTime: String, // 통보시간
    val coGrade: String, // 일산화탄소 지수
    val no2Value: String, // 이산화질소 농도
    val stationName: String, // 측정소 명
    val pm10Grade: String, // Adjust code style settings
    val o3Value: String // 오존 농도
)
