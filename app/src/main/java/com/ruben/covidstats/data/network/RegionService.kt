package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegionService @Inject constructor(private val api: ApiClient){

    suspend fun getRegionsByDate(date: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getRegionsByDate(date)
            response.body()
        }
    }

    suspend fun getRegionsByDateRange(dateFrom: String,dateTo: String): DateObject?{
        return withContext(Dispatchers.IO){
            val response = api.getRegionsByDateRange(dateFrom,dateTo)
            response.body()
        }
    }

}