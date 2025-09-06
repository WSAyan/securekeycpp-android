package com.wsayan.secureapikey.data.repository

import com.google.gson.Gson
import com.wsayan.secureapikey.AppException
import com.wsayan.secureapikey.data.network.ApiService
import com.wsayan.secureapikey.data.network.dto.AnimeItemDto
import com.wsayan.secureapikey.data.network.dto.AnimeResponseDto
import com.wsayan.secureapikey.data.network.dto.BaseResponseDto
import com.wsayan.secureapikey.data.network.dto.ImageUrls
import com.wsayan.secureapikey.data.network.dto.ImagesDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class AnimeRepositoryImplTest {

    private val mockApiService: ApiService = mockk()
    private val repository = AnimeRepositoryImpl(mockApiService)

    @Test
    fun `getAnimeList successful response`() = runTest {
        val mockDtoList = listOf(
            AnimeItemDto(
                title = "Naruto",
                url = "https://example.com/naruto",
                synopsis = "Ninja story",
                score = 8.5,
                images = ImagesDto(jpg =  ImageUrls(imageUrl = "https://example.com/naruto.jpg"))
            )
        )
        val mockResponse = AnimeResponseDto(data = mockDtoList)

        coEvery { mockApiService.getAnimeList() } returns mockResponse

        val result = repository.getAnimeList()

        assertEquals(1, result.size)
        assertEquals("Naruto", result[0].title)
        assertEquals("https://example.com/naruto", result[0].url)
        assertEquals("8.5", result[0].score)
    }

    @Test
    fun `getAnimeList empty response`() = runTest {
        val mockResponse = AnimeResponseDto(data = emptyList())
        coEvery { mockApiService.getAnimeList() } returns mockResponse

        val result = repository.getAnimeList()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAnimeList API error handling - IOException`() = runTest {
        val ioException = IOException("Network error")
        coEvery { mockApiService.getAnimeList() } throws ioException

        try {
            repository.getAnimeList()
            fail("Expected Network exception to be thrown")
        } catch (e: AppException.Network) {
            assertEquals("Network error", e.message)
        }
    }

    @Test
    fun `getAnimeList API error handling - HttpException`() = runTest {
        val errorResponse = BaseResponseDto().apply { message = "Not Found" }
        val errorJson = Gson().toJson(errorResponse)
        val httpException = HttpException(
            Response.error<Any>(404, errorJson.toResponseBody())
        )
        coEvery { mockApiService.getAnimeList() } throws httpException

        try {
            repository.getAnimeList()
            fail("Expected Server exception to be thrown")
        } catch (e: AppException.Server) {
            assertEquals("Not Found", e.message)
            assertEquals(404, e.code)
        }
    }

    @Test
    fun `getAnimeList transformation error`() = runTest {
        val mockDtoList = listOf(
            AnimeItemDto(
                title = null,
                url = "https://example.com/naruto",
                synopsis = "Ninja story",
                score = 8.5,
                images = ImagesDto(jpg =  ImageUrls(imageUrl = "https://example.com/naruto.jpg"))
            )
        )
        val mockResponse = AnimeResponseDto(data = mockDtoList)
        coEvery { mockApiService.getAnimeList() } returns mockResponse

        try {
            repository.getAnimeList()
            fail("Expected Unknown exception to be thrown")
        } catch (e: AppException.Unknown) {
            assertNotNull(e.message)
        }
    }


    @Test
    fun `getAnimeList with malformed data causing transformation failure`() = runTest {
        val mockDtoList = listOf(
            AnimeItemDto(
                title = "Valid Title",
                url = null,
                synopsis = "Ninja story",
                score = 8.5,
                images = ImagesDto(jpg =  ImageUrls(imageUrl = "https://example.com/naruto.jpg"))
            )
        )
        val mockResponse = AnimeResponseDto(data = mockDtoList)
        coEvery { mockApiService.getAnimeList() } returns mockResponse

        try {
            repository.getAnimeList()
            fail("Expected Unknown exception to be thrown")
        } catch (e: AppException.Unknown) {
            assertNotNull(e.message)
        }
    }

    @Test
    fun `getAnimeList network timeout`() = runTest {
        val timeoutException = SocketTimeoutException("Connection timed out")
        coEvery { mockApiService.getAnimeList() } throws timeoutException

        try {
            repository.getAnimeList()
            fail("Expected Network exception to be thrown")
        } catch (e: AppException.Network) {
            assertEquals("Connection timed out", e.message)
        }
    }

    @Test
    fun `getAnimeList HTTP error codes with empty error body`() = runTest {
        val httpException = HttpException(
            Response.error<Any>(500, "".toResponseBody())
        )
        coEvery { mockApiService.getAnimeList() } throws httpException

        try {
            repository.getAnimeList()
            fail("Expected Server exception to be thrown")
        } catch (e: AppException.Server) {
            assertEquals("Unknown server error", e.message)
            assertEquals(500, e.code)
        }
    }

    @Test
    fun `getAnimeList HTTP error codes with malformed error body`() = runTest {
        val httpException = HttpException(
            Response.error<Any>(400, "{invalid json}".toResponseBody())
        )
        coEvery { mockApiService.getAnimeList() } throws httpException

        try {
            repository.getAnimeList()
            fail("Expected Server exception to be thrown")
        } catch (e: AppException.Server) {
            assertEquals("Unknown server error", e.message)
            assertEquals(400, e.code)
        }
    }

    @Test
    fun `getAnimeList with different HTTP error codes`() = runTest {
        val errorCodes = listOf(401, 403, 404, 500)

        errorCodes.forEach { code ->
            val errorResponse = BaseResponseDto().apply { message = "Error $code" }
            val errorJson = Gson().toJson(errorResponse)
            val httpException = HttpException(
                Response.error<Any>(code, errorJson.toResponseBody())
            )
            coEvery { mockApiService.getAnimeList() } throws httpException

            try {
                repository.getAnimeList()
                fail("Expected Server exception to be thrown for code $code")
            } catch (e: AppException.Server) {
                assertEquals("Error $code", e.message)
                assertEquals(code, e.code)
            }
        }
    }

    @Test
    fun `getAnimeList with unknown exception type`() = runTest {
        val unknownException = RuntimeException("Some unexpected error")
        coEvery { mockApiService.getAnimeList() } throws unknownException

        try {
            repository.getAnimeList()
            fail("Expected Unknown exception to be thrown")
        } catch (e: AppException.Unknown) {
            assertEquals("Some unexpected error", e.message)
        }
    }

}