package com.challenge.developer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {

    @SerializedName("userName")
    @Expose
    var userName: String? = "kariyer"
    @SerializedName("userPassword")
    @Expose
    var userPassword: String? = "2019ADev"

}