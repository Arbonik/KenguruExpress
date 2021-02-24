package com.arbonik.myapplication.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.ContainerCalculateCargoSizeBinding
import com.arbonik.myapplication.databinding.TariffDataItemBinding
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.model.UIProduct
import com.arbonik.myapplication.ui.tariffs.ARG_TARIFF_ID_KEY
import com.arbonik.myapplication.ui.views.AddressInputView
import com.squareup.picasso.Picasso

class OrderFragment : Fragment() {

    lateinit var viewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order, container, false)

        root.findViewById<AddressInputView>(R.id.address_from_text_input).let {
            val localityPair =
                KenguruApplication.localityRepository.localityResponseToLocalityPair()
            it.setText(localityPair.fromCityTitle)
            it.isEnabled = false
        }
        root.findViewById<AddressInputView>(R.id.address_to_text_input).let {
            val localityPair =
                KenguruApplication.localityRepository.localityResponseToLocalityPair()
            it.setText(localityPair.toCityTitle)
            it.isEnabled = false
        }

        val calculateCargoSizeBinding =
            ContainerCalculateCargoSizeBinding.bind(root.findViewById(R.id.cargo_order_container))
        val value = KenguruApplication.productRepository.currentProductResponse

        when (KenguruApplication.productRepository.currentProductResponse?.delivery_type) {
            "doc" -> {
                calculateCargoSizeBinding.cargoParamLayout.visibility = View.GONE
            }
            "cargo" -> {
                calculateCargoSizeBinding.cargoParamLayout.visibility = View.VISIBLE
            }
        }

        calculateCargoSizeBinding.uIproduct = UIProduct().apply {
            weight = value?.weight.toString()
            height = value?.height.toString()
            width = value?.width.toString()
            length = value?.length.toString()
        }

        calculateCargoSizeBinding.recentCargoButton.visibility = View.GONE
        calculateCargoSizeBinding.heightInput.isEnabled = false
        calculateCargoSizeBinding.typeParcel.isEnabled = false
        calculateCargoSizeBinding.widthInput.isEnabled = false
        calculateCargoSizeBinding.typeParcelContainer.isEnabled = false
        calculateCargoSizeBinding.weightCargoTextInput.isEnabled = false
        calculateCargoSizeBinding.lengthInput.isEnabled = false
        calculateCargoSizeBinding.buttonCargo.isEnabled = false
        calculateCargoSizeBinding.buttonDocument.isEnabled = false

        val tariffDataItemBinding =
            TariffDataItemBinding.bind(root.findViewById(R.id.tariffDataContainer))
        val tariffId = arguments?.getString(ARG_TARIFF_ID_KEY)

        viewModel.loadTariff(tariffId!!).observe(viewLifecycleOwner) {
            tariffDataItemBinding.tariff = it
            Picasso.with(requireContext()).load(it.logo).into(tariffDataItemBinding.imageView)
            tariffDataItemBinding.tariffDataContainer.isEnabled = false
        }

        //NEED TO DO
        root.findViewById<Button>(R.id.accept_button).setOnClickListener {
            viewModel.addRequestToDatabase(
                FullRequest(
                    KenguruApplication.localityRepository.localityResponseToLocalityPair(),
                    KenguruApplication.productRepository.currentProductResponse,
                    tariffDataItemBinding.tariff
                )
            )
        }

        return root
    }
}