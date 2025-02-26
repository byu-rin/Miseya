package com.example.miseya.data

// 대기질 분류 유틸 클래스
object AirQualityClassifier {
    fun classifyAirQuality(pm10Value: String?, pm25Value: String?, o3Value: String?): String
    {
        val pm10Int = pm10Value?.toIntOrNull()
        val pm25Int = pm25Value?.toIntOrNull()
        val o3Double = o3Value?.toDoubleOrNull()

        val pm10Grade = when {
            pm10Int == null -> 0
            pm10Int <= 30 -> 1
            pm10Int <= 80 -> 2
            pm10Int <= 150 -> 3
            else -> 4
        }

        val pm25Grade = when {
            pm25Int == null -> 0
            pm25Int <= 15 -> 1
            pm25Int <= 35 -> 2
            pm25Int <= 75 -> 3
            else -> 4
        }

        val o3Grade = when {
            o3Double == null -> 0
            o3Double <= 0.030 -> 1
            o3Double <= 0.090 -> 2
            o3Double <= 0.150 -> 3
            else -> 4
        }
        val averageGrade = (pm10Grade + pm25Grade + o3Grade) / 3.0

        return when {
            averageGrade <= 1 -> "좋음"
            averageGrade <= 2 -> "보통"
            averageGrade <= 3 -> "나쁨"
            else -> "매우 나쁨"
        }
    }
}