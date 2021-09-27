package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("api/{date}")
    suspend fun getCountriesByDate(@Path("date") date: String): Response<DateObject>

    @GET("api")
    suspend fun getCountriesByDateRange(@Query("date_from") dateFrom: String, @Query("date_to") dateTo: String): Response<DateObject>

    @GET("api/{date}/country/{country}")
    suspend fun getCountryByDate(@Path("date") date: String, @Path("country") country: String): Response<DateObject>

    @GET("api/country/{country}")
    suspend fun getCountryByDateRange(@Path("country") country: String,@Query("date_from") dateFrom: String, @Query("date_to") dateTo: String): Response<DateObject>

    @GET("api/{date}/country/spain")
    suspend fun getRegionsByDate(@Path("date") date: String): Response<DateObject>

    @GET("api/country/spain")
    suspend fun getRegionsByDateRange(@Query("date_from") dateFrom: String, @Query("date_to") dateTo: String): Response<DateObject>

}