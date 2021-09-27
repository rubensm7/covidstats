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
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.RegionDetailProvider
import com.ruben.covidstats.data.model.RegionModel
import com.ruben.covidstats.databinding.RegionFragmentBinding
import com.ruben.covidstats.ui.view.adapter.CountriesAdapter
import com.ruben.covidstats.ui.view.activity.CountryDetailActivity
import com.ruben.covidstats.ui.view.activity.RegionDetailActivity
import com.ruben.covidstats.ui.view.adapter.RegionsAdapter
import com.ruben.covidstats.ui.viewmodel.RegionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegionFragment : Fragment(), RegionsAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = RegionFragment()
    }

    private var _binding: RegionFragmentBinding? = null
    private val  binding get() = _binding!!

    private lateinit var regionViewModel: RegionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegionFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regionViewModel = ViewModelProvider(this).get(RegionViewModel::class.java)

        clicks()
        initRvRegions()
        isLoading()
    }

    private fun isLoading() {
        activity?.let { regionViewModel.isLoading.observe(it,{ isLoading ->
            binding.progressBar.isVisible = isLoading
        }) }
    }

    private fun initRvRegions() {
        val linearLayouManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.rvRegions.layoutManager = linearLayouManager
        binding.rvRegions.addItemDecoration(dividerItemDecoration)
        val adapter = RegionsAdapter()
        adapter.onItemClickListener(this)
        binding.rvRegions.adapter = adapter

        activity?.let {
            regionViewModel.regionModel.observe(it,{ regions ->
                if (regions.isEmpty()){
                    binding.tvEmptyList.visibility = View.VISIBLE
                }else {
                    binding.tvEmptyList.visibility = View.GONE
                    adapter.setData(regions)
                }
            })
        }

    }

    override fun onItemClick(regionModel: RegionModel) {
        val intent = Intent(activity, RegionDetailActivity::class.java)
        intent.putExtra("country", regionViewModel.getDataFromRange(regionModel.name))
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
                regionViewModel.getRegionsByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString())
            }else{
                regionViewModel.getRegionsByDate(binding.etDateStart.text.toString())
            }
        }
        activity?.let { datePicker.show(it.supportFragmentManager, "datePicker") }
    }

    private fun showDateEndPickerDialog() {
        val datePicker = DatePickerFragment(binding.etDateEnd.text.toString(),binding.etDateStart.text.toString()) { day, month, year ->
            binding.etDateEnd.setText(String.format("%d-%02d-%02d", year, month, day))
            regionViewModel.getRegionsByDateRange(binding.etDateStart.text.toString(),binding.etDateEnd.text.toString())
        }
        activity?.let { datePicker.show(it.supportFragmentManager, "datePicker") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}