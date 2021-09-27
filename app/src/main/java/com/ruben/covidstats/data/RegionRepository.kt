package com.ruben.covidstats.data

import com.ruben.covidstats.data.model.RegionProvider
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.network.RegionService
import javax.inject.Inject

class RegionRepository @Inject constructor(private val api: RegionService, private val provider: RegionProvider) {


    suspend fun getRegionsByDate(date: String): DateObject? {
        val response = api.getRegionsByDate(date)
        provider.dateObject = response
        return response
    }

    suspend fun getRegionsByDateRange(dateFrom: String, dateTo: String): DateObject?{
        val response = api.getRegionsByDateRange(dateFrom,dateTo)
        provider.dateObject = response
        return response
    }

}
