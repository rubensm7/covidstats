package com.ruben.covidstats.data.model.objects

import com.google.gson.annotations.SerializedName

data class TotalObject(@SerializedName("today_confirmed")val todayConfirmed: Int, @SerializedName("today_deaths")val todayDeaths: Int)
