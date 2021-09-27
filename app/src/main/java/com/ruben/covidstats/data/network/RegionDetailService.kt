package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegionDetailService @Inject constructor(private val api: ApiClient){

    suspend fun getRegionByDate(date: String,region: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getRegionByDate(date,region)
            response.body()
        }
    }

    suspend fun getRegionByDateRange(dateFrom: String, dateTo: String,region: String): DateObject? {
        return withContext(Dispatchers.IO){
            val response = api.getRegionByDateRange(region,dateFrom,dateTo)
            response.body()
        }
    }

}