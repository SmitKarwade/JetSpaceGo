package com.example.jetspacego.mongorepo

import com.example.jetspacego.model.BookingDetails
import com.example.jetspacego.request.MongoService
import javax.inject.Inject

class MongoRepo @Inject constructor(val mongoService: MongoService){
    suspend fun addUser(user: BookingDetails){
        mongoService.createdUser(user)
    }

    suspend fun doPayment(amt: Double) = mongoService.createOrder(amt)

    suspend fun verify(paymentId: String, orderId: String, signature: String) = mongoService.verifyPayment(paymentId, orderId, signature)
}