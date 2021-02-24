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

class CalculatorFragment : Fragment() {

    private val viewModel by viewModels<CalculatorViewModel>()

    lateinit var containerCalculateCargoSizeBinding: ContainerCalculateCargoSizeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        //layout с настройками груза
        var cargoView = root.findViewById<LinearLayout>(R.id.calculate_container)
        containerCalculateCargoSizeBinding = ContainerCalculateCargoSizeBinding.bind(cargoView)

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
            //Отправление настроенно правильно
            if (viewModel.isLocalitySetup()) {
                // груз настроен правильно
                if (viewModel.cargoSetSettings()) {
                    viewModel.createCargo()
                    findNavController().navigate(R.id.action_navigation_calculator_to_tariffsListFragment)
                } else

                if (containerCalculateCargoSizeBinding.weightCargoTextInput.text.isNullOrEmpty()) {
                    containerCalculateCargoSizeBinding.weightCargoTextInput.error = "Заполните поле"
                } else{
                    if (containerCalculateCargoSizeBinding.lengthInput.text.isNullOrEmpty())
                        containerCalculateCargoSizeBinding.lengthInput.error = "Заполните поле"
                    if (containerCalculateCargoSizeBinding.heightInput.text.isNullOrEmpty())
                        containerCalculateCargoSizeBinding.heightInput.error = "Заполните поле"
                    if (containerCalculateCargoSizeBinding.widthInput.text.isNullOrEmpty())
                        containerCalculateCargoSizeBinding.widthInput.error = "Заполните поле"
                }

            }
            else {
                if (addressInputFrom.text.isNullOrEmpty())
                    addressInputFrom.error = "Заполните поле"
                if (addressInputTo.text.isNullOrEmpty())
                    addressInputTo.error = "Заполните поле"
            }
        }

        //запрос на загрузку тарифов
        viewModel.currentCargoes.observe(viewLifecycleOwner) {
            if (it != null)
                viewModel.downloadTariffs(it)
        }

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