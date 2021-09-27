package com.ruben.covidstats.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.covidstats.data.CountryDetailRepository
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.objects.DateObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(private val repository: CountryDetailRepository) : ViewModel() {

    val countryDetailModel = MutableLiveData<CountryModel>()
    val isLoading = MutableLiveData<Boolean>()

    fun getCountryByDate(date: String,country: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getCountryByDate(date,country)
            if (result != null){
                //First item - One date
                countryDetailModel.postValue(result.dates.values.first().countries.values.first())
            }
            isLoading.postValue(false)
        }
    }

    fun getCountryByDateRange(dateFrom: String,dateTo: String,country: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getCountryByDateRange(dateFrom,dateTo,country)
            if (result != null){
                countryDetailModel.postValue(calculateRange(result))
            }
            isLoading.postValue(false)
        }
    }

    private fun calculateRange(result: DateObject): CountryModel? {
        var newConfirmedTotal = 0
        var newDeathsTotal = 0
        var openCasesTotal = 0
        var recoversTotal = 0
        var lastCountry: CountryModel? = null

        for (date in result.dates){
            //First item - One country
            newConfirmedTotal+= date.value.countries.values.first().todayNewConfirmed
            newDeathsTotal+= date.value.countries.values.first().todayNewDeaths
            openCasesTotal+= date.value.countries.values.first().todayOpenCases
            recoversTotal+= date.value.countries.values.first().todayRecovered
            lastCountry = date.value.countries.values.first()
        }
        //We return the more recent country with the rest of the data
        return lastCountry?.let { CountryModel(it.name,it.todayConfirmed,it.todayDeaths,newConfirmedTotal,newDeathsTotal,openCasesTotal,recoversTotal) }

    }


}