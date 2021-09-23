package com.ruben.covidstats.model

import com.google.gson.annotations.SerializedName

data class CountryModel(val name: String, @SerializedName("today_confirmed")val todayConfirmed: Int, @SerializedName("today_deaths")val todayDeaths: Int)
