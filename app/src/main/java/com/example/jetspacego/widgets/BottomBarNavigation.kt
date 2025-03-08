package com.example.jetspacego.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.PopUpToBuilder
import com.example.jetspacego.R

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("Home", "Ticket", "Profile")
    val routes = listOf("main", "ticket", "profile")

    var currentRoute = navController.currentDestination?.route

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEachIndexed { index, s ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = {
                    if (currentRoute != routes[index]) {
                        navController.navigate(routes[index]) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    selectedItem = index
                          },
                icon = {
                    when (index) {
                        0 -> Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.rocket),
                            contentDescription = "Home"
                        )

                        1 -> Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ticket),
                            contentDescription = "Ticket"
                        )

                        2 -> Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.common_user),
                            contentDescription = "Profile"
                        )
                    }
                },
                label = {
                    if (index == selectedItem){
                        Text(text = items[index])
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    indicatorColor = Color.LightGray,
                    unselectedIconColor = Color.Gray
                )
            )
        }

    }


}
