package com.ruben.covidstats.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruben.covidstats.R
import com.ruben.covidstats.core.lineBreak
import com.ruben.covidstats.data.model.CountryModel
import com.ruben.covidstats.data.model.RegionModel
import com.ruben.covidstats.databinding.ItemCountryBinding

class RegionsAdapter:
    RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    private var regions: List<RegionModel> = emptyList()
    private lateinit var mListener: OnItemClickListener

    fun setData(newRegions: List<RegionModel>){
        regions = newRegions
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(regionModel: RegionModel)
    }

    fun onItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ViewHolder(layoutInflater.inflate(R.layout.item_country,parent, false),parent.context)
    }

    override fun getItemCount(): Int {
        return regions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = regions[position]
        holder.bind(item,mListener)
    }

    class ViewHolder(view: View, val context: Context): RecyclerView.ViewHolder(view){
        private val binding = ItemCountryBinding.bind(view)

        fun bind(regionModel: RegionModel,listener: OnItemClickListener){
            binding.tvName.text = regionModel.name
            binding.tvConfirmed.text = regionModel.todayConfirmed.lineBreak(context.getString(R.string.contagios))
            binding.tvDeath.text = regionModel.todayDeaths.lineBreak(context.getString(R.string.muertes))
            binding.clItem.setOnClickListener { listener.onItemClick(regionModel) }
        }
    }
}