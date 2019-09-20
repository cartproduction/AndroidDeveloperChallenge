package com.raventech.fujibas.interfaces

import com.challenge.developer.model.ProductResponse
import com.challenge.developer.model.User
import io.reactivex.Observable
import retrofit2.http.*

interface NetworkAPI {

    @POST("users/login")
    fun login(@Body model: User): Observable<User>

    @GET
    fun getProducts(): Observable<ProductResponse>
}