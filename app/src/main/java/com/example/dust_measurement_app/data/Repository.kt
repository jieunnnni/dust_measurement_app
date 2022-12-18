package com.example.dust_measurement_app.data

import com.example.dust_measurement_app.BuildConfig
import com.example.dust_measurement_app.data.models.airquality.MeasuredValue
import com.example.dust_measurement_app.data.models.monitoringstation.MonitoringStation
import com.example.dust_measurement_app.data.services.AirKoreaApiService
import com.example.dust_measurement_app.data.services.KakaoLocalApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {

    suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double): MonitoringStation? {
        val tmCoordinates = kakaoLocalApiService
            .getTmCoordinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y

        return airKoreaApiService
            .getNearbyMonitoringStation(tmX!!, tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            // 선택한 요소를 비교해 가장 작은 값을 전달하고 null인 값은 자동으로 후순위로 밀림
            // => 가장 가까운 측정소 하나만 받아오게 됨
            ?.minByOrNull { it?.tm ?: Double.MAX_VALUE }
    }

    suspend fun getLatestAirQualityData(stationName: String): MeasuredValue? =
        airKoreaApiService
            .getRealtimeAirQualties(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull()

    private val airKoreaApiService: AirKoreaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.AIR_KOREA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private val kakaoLocalApiService: KakaoLocalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private fun buildHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        // DEBUG 일때만 다 보여주기
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        // NONE : 보여주지 않음
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}