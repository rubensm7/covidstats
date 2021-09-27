package com.ruben.covidstats.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ruben.covidstats.R
import com.ruben.covidstats.core.lineBreak
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.databinding.ActivityCountryDetailBinding
import com.ruben.covidstats.ui.view.fragment.DatePickerFragment
import com.ruben.covidstats.ui.viewmodel.CountryDetailViewModel
import com.ruben.covidstats.ui.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding
    private val countryDetailViewModel: CountryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent.getSerializableExtra("country") as CountryModel).apply {
            setDataToViews(this)
        }
        binding.etDateStart.setText(intent.getStringExtra("dateStart"))
        binding.etDateEnd.setText(intent.getStringExtra("dateEnd"))
        binding.cbRange.isChecked = intent.getBooleanExtra("dateRange",false)
        binding.etDateEnd.isEnabled = intent.getBooleanExtra("dateRange",false)

        countryDetailViewModel.countryDetailModel.observe(this){ countryModel ->
            setDataToViews(countryModel)
        }

        isLoading()
        clicks()
    }

    private fun setDataToViews(countryModel: CountryModel){
        binding.tvCountry.text = countryModel.name
        binding.tvConfirmed.text = countryModel.todayConfirmed.lineBreak(getString(R.string.contagios))
        binding.tvDeath.text = countryModel.todayDeaths.lineBreak(getString(R.string.muertes))
        binding.tvNewConfirmed.text = countryModel.todayNewConfirmed.lineBreak(getString(R.string.nuevos_contagios))
        binding.tvNewDeath.text = countryModel.todayNewDeaths.lineBreak(getString(R.string.nuevas_muertes))
        binding.tvCases.text = countryModel.todayOpenCases.lineBreak(getString(R.string.casos_abiertos))
        binding.tvRecovers.text = countryModel.todayRecovered.lineBreak(getString(R.string.recuperados))
    }

    private fun isLoading() {
        countryDetailViewModel.isLoading.observe(this){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun clicks() {
        binding.ivBack.setOnClickListener { finish() }
        binding.etDateStart.setOnClickListener { showDateStartPickerDialog() }
        binding.etDateEnd.setOnClickListener { showDateEndPickerDialog() }
        binding.cbRange.setOnCheckedChangeListener { _, b -> binding.etDateEnd.isEnabled = b }
    }

    private fun showDateStartPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateStart.text.toString(),"") { day, month, year ->
            binding.etDateStart.setText(String.format("%d-%02d-%02d", year, month, day))
            if (binding.cbRange.isChecked && binding.etDateEnd.text.isNotEmpty()) {
                countryDetailViewModel.getCountryByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString(),binding.tvCountry.text.toString())
            }else{
                countryDetailViewModel.getCountryByDate(binding.etDateStart.text.toString(),binding.tvCountry.text.toString())
            }
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun showDateEndPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateEnd.text.toString(),binding.etDateStart.text.toString()) { day, month, year ->
            binding.etDateEnd.setText(String.format("%d-%02d-%02d", year, month, day))
            countryDetailViewModel.getCountryByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString(),binding.tvCountry.text.toString())
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }


}