package com.challenge.developer

import android.app.Application

class Consants : Application() {

    companion object {

        val baseUrl = "http://kariyertechchallenge.mockable.io/"
        var authenticationToken: String? = null
    }

}