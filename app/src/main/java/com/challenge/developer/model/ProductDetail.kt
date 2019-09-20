package com.challenge.developer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductDetail {

    @SerializedName("orderDetail")
    @Expose
    var orderDetail: String? = null
    @SerializedName("summaryPrice")
    @Expose
    var summaryPrice: Double? = null

}