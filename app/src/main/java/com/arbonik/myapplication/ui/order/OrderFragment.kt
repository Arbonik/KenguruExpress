package com.arbonik.myapplication.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.ProfileOrderBinding
import com.arbonik.myapplication.profile.OrderData
import com.arbonik.myapplication.ui.calculator.CalculatorViewModel
import com.arbonik.myapplication.ui.views.AddressInputView
import com.arbonik.myapplication.ui.views.ProfileOrderLayout

class OrderFragment : Fragment() {

    lateinit var orderViewModel : OrderViewModel
    lateinit var calculatorViewModel : CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        calculatorViewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_order, container, false)

        val addressInputFrom = root.findViewById<AddressInputView>(R.id.address_from_text_input)
        addressInputFrom.addViewModel(calculatorViewModel.localityFrom,
                viewLifecycleOwner)

        val addressInputTo = root.findViewById<AddressInputView>(R.id.address_to_text_input)
        addressInputTo.addViewModel( calculatorViewModel.localityTo,
                viewLifecycleOwner)

        val recepierContainer = root.findViewById<ProfileOrderLayout>(R.id.recepier_include)
        val senderContainer = root.findViewById<ProfileOrderLayout>(R.id.sender_include)

        val button = root.findViewById<Button>(R.id.button)

        val recepierBinding = ProfileOrderBinding.bind(recepierContainer)
        val senderBinding = ProfileOrderBinding.bind(senderContainer)

        recepierBinding.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                orderViewModel.updateOrderData(recepierBinding.orderData ?: OrderData.instance())
                Log.d("RE", recepierBinding.orderData.toString())
            }
        })

        button.setOnClickListener {
            recepierBinding.orderData = senderBinding.orderData
        }


//        orderViewModel.order.observe(senderContainer.binding?.lifecycleOwner!!){
//
//        }

//        OrderData("wqe", "wqe", "qwe", "wqe", "wqeq", "qwe")

//        recepierData?.orderData = OrderData("wqe", "wqe", "qwe", "wqe", "wqeq", "qwe")
//        recepierContainer.findViewById<TextInputEditText>(R.id.name).addTextChangedListener(onTextChanged = {
//            text, start, before, count ->
//            recepierData.orderData = recepierData.orderData.copy(company = text.toString())
//        })

//        recepier_container. .commentary.addTextChangedListener {
//            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
////            orderViewModel.order
//        }

        return root
    }

}