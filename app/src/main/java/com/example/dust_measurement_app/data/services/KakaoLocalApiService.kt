package com.example.dust_measurement_app.data.services

import com.example.dust_measurement_app.BuildConfig
import com.example.dust_measurement_app.data.models.tmcoordinates.TmCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoLocalApiService {
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}") // API_KEY 전달
    @GET("v2/local/geo/transcoord.json?output_coord=TM")
    suspend fun getTmCoordinates(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): Response<TmCoordinatesResponse>
}