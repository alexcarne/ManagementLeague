package com.example.managementleague.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewmodel:ViewModel() {
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address
    fun updateAddress(address: String) {
        _address.value = address
    }
}