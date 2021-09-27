package com.ruben.covidstats.data.model.objects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DateObject(@SerializedName("dates")@Expose val dates: Map<String, CountriesObject>,val total: TotalObject)
