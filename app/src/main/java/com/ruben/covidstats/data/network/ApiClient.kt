package com.ruben.covidstats.data.network

import com.ruben.covidstats.data.model.objects.DateObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface CountryApiClient {

    @GET("{date}")
    suspend fun getCountriesByDate(@Path("date") date: String): Response<DateObject>

}