package com.challenge.developer

import android.app.Application
import java.util.ArrayList

class Consants : Application() {

    companion object {

        const val baseUrl = "http://kariyertechchallenge.mockable.io/"
        var authenticationToken: String? = null
    }

}