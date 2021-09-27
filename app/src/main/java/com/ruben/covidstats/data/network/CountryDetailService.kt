package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountryDetailService @Inject constructor(private val api: ApiClient){

    suspend fun getCountryByDate(date: String,country: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getCountryByDate(date,country)
            response.body()
        }
    }

    suspend fun getCountryByDateRange(dateFrom: String, dateTo: String,country: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getCountryByDateRange(country,dateFrom,dateTo)
            response.body()
        }
    }

}