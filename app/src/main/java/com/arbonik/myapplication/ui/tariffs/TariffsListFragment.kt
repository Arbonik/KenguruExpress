package com.arbonik.myapplication.ui.tariffs

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.TariffDataItemBinding
import com.arbonik.myapplication.network.models.tariff.Data
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class TariffsListFragment : Fragment() {

    private val viewModel: TariffsListViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val root =  inflater.inflate(R.layout.tariffs_list_fragment, container, false)

        val tariffRecycler = root.findViewById<RecyclerView>(R.id.tariffDataRecycler)

        tariffRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.dao.observe(viewLifecycleOwner){
            tariffRecycler.adapter = TariffDataAdapter(it)
            tariffRecycler.adapter!!.notifyDataSetChanged()
        }

        viewModel.sorted.observe(viewLifecycleOwner){
            when (it){
                TariffSortedTypes.DEFAULT -> {};
                TariffSortedTypes.PRICE -> tariffRecycler.adapter =
                    TariffDataAdapter(viewModel.dao.value!!.sortedBy { it.price })
                TariffSortedTypes.DELIVERY -> tariffRecycler.adapter =
                    TariffDataAdapter(viewModel.dao.value!!.sortedBy { rangeBetweenDays(it.pickup_day!!, it.delivery_day!!)})
            }
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun rangeBetweenDays(dayStart:String, dayFinish : String): Long {
        return LocalDate.parse(dayStart).until(LocalDate.parse(dayFinish), ChronoUnit.DAYS)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tariffs_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
           R.id.delivery_menu -> viewModel.sorted.value = TariffSortedTypes.DELIVERY
           R.id.price_menu -> viewModel.sorted.value = TariffSortedTypes.PRICE
        }
        return true
    }

    inner class TariffDataAdapter(val tariffs: List<Data>) :
        RecyclerView.Adapter<TariffDataAdapter.TariffViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TariffViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tariff_data_item, parent, false)
        )

        override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
            holder.onBind(tariffs[position])
        }

        override fun getItemCount() = tariffs.size

        inner class TariffViewHolder(item: View) : RecyclerView.ViewHolder(item) {
            val tariffItem = TariffDataItemBinding.bind(item)
            fun onBind(tariff: Data?) {
                tariffItem.tariff = tariff
                    Picasso.with(requireContext()).load(tariff!!.logo).into(tariffItem.imageView)
                    Log.d("URL", tariff!!.logo.toString())
                tariffItem.tariffDataContainer.setOnClickListener {
                    findNavController().navigate(R.id.action_tariffsListFragment_to_orderFragment)
                }
            }
        }
    }

}