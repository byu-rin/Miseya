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
    @SerializedName("stationName") val stationName: String,
    @SerializedName("sidoName") val sidoName: String,
    @SerializedName("dataTime") val dataTime: String,
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

