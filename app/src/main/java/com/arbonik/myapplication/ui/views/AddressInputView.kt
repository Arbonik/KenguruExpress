package com.arbonik.myapplication.ui.views

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import com.arbonik.myapplication.network.data.geography.LocalityItem
import com.arbonik.myapplication.ui.calculator.LocalityRepository
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AddressInputView(
    context: Context, attributeSet: AttributeSet
) : MaterialAutoCompleteTextView(context, attributeSet){

    var currentLocalityItem :LocalityItem? = null

    fun addViewModel(lovalityLiveData: LocalityRepository,
                     viewLifecycleOwner: LifecycleOwner ){
        addTextChangedListener(onTextChanged = { s, start, before, count ->
            lovalityLiveData.localitySearch(s.toString())
        })
        lovalityLiveData.address.observe(viewLifecycleOwner, { locality ->
            setAdapter(
                    ArrayAdapter(context,
                            R.layout.simple_list_item_1,
                            Array(locality.size)
                            {
                                val loc = locality[it]
                                loc.full_title ?: ""
                            })
            )
        })

        setOnItemClickListener { parent, view, position, id ->
            currentLocalityItem = lovalityLiveData.address.value?.get(position)
        }
    }
}