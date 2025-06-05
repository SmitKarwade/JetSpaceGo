package com.example.jetspacego.request

import com.example.jetspacego.model.launches.BookingDetails
import com.example.jetspacego.model.paymentmodel.OrderResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MongoService {

    @POST("form/add")
    suspend fun createdUser(@Body user : BookingDetails)

    @POST("api/payment/create-order")
    suspend fun createOrder(@Query("amount") amount: Double): OrderResponse

    @POST("api/payment/verify-payment")
    suspend fun verifyPayment(@Query("paymentId") paymentId: String,
                              @Query("orderId") orderId: String,
                              @Query("signature") signature: String): String


}