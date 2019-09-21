package com.challenge.developer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.developer.model.Product

class OrdersViewModel : ViewModel() {
    val orders: MutableLiveData<List<Product>?> by lazy {
        MutableLiveData<List<Product>?>()
    }
}
