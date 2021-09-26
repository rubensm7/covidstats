package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountryService @Inject constructor(private val api: ApiClient){

    suspend fun getDataByDate(date: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getCountriesByDate(date)
            response.body()
        }
    }

    suspend fun getDataByDateRange(dateFrom: String,dateTo: String): DateObject?{
        return withContext(Dispatchers.IO){
            val response = api.getCountriesByDateRange(dateFrom,dateTo)
            response.body()
        }
    }

}