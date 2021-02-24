package com.arbonik.myapplication.ui.views

import android.R
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import com.arbonik.myapplication.network.models.geography.LocalityResponse
import com.arbonik.myapplication.network.models.geography.LocalityItem
import com.arbonik.myapplication.ui.calculator.CalculatorViewModel
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AddressInputView(
    context: Context, attributeSet: AttributeSet
) : androidx.appcompat.widget.AppCompatAutoCompleteTextView(context, attributeSet){
    //Для отображения списка возможных пунктов отправления
    fun addObservable(calculatorViewModel : CalculatorViewModel, viewLifecycleOwner : LifecycleOwner){

        val obsAdressInputFrom = { localityResponse : LocalityResponse ->
            this.setAdapter(arrayAdapter(localityResponse))
        }
        // настройка прослушивания
        this.addTextChangedListener(
            onTextChanged = { s, start, before, count ->
                if (s != null)
                    if (s.isNotBlank() && s.isNotEmpty())
                        calculatorViewModel.localitySearch(s.toString())
            },
            afterTextChanged = { text -> calculatorViewModel.localityResponse.removeObserver(obsAdressInputFrom)},

            beforeTextChanged = {text, start, count, after ->
                calculatorViewModel.localityResponse.observe(
                    viewLifecycleOwner, obsAdressInputFrom)
            }
        )
    }

    // настройка адаптера для отображения списка городов
    private fun arrayAdapter(localityResponse: LocalityResponse) =
        ArrayAdapter(context,
            R.layout.simple_list_item_1,
            Array(localityResponse.size)
            {
                val loc = localityResponse[it]
                loc.full_title ?: ""
            })

}