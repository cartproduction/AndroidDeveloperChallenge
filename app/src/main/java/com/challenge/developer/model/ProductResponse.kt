package com.challenge.developer.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductResponse {

    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("month")
    @Expose
    var month: String? = null
    @SerializedName("marketName")
    @Expose
    var marketName: String? = null
    @SerializedName("orderName")
    @Expose
    var orderName: String? = null
    @SerializedName("productPrice")
    @Expose
    var productPrice: Double? = null
    @SerializedName("productState")
    @Expose
    var productState: String? = null
    @SerializedName("productDetail")
    @Expose
    var productDetail: ProductDetail? = null

}