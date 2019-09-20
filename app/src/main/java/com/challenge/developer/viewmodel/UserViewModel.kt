package com.challenge.developer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.challenge.developer.model.User

class UserViewModel : ViewModel() {
    val user: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>()
    }
}
