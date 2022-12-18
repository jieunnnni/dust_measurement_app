package com.example.dust_measurement_app.data.models.airquality


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val measuredValues: List<MeasuredValue?>? = null,
    @SerializedName("numOfRows")
    val numOfRows: Int? = null,
    @SerializedName("pageNo")
    val pageNo: Int? = null,
    @SerializedName("totalCount")
    val totalCount: Int? = null
)