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
)

// Data class of Body in response
data class DustBody(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val dustItem: List<DustItem>?, // Save the list of Air pollution information
)

 // Data class for air pollution items
data class DustItem(
    @SerializedName("stationName") val stationName: String, // 측정소명
    @SerializedName("stationCode") val stationCode: String,
    @SerializedName("dataTime") val dataTime: String,
    @SerializedName("khaiValue") val khaiValue: String?,
    @SerializedName("pm10Value") val pm10Value: String?,
    @SerializedName("pm25Value") val pm25Value: String?,
    @SerializedName("o3Value") val o3Value: String?
 )