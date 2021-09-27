package com.ruben.covidstats.data

import com.ruben.covidstats.data.model.RegionDetailProvider
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.network.RegionDetailService
import javax.inject.Inject

class RegionDetailRepository @Inject constructor(private val api: RegionDetailService, private val provider: RegionDetailProvider) {

    suspend fun getRegionByDate(date: String, region: String): DateObject?{
        val response = api.getRegionByDate(date,region)
        provider.dateObject = response
        return response
    }

    suspend fun getRegionByDateRange(dateFrom: String, dateTo: String, region: String): DateObject?{
        val response = api.getRegionByDateRange(dateFrom,dateTo,region)
        provider.dateObject = response
        return response
    }


}
