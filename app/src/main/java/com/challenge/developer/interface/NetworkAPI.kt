package com.raventech.fujibas.interfaces

import com.challenge.developer.Consants.Companion.baseUrl
import com.challenge.developer.model.Product
import com.challenge.developer.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface NetworkAPI {

    @POST("users/login")
    fun login(@Body model: User): Observable<User>

    @GET(baseUrl)
    fun getProducts(): Observable<List<Product>>
}