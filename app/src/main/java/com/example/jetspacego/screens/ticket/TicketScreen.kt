package com.example.jetspacego.screens.ticket

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jetspacego.model.Result

@Composable
    fun TicketScreen(navController: NavController){

        val added = navController.previousBackStackEntry?.savedStateHandle?.get<Result>("added msn")
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "Ticket ${added?.name}")
        }
}