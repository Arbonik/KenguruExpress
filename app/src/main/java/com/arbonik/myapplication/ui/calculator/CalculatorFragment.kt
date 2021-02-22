package com.arbonik.myapplication.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.ContainerCalculateCargoSizeBinding
import com.arbonik.myapplication.model.UIProduct
import com.arbonik.myapplication.model.cargo.CargoType
import com.arbonik.myapplication.ui.views.AddressInputView
import com.google.android.material.snackbar.Snackbar

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
            if (viewModel.cargoSetSettings())
                viewModel.createProduct()
            else
//                    Snackbar.make(requireContext(), root,"заполните все поля!", Snackbar.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_navigation_calculator_to_tariffsListFragment)
        }
        viewModel.currentCargoes.observe(viewLifecycleOwner) {
            if (it != null)
                viewModel.downloadTariffs(it)
        }

        //layout с настройками груза
        var cargoView = root.findViewById<LinearLayout>(R.id.calculate_container)
        containerCalculateCargoSizeBinding = ContainerCalculateCargoSizeBinding.bind(cargoView)
        var uiProduct = UIProduct()
        containerCalculateCargoSizeBinding.uIproduct = uiProduct
        containerCalculateCargoSizeBinding.uIproduct?.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.cargoSetSettings(uiProduct)

            }
        })
        containerCalculateCargoSizeBinding.typeParcel.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (checkedId) {
                R.id.button_document -> {
                    containerCalculateCargoSizeBinding.cargoParamLayout.visibility = View.GONE
                    viewModel.typeProductLiveData.value = CargoType.DOCUMENT
                }
                R.id.button_cargo -> {
                    containerCalculateCargoSizeBinding.cargoParamLayout.visibility = View.VISIBLE
                    viewModel.typeProductLiveData.value = CargoType.CARGO
                }
            }
        }

        return root
    }


}