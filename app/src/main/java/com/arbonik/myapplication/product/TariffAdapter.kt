package com.arbonik.myapplication.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.myapplication.R

class TariffAdapter(var tariffs : LiveData<MutableList<Tariff>>) : RecyclerView.Adapter<TariffAdapter.TariffViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = TariffViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent))

    override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
        holder.onBind(tariffs.value?.get(position))
    }

    override fun getItemCount() = tariffs.value?.let { it.size } ?: 0

    inner class TariffViewHolder(item : View) : RecyclerView.ViewHolder(item){
        fun onBind(tariff: Tariff?) {

        }
    }
}