package com.arbonik.myapplication.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.network.Resource
import com.arbonik.myapplication.network.models.login.ProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragmentViewModel : ViewModel() {
    private val loginRepository = KenguruApplication.loginRepository

    var profileData = MutableLiveData<ProfileData>()

    val dataStatus = MutableLiveData<Resource<String>>()

    fun loadProfileData(){
        viewModelScope.launch(Dispatchers.IO) {
            if (loginRepository.loadProfileData())
                profileData.postValue(loginRepository.userData)
        }
    }

    fun updateProfileData(profileData: ProfileData){
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.updateProfileData(profileData)
            if (result.isSuccessful)
                dataStatus.postValue(Resource.Success("Данные успешно обновлены!"))
        }
    }
}