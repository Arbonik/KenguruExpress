package com.arbonik.myapplication.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TracksViewModel : ViewModel() {

    val mutableLiveData = MutableLiveData<List<FullRequest>>()

    fun downloadRequests(){
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveData.postValue(KenguruApplication.productRepository.getAllRequests())
        }
    }

    fun addTrack(track: Track){
        _tracksList.postValue(tracksList.value?.apply {
            add(track)
        })
    }

    private val _tracksList = MutableLiveData<MutableList<Track>>().apply {
        value = mutableListOf()
    }


    val tracksList :LiveData<MutableList<Track>> = _tracksList


}