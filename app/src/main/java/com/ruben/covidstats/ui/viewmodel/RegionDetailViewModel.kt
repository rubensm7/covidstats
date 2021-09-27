package com.ruben.covidstats.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.covidstats.data.RegionDetailRepository
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.RegionModel
import com.ruben.covidstats.data.model.objects.DateObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionDetailViewModel @Inject constructor(private val repository: RegionDetailRepository): ViewModel() {

    val regionDetailModel = MutableLiveData<RegionModel>()
    val isLoading = MutableLiveData<Boolean>()

    fun getRegionByDate(date: String,region: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getRegionByDate(date,region)
            if (result != null){
                //First item - One date
                regionDetailModel.postValue(result.dates.values.first().countries.values.first().regions.first())
            }
            isLoading.postValue(false)
        }
    }

    fun getRegionByDateRange(dateFrom: String,dateTo: String,region: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getRegionByDateRange(dateFrom,dateTo,region)
            if (result != null){
                regionDetailModel.postValue(calculateRange(result))
            }
            isLoading.postValue(false)
        }
    }

    private fun calculateRange(result: DateObject): RegionModel? {
        var newConfirmedTotal = 0
        var newDeathsTotal = 0
        var openCasesTotal = 0
        var recoversTotal = 0
        var lastRegion: RegionModel? = null

        for (date in result.dates){
            //First item - One region
            newConfirmedTotal+= date.value.countries.values.first().regions.first().todayNewConfirmed
            newDeathsTotal+= date.value.countries.values.first().regions.first().todayNewDeaths
            openCasesTotal+= date.value.countries.values.first().regions.first().todayOpenCases
            recoversTotal+= date.value.countries.values.first().regions.first().todayRecovered
            lastRegion = date.value.countries.values.first().regions.first()
        }
        //We return the more recent region with the rest of the data
        return lastRegion?.let { RegionModel(it.name,it.todayConfirmed,it.todayDeaths,newConfirmedTotal,newDeathsTotal,openCasesTotal,recoversTotal) }

    }

}
