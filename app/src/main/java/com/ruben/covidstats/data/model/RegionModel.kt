package com.ruben.covidstats.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegionModel(val name: String, @SerializedName("today_confirmed")val todayConfirmed: Int, @SerializedName("today_deaths")val todayDeaths: Int,@SerializedName("today_new_confirmed") val todayNewConfirmed: Int, @SerializedName("today_new_deaths") val todayNewDeaths: Int,@SerializedName("today_open_cases") val todayOpenCases: Int
                       ,@SerializedName("today_recovered") val todayRecovered: Int) : Serializable
