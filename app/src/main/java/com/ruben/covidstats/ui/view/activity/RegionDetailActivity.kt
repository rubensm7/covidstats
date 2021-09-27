package com.ruben.covidstats.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ruben.covidstats.R
import com.ruben.covidstats.core.lineBreak
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.RegionModel
import com.ruben.covidstats.databinding.ActivityRegionDetailBinding
import com.ruben.covidstats.ui.view.fragment.DatePickerFragment
import com.ruben.covidstats.ui.viewmodel.CountryDetailViewModel
import com.ruben.covidstats.ui.viewmodel.RegionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegionDetailBinding
    private val regionDetailViewModel: RegionDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent.getSerializableExtra("country") as RegionModel).apply {
            setDataToViews(this)
        }
        binding.etDateStart.setText(intent.getStringExtra("dateStart"))
        binding.etDateEnd.setText(intent.getStringExtra("dateEnd"))
        binding.cbRange.isChecked = intent.getBooleanExtra("dateRange",false)
        binding.etDateEnd.isEnabled = intent.getBooleanExtra("dateRange",false)

        regionDetailViewModel.regionDetailModel.observe(this){ regionModel ->
            setDataToViews(regionModel)
        }

        isLoading()
        clicks()
    }

    private fun setDataToViews(regionModel: RegionModel){
        binding.tvCountry.text = regionModel.name
        binding.tvConfirmed.text = regionModel.todayConfirmed.lineBreak(getString(R.string.contagios))
        binding.tvDeath.text = regionModel.todayDeaths.lineBreak(getString(R.string.muertes))
        binding.tvNewConfirmed.text = regionModel.todayNewConfirmed.lineBreak(getString(R.string.nuevos_contagios))
        binding.tvNewDeath.text = regionModel.todayNewDeaths.lineBreak(getString(R.string.nuevas_muertes))
        binding.tvCases.text = regionModel.todayOpenCases.lineBreak(getString(R.string.casos_abiertos))
        binding.tvRecovers.text = regionModel.todayRecovered.lineBreak(getString(R.string.recuperados))
    }

    private fun isLoading() {
        regionDetailViewModel.isLoading.observe(this){ isLoading ->
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
                regionDetailViewModel.getRegionByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString(),binding.tvCountry.text.toString())
            }else{
                regionDetailViewModel.getRegionByDate(binding.etDateStart.text.toString(),binding.tvCountry.text.toString())
            }
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun showDateEndPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateEnd.text.toString(),binding.etDateStart.text.toString()) { day, month, year ->
            binding.etDateEnd.setText(String.format("%d-%02d-%02d", year, month, day))
            regionDetailViewModel.getRegionByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString(),binding.tvCountry.text.toString())
        }
        datePicker.show(supportFragmentManager, "datePicker")
    }


}