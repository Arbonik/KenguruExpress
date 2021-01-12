package com.arbonik.myapplication.ui.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.model.Track

class TracksViewModel : ViewModel() {

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