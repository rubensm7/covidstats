package com.ruben.covidstats.data

import com.ruben.covidstats.data.model.CountryDetailProvider
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.network.CountryDetailService
import com.ruben.covidstats.data.network.CountryService
import javax.inject.Inject

class CountryDetailRepository @Inject constructor(private val api: CountryDetailService, private val provider: CountryDetailProvider) {

    suspend fun getCountryByDate(date: String, country: String): DateObject?{
        val response = api.getCountryByDate(date,country)
        provider.dateObject = response
        return response
    }

    suspend fun getCountryByDateRange(dateFrom: String, dateTo: String, country: String): DateObject?{
        val response = api.getCountryByDateRange(dateFrom,dateTo,country)
        provider.dateObject = response
        return response
    }


}
