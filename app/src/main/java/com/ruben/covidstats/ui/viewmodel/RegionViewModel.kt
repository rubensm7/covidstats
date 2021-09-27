package com.ruben.covidstats.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.covidstats.data.RegionRepository
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.RegionModel
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.model.objects.TotalObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionViewModel @Inject constructor(private val repository: RegionRepository): ViewModel() {

    val regionModel = MutableLiveData<List<RegionModel>>()
    private val regionModelDateObject = MutableLiveData<DateObject>()
    val isLoading = MutableLiveData<Boolean>()

    fun getRegionsByDate(date: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getRegionsByDate(date)
            if (result != null) {
                //First item - One date
                regionModel.postValue(result.dates.values.first().countries.values.first().regions)
            }else{
                regionModel.postValue(emptyList())
            }
            isLoading.postValue(false)
        }
    }

    fun getRegionsByDateRange(dateFrom: String, dateTo: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getRegionsByDateRange(dateFrom,dateTo)
            if (result != null){
                //We send the more recent region (Has the more recent data)
                regionModel.postValue(result.dates.values.last().countries.values.last().regions)
                //Save data to send it to other activity
                regionModelDateObject.postValue(result!!)
            }else{
                regionModel.postValue(emptyList())
            }
            isLoading.postValue(false)
        }
    }

    fun getDataFromRange(regionName: String): RegionModel?{
        var newConfirmedTotal = 0
        var newDeathsTotal = 0
        var openCasesTotal = 0
        var recoversTotal = 0
        var lastRegion: RegionModel? = null

        for (date in regionModelDateObject.value?.dates!!){
            for (country in date.value.countries){
                for (region in country.value.regions){
                    if (region.name == regionName){
                        newConfirmedTotal+= region.todayNewConfirmed
                        newDeathsTotal+= region.todayNewDeaths
                        openCasesTotal+= region.todayOpenCases
                        recoversTotal+= region.todayRecovered
                        lastRegion = region
                    }
                }
            }

        }

        return lastRegion?.let { RegionModel(it.name,it.todayConfirmed,it.todayDeaths,newConfirmedTotal,newDeathsTotal,openCasesTotal,recoversTotal) }

    }
}