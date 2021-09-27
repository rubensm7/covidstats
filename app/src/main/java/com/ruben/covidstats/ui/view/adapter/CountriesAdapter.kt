package com.ruben.covidstats.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruben.covidstats.R
import com.ruben.covidstats.core.lineBreak
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.databinding.ItemCountryBinding

class CountriesAdapter:
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    private var countries: List<CountryModel> = emptyList()
    private lateinit var mListener: OnItemClickListener

    fun setData(newCountries: List<CountryModel>){
        countries = newCountries
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(countryModel: CountryModel)
    }

    fun onItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ViewHolder(layoutInflater.inflate(R.layout.item_country,parent, false),parent.context)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries[position]
        holder.bind(item,mListener)
    }

    class ViewHolder(view: View, val context: Context): RecyclerView.ViewHolder(view){
        private val binding = ItemCountryBinding.bind(view)

        fun bind(countryModel: CountryModel,listener: OnItemClickListener){
            binding.tvName.text = countryModel.name
            binding.tvConfirmed.text = countryModel.todayConfirmed.lineBreak(context.getString(R.string.contagios))
            binding.tvDeath.text = countryModel.todayDeaths.lineBreak(context.getString(R.string.muertes))
            binding.clItem.setOnClickListener { listener.onItemClick(countryModel) }
        }
    }
}