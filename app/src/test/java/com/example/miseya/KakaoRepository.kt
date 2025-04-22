package com.example.miseya

import com.example.miseya.Api.KakaoRepository
import com.example.miseya.data.Document
import com.example.miseya.data.Meta
import com.example.miseya.data.TmCoordinatesResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import retrofit2.Response

@Suppress("unused")
interface KakaoRepository {
    suspend fun fetchTMCoordinate(lat: Double, lng: Double): Response<TmCoordinatesResponse>
}

class KakaRepositoryTest {
    private lateinit var kakaoRepository: KakaoRepository

    @Before
    fun setup() {
        kakaoRepository = mockk()
    }

    @Test
    fun `fetchTMCoordinate should return valid TM coordinates` () = runBlocking {
        // Mock 응답 데이터생성
        val mockResponse = Response.success(
            TmCoordinatesResponse(
                meta = Meta(totalCount = 1),
                documents = listOf(
                    Document(x = 4433595.673, y = 6392999.216)
                ),
            )
        )

        coEvery { kakaoRepository.fetchTMCoordinate(37.4436548, 127.1635096) } returns mockResponse

        // 실제 함수 호출
        val response = kakaoRepository.fetchTMCoordinate(37.4436548, 127.1635096)

        assertEquals(true, response.isSuccessful)
        assertEquals(4433595.673, response.body()?.documents?.firstOrNull()?.x)
        assertEquals(6392999.216, response.body()?.documents?.firstOrNull()?.y)
    }

    @Test
    fun `fetchTMCoordinate should handle API failure`() = runBlocking {
        // Mock 실패 응답
        val errorResponse = Response.error<TmCoordinatesResponse>(
            400, "{\"error\":\"Invalid request\"}".toResponseBody("application/json".toMediaType())
        )

        coEvery { kakaoRepository.fetchTMCoordinate(0.0, 0.0) } returns errorResponse

        // 실제 함수 호출
        val response = kakaoRepository.fetchTMCoordinate(0.0, 0.0)

        // 실패 검증
        assertEquals(false, response.isSuccessful)
        assertEquals(400, response.code())
    }
}