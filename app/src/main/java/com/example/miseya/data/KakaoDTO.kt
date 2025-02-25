package com.example.miseya.data

import com.google.gson.annotations.SerializedName
// api 에서 받아오는 json 응답을 kotlin 객체로 변환

data class Document(
    @SerializedName("x")
    val x: Double?,
    @SerializedName("y")
    val y: Double?
)

// Meta.kt
data class Meta(
    @SerializedName("total_count")
    val totalCount: Int?
)

// TmCoordinatesResponse.kt
data class TmCoordinatesResponse(
    @SerializedName("documents")
    val documents: List<Document>?,
    @SerializedName("meta")
    val meta: Meta?
)