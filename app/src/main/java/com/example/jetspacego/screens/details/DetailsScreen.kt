package com.example.jetspacego.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetspacego.model.launches.Agencies
import com.example.jetspacego.model.launches.Results

@Composable
fun DetailsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Agencies", "More Info")

    val result : Results? = navController.previousBackStackEntry?.savedStateHandle?.get<Results>("msn")

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when (selectedTab) {
            0 -> result?.let {
                OverviewTab(it, navController = navController)
            }
            1 -> result?.mission?.agencies?.let { AgenciesTab(it) }
            2 -> result?.let { MoreInfoTab(it) }
        }
    }
}

@Composable
fun OverviewTab(result: Results, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        result.name?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
        Spacer(modifier = Modifier.height(6.dp))
        result.mission?.description?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Type", style = TextStyle(color = Color.Gray, fontSize = 14.sp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${result.launchServiceProvider?.type?.name}")
        Spacer(modifier = Modifier.weight(1f))
        Box(contentAlignment = Alignment.Center) {
            OutlinedButton(onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("added msn", result)
                navController.navigate("listBook")
            }, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Text(text = "Book a Space Flight", fontSize = 16.sp)
            }
        }

    }
}

@Composable
fun AgenciesTab(agencies: ArrayList<Agencies>) {
    LazyColumn {
        items(agencies) { agency ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    agency.name?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
                    Text(text = "${agency.abbrev}", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Type: ${agency.type?.name}")
                }
            }
        }
    }
}


@Composable
fun MoreInfoTab(result: Results) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        result.mission?.agencies?.get(0)?.wikiUrl?.let { url ->
            ClickableLink(text = "Wikipedia", url = url)
        }

        result.mission?.agencies?.get(0)?.infoUrl?.let { url ->
            ClickableLink(text = "Official page", url = url)
        }
    }
}

@Composable
fun ClickableLink(text: String, url: String) {
    val context = LocalContext.current

    Text(
        text = buildAnnotatedString {
            append(text)
        },
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .padding(4.dp),
        textDecoration = TextDecoration.Underline,
        color = Color(0xFF3F51B5)
    )
}




