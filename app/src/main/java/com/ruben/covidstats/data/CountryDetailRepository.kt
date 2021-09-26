package com.ruben.covidstats.data

import com.ruben.covidstats.data.model.CountryProvider
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.network.CountryService
import javax.inject.Inject

class CountryRepository @Inject constructor(private val api: CountryService, private val provider: CountryProvider) {


    suspend fun getDataByDate(date: String): DateObject? {
        val response = api.getDataByDate(date)
        provider.dateObject = response
        return response
    }

    suspend fun getDataByDateRange(dateFrom: String, dateTo: String): DateObject?{
        val response = api.getDataByDateRange(dateFrom,dateTo)
        provider.dateObject = response
        return response
    }

}
