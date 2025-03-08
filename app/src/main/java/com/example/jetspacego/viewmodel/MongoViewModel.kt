package com.example.jetspacego.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetspacego.model.BookingDetails
import com.example.jetspacego.model.paymentmodel.OrderResponse
import com.example.jetspacego.mongorepo.MongoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MongoViewModel @Inject constructor(private val mongoRepo: MongoRepo) : ViewModel() {

    val mutableOrder = MutableStateFlow<OrderResponse>(OrderResponse("", "", "", ""))
    val stateOrder: StateFlow<OrderResponse> = mutableOrder

    fun addDetails(details : BookingDetails){
        viewModelScope.launch {
            mongoRepo.addUser(details)
        }
    }

    fun startPayment(amt: Double){
        viewModelScope.launch {
            mutableOrder.value = mongoRepo.doPayment(amt)
        }
    }

    fun startVerification(paymentId: String, orderId: String, signature: String){
        viewModelScope.launch {
            mongoRepo.verify(paymentId, orderId, signature)
        }
    }
}