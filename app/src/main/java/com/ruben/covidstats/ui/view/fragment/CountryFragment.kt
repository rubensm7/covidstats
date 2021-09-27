package com.ruben.covidstats.ui.view.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruben.covidstats.R
import com.ruben.covidstats.core.lineBreak
import com.ruben.covidstats.data.model.CountryModel

import com.ruben.covidstats.databinding.CountryFragmentBinding
import com.ruben.covidstats.ui.view.adapter.CountriesAdapter
import com.ruben.covidstats.ui.view.activity.CountryDetailActivity
import com.ruben.covidstats.ui.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment(), CountriesAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = CountryFragment()
    }

    private var _binding: CountryFragmentBinding? = null
    private val  binding get() = _binding!!

    private lateinit var countryViewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountryFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        clicks()
        initRvCountries()
        initGlobalData()
        isLoading()
    }

    private fun isLoading() {
        activity?.let { countryViewModel.isLoading.observe(it,{ isLoading ->
            binding.progressBar.isVisible = isLoading
        }) }
    }

    private fun initGlobalData() {
        activity?.let {
            countryViewModel.countryModelTotal.observe(it,{ total ->
                binding.tvConfirmed.text = total.todayConfirmed.lineBreak(getString(R.string.contagios))
                binding.tvDeath.text = total.todayDeaths.lineBreak(getString(R.string.muertes))
            })
        }
    }

    private fun initRvCountries() {
        val linearLayouManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        binding.rvCountries.layoutManager = linearLayouManager
        binding.rvCountries.addItemDecoration(dividerItemDecoration)
        val adapter = CountriesAdapter()
        adapter.onItemClickListener(this)
        binding.rvCountries.adapter = adapter

        activity?.let {
            countryViewModel.countryModel.observe(it,{ countries ->
                if (countries.isEmpty()){
                    binding.tvEmptyList.visibility = View.VISIBLE
                }else {
                    binding.tvEmptyList.visibility = View.GONE
                    adapter.setData(countries)
                }
            })
        }

    }

    override fun onItemClick(countryModel: CountryModel) {
        val intent = Intent(activity,CountryDetailActivity::class.java)
        intent.putExtra("country", countryViewModel.getDataFromRange(countryModel.name))
        intent.putExtra("dateStart",binding.etDateStart.text.toString())
        intent.putExtra("dateEnd",binding.etDateEnd.text.toString())
        intent.putExtra("dateRange",binding.cbRange.isChecked)
        startActivity(intent)
    }

    private fun clicks() {

        binding.etDateStart.setOnClickListener { showDateStartPickerDialog() }
        binding.etDateEnd.setOnClickListener { showDateEndPickerDialog() }
        binding.cbRange.setOnCheckedChangeListener { _, b -> binding.etDateEnd.isEnabled = b }

    }

    private fun showDateStartPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateStart.text.toString(),"") { day, month, year ->
            binding.etDateStart.setText(String.format("%d-%02d-%02d", year, month, day))
            if (binding.cbRange.isChecked && binding.etDateEnd.text.isNotEmpty()) {
                countryViewModel.getDataByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString())
            }else{
                countryViewModel.getDataByDate(binding.etDateStart.text.toString())
            }
        }
        activity?.let { datePicker.show(it.supportFragmentManager, "datePicker") }
    }

    private fun showDateEndPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateEnd.text.toString(),binding.etDateStart.text.toString()) { day, month, year ->
            binding.etDateEnd.setText(String.format("%d-%02d-%02d", year, month, day))
            countryViewModel.getDataByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString())
        }
        activity?.let { datePicker.show(it.supportFragmentManager, "datePicker") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}