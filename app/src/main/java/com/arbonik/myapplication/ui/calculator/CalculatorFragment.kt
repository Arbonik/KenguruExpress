package com.arbonik.myapplication.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.TariffItemBinding
import com.arbonik.myapplication.network.ws.WebSocketDepartures
import com.arbonik.myapplication.product.Calculator
import com.arbonik.myapplication.product.Tariff
import com.arbonik.myapplication.ui.views.AddressInputView
import com.google.android.material.button.MaterialButtonToggleGroup

class CalculatorFragment : Fragment() {

    private lateinit var calculatorViewModel: CalculatorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        calculatorViewModel =
                ViewModelProvider(this)
                    .get(CalculatorViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        val cargoSettingContainer = root.findViewById<FrameLayout>(R.id.calculate_container)


        val addressInputFrom = root.findViewById<AddressInputView>(R.id.address_from_text_input)
        addressInputFrom.addViewModel(calculatorViewModel.localityFrom,
                viewLifecycleOwner)

        val addressInputTo = root.findViewById<AddressInputView>(R.id.address_to_text_input)
        addressInputTo.addViewModel(calculatorViewModel.localityTo,
                viewLifecycleOwner)

        val tariffsRecyclerView = RecyclerView(requireActivity()).apply {
            adapter = TariffAdapter(calculatorViewModel.tariffs)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        calculatorViewModel.tariffs.observe(viewLifecycleOwner){
            tariffsRecyclerView.adapter?.notifyDataSetChanged()
        }


//        cargoSettingContainer.addView(tariffsRecyclerView)

        val calculateButton = root.findViewById<Button>(R.id.button_calculate)
            calculateButton.setOnClickListener {
                val calculator = Calculator()
                calculator.fromIdSity = addressInputFrom.currentLocalityItem?.id?.toInt() ?: 1
                calculator.toIdSity = addressInputTo.currentLocalityItem?.id?.toInt() ?: 2
                calculator.calculate()
//                val webSocketDepartures = WebSocketDepartures("3")

            }

        val typeParcelButton = root.findViewById<MaterialButtonToggleGroup>(R.id.type_parcel)
        typeParcelButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (checkedId) {
                R.id.button_document -> {
                    cargoSettingContainer.removeAllViews()
                }
                R.id.button_cargo -> {
                    cargoSettingContainer.removeAllViews()
                    var view = inflater.inflate(R.layout.container_calculate_cargo_size, cargoSettingContainer, false)
                    cargoSettingContainer.addView(view)
                }
            }
        }

        return root
    }


    inner class TariffAdapter(val tariffs : LiveData<MutableList<Tariff>>) : RecyclerView.Adapter<TariffAdapter.TariffViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                = TariffViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tariff_item, parent, false))

        override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
            holder.onBind(tariffs.value?.get(position))
        }

        override fun getItemCount() = tariffs.value?.let { it.size } ?: 0

        inner class TariffViewHolder(item : View) : RecyclerView.ViewHolder(item){
            val tariffItem = TariffItemBinding.bind(item)
            fun onBind(tariff: Tariff?) {
                tariffItem.tariff = tariff
                tariffItem.tariffOrder.setOnClickListener {
                    activity?.findNavController(R.id.nav_host_fragment)
                        ?.navigate(R.id.action_navigation_calculator_to_orderFragment)
                }
            }
        }
    }
}