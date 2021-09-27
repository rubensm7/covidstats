package com.ruben.covidstats.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.covidstats.data.CountryRepository
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.objects.CountriesObject
import com.ruben.covidstats.data.model.objects.DateObject
import com.ruben.covidstats.data.model.objects.TotalObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val repository: CountryRepository): ViewModel() {

    val countryModel = MutableLiveData<List<CountryModel>>()
    val countryModelTotal = MutableLiveData<TotalObject>()
    private val countryModelDateObject = MutableLiveData<DateObject>()
    val isLoading = MutableLiveData<Boolean>()

    fun getDataByDate(date: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getDataByDate(date)
            if (result != null) {
                countryModelTotal.postValue(result.total)
                //First item - One date
                countryModel.postValue(result.dates.values.first().countries.values.toList())
                //Save data to send it to other activity
                countryModelDateObject.postValue(result!!)
            }else{
                countryModel.postValue(emptyList())
            }
            isLoading.postValue(false)
        }
    }

    fun getDataByDateRange(dateFrom: String, dateTo: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getDataByDateRange(dateFrom,dateTo)
            if (result != null){
                countryModelTotal.postValue(result.total)
                //We send the more recent country (Has the more recent data)
                countryModel.postValue(result.dates.values.last().countries.values.toList())
                //Save data to send it to other activity
                countryModelDateObject.postValue(result!!)
            }else{
                countryModel.postValue(emptyList())
            }
            isLoading.postValue(false)
        }
    }

    fun getDataFromRange(countryName: String): CountryModel?{
        var newConfirmedTotal = 0
        var newDeathsTotal = 0
        var openCasesTotal = 0
        var recoversTotal = 0
        var lastCountry: CountryModel? = null

        for (date in countryModelDateObject.value?.dates!!){
            for (country in date.value.countries){
                if (country.key == countryName){
                    newConfirmedTotal+= country.value.todayNewConfirmed
                    newDeathsTotal+= country.value.todayNewDeaths
                    openCasesTotal+= country.value.todayOpenCases
                    recoversTotal+= country.value.todayRecovered
                    lastCountry = country.value
                }
            }

        }

        return lastCountry?.let { CountryModel(it.name,it.todayConfirmed,it.todayDeaths,newConfirmedTotal,newDeathsTotal,openCasesTotal,recoversTotal,
            emptyList()) }

    }

}