package com.arbonik.myapplication.ui.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arbonik.myapplication.R
import com.arbonik.myapplication.geography.Locality
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class CalculatorFragment : Fragment() {

    private lateinit var calculatorViewModel: CalculatorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        calculatorViewModel =
                ViewModelProvider(this).get(CalculatorViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        val cargoSettingContainer = root.findViewById<FrameLayout>(R.id.calculate_container)

        val typeParcelButton = root.findViewById<MaterialButtonToggleGroup>(R.id.type_parcel)

        val addressInputFrom = root.findViewById<MaterialAutoCompleteTextView>(R.id.address_from_text_input)
        AddressInputView(addressInputFrom,
                calculatorViewModel.localityFrom)

        val addressInputTo = root.findViewById<MaterialAutoCompleteTextView>(R.id.address_to_text_input)
        AddressInputView(addressInputTo,
                calculatorViewModel.localityTo)


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
    // связывает editText с запросами в сеть
    inner class AddressInputView(val addressEditText: MaterialAutoCompleteTextView,
                            val lovalityLiveData: LocalityModel) {
        init {
            addressEditText.addTextChangedListener(onTextChanged = { s, start, before, count ->
                lovalityLiveData.localitySearch(s.toString())
            })
            lovalityLiveData.address.observe(viewLifecycleOwner, { locality ->
                addressEditText.setAdapter(ArrayAdapter<String>(requireContext(),
                        android.R.layout.simple_list_item_1,
                        Array(locality.size)
                        {
                            val loc = locality[it]
                            loc.full_title ?: ""
                        }))
            })
        }
    }

}