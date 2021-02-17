package com.arbonik.myapplication.ui.tariffs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.KenguruApplication

class TariffsListViewModel : ViewModel() {

    val sorted : MutableLiveData<TariffSortedTypes> = MutableLiveData(TariffSortedTypes.DEFAULT)

    val dao = KenguruApplication.departuresRepository.tariffDao.getAll()

}