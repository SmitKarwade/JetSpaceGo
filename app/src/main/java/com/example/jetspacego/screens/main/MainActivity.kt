package com.example.jetspacego.screens.main

import TicketScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.jetspacego.R
import com.example.jetspacego.model.OrgView
import com.example.jetspacego.screens.details.DetailsScreen
import com.example.jetspacego.screens.listbook.ListBook
import com.example.jetspacego.screens.profile.ProfileScreen
import com.example.jetspacego.ui.theme.JetSpaceGoTheme
import com.example.jetspacego.ui.theme.jetDark
import com.example.jetspacego.ui.theme.jetGreenish
import com.example.jetspacego.ui.theme.jetSteelBlue
import com.example.jetspacego.ui.theme.jetTeal
import com.example.jetspacego.widgets.BottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SystemBarColorHandler()
            MainUI()
        }
    }
}

private val organizations = listOf(
    OrgView("Arianespace", R.drawable.arianespace),
    OrgView("Astra", R.drawable.astra),
    OrgView("Blue Origin", R.drawable.blueorigin),
    OrgView("CAS Space", R.drawable.cas),
    OrgView("China Aerospace Science and Technology Corporation", R.drawable.chinaas),
    OrgView("Firefly Aerospace", R.drawable.firefly),
    OrgView("Galactic Energy", R.drawable.galacticenergy),
    OrgView("Indian Space Research Organization", R.drawable.isro),
    OrgView("INNOSPACE", R.drawable.innospace),
    OrgView("International Launch Services", R.drawable.ils),
    OrgView("Iranian Space Agency", R.drawable.isa),
    OrgView("i-Space", R.drawable.ispace),
    OrgView("Japan Aerospace Exploration Agency", R.drawable.jaxa),
    OrgView("Mitsubishi Heavy Industries", R.drawable.mitsubishi),
    OrgView("National Aeronautics and Space Administration", R.drawable.nasa),
    OrgView("Northrop Grumman", R.drawable.northrop),
    OrgView("Orienspace", R.drawable.orienspace),
    OrgView("Relativity Space", R.drawable.relativity),
    OrgView("Rocket Lab", R.drawable.rocketlab),
    OrgView("Russian Federal Space Agency (ROSCOSMOS)", R.drawable.roscosmos),
    OrgView("Skyroot Aerospace", R.drawable.skyroot),
    OrgView("SpaceX", R.drawable.spacex),
    OrgView("United Launch Alliance", R.drawable.ula),
    OrgView("Virgin Galactic", R.drawable.virgingalactic)
)

@Preview(showBackground = true)
@Composable
fun MainUI(){
    val navController = rememberNavController()

    JetSpaceGoTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomBar(navController)
            }) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                NavHost(navController = navController, startDestination = "main") {
                    composable("main"){
                        NestedMissionList(organizations, navController = navController)
                    }

                    composable("list"){
                        MissionList(navController = navController)
                    }

                    composable("profile"){
                        ProfileScreen(navController = navController)
                    }

                    composable("ticket"){
                        TicketScreen(navController = navController)
                    }

                    composable("details"){
                        DetailsScreen(navController = navController)
                    }

                    composable("listBook"){
                        ListBook(navController = navController)
                    }
                }
            }

        }
    }
}

@Composable
fun NestedMissionList(list: List<OrgView>, navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    val filteredList = remember(searchText) {
        list.filter {
            it.name?.contains(searchText, ignoreCase = true) == true
        }
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxSize(),
                label = { Text("Search Organization") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    focusedContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredList) { item ->
                MissionCard(item) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("msn_name", item.name)
                    navController.navigate("list")
                }
            }
        }
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MissionCard(org: OrgView, onItemClick : () -> Unit) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = {onItemClick()},
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column() {
            org.image?.let {
                org.name?.let { it1 ->
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = org.image),
                        contentDescription = org.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            org.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}


@SuppressLint("ContextCastToActivity")
@Composable
fun SystemBarColorHandler() {
    val statusBarLight = Color.Green
    val statusBarDark = Color.Blue
    val navigationBarLight = Color.Green
    val navigationBarDark = Color.Blue
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    statusBarLight.toArgb(),
                    statusBarDark.toArgb()
                )
            } else {
                SystemBarStyle.dark(statusBarDark.toArgb())
            },
            navigationBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    navigationBarLight.toArgb(),
                    navigationBarDark.toArgb()
                )
            } else {
                SystemBarStyle.dark(navigationBarDark.toArgb())
            }
        )
        onDispose {}
    }
}



