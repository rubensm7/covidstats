package com.ruben.covidstats.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ruben.covidstats.databinding.CountryFragmentBinding
import com.ruben.covidstats.ui.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : Fragment() {

    companion object {
        fun newInstance() = CountryFragment()
    }

    private var _binding: CountryFragmentBinding? = null
    private val  binding get() = _binding!!

    private lateinit var countryViewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CountryFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        clicks()
    }

    private fun clicks() {

    }

    private fun showDatePickerDialog() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}