package com.example.dust_measurement_app.data.models.monitoringstation


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val monitoringStations: List<MonitoringStation?>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)