package com.arbonik.myapplication.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.ContainerCalculateCargoSizeBinding
import com.arbonik.myapplication.model.UIProduct
import com.arbonik.myapplication.network.models.DeliveryType
import com.arbonik.myapplication.product.Tariff
import com.arbonik.myapplication.ui.views.AddressInputView
import com.google.android.material.button.MaterialButtonToggleGroup

class CalculatorFragment : Fragment() {

    private val viewModel by viewModels<CalculatorViewModel>()

    lateinit var containerCalculateCargoSizeBinding: ContainerCalculateCargoSizeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        val addressInputFrom = root.findViewById<AddressInputView>(R.id.address_from_text_input)
        addressInputFrom.addObservable(viewModel, viewLifecycleOwner)
        addressInputFrom.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.setupStartLocality(addressInputFrom.adapter.getItem(position) as String)
            }

        val addressInputTo = root.findViewById<AddressInputView>(R.id.address_to_text_input)
        addressInputTo.addObservable(viewModel, viewLifecycleOwner)
        addressInputTo.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                viewModel.setupFinishLocality(addressInputTo.adapter.getItem(position) as String)
            }

        val calculateButton = root.findViewById<Button>(R.id.button_calculate)
        calculateButton.setOnClickListener {
//            viewModel.downloadTariffs()
            viewModel.createProduct()
        }
        viewModel.tarrifs.observe(viewLifecycleOwner) {

        }

        //layout с настройками груза
        var cargoView = root.findViewById<LinearLayout>(R.id.calculate_container)
        containerCalculateCargoSizeBinding = ContainerCalculateCargoSizeBinding.bind(cargoView)
        var uiProduct = UIProduct()
        containerCalculateCargoSizeBinding.uIproduct = uiProduct
        containerCalculateCargoSizeBinding.uIproduct?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.cargoSettings(uiProduct)
            }
        })
        containerCalculateCargoSizeBinding.typeParcel.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (checkedId) {
                R.id.button_document -> {
                    containerCalculateCargoSizeBinding.cargoParamLayout.visibility = View.GONE
                    viewModel.typeProductLiveData.value = DeliveryType.DOCUMENT
                }
                R.id.button_cargo -> {
                    containerCalculateCargoSizeBinding.cargoParamLayout.visibility = View.VISIBLE
                    viewModel.typeProductLiveData.value = DeliveryType.CARGO
                }
            }
        }

        return root
    }

    inner class TariffAdapter(val tariffs: LiveData<MutableList<Tariff>>) :
        RecyclerView.Adapter<TariffAdapter.TariffViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TariffViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tariff_item, parent, false)
        )

        override fun onBindViewHolder(holder: TariffViewHolder, position: Int) {
//            holder.onBind(tariffs.value?.get(position))
        }

        override fun getItemCount() = tariffs.value?.size ?: 0

        inner class TariffViewHolder(item: View) : RecyclerView.ViewHolder(item) {
//            val tariffItem = TariffItemBinding.bind(item)
//            fun onBind(tariff: Tariff?) {
//                tariffItem.tariff = tariff
//                tariffItem.tariffOrder.setOnClickListener {
//                    activity?.findNavController(R.id.nav_host_fragment)
//                        ?.navigate(R.id.action_navigation_calculator_to_orderFragment)
//                }
//            }
        }
    }
}