package com.example.jetspacego.screens.main

import TicketScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetspacego.model.MainViewORG
import com.example.jetspacego.screens.details.DetailsScreen
import com.example.jetspacego.screens.listbook.ListBook
import com.example.jetspacego.screens.profile.ProfileScreen
import com.example.jetspacego.ui.theme.JetSpaceGoTheme
import com.example.jetspacego.widgets.BottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MainUI()
        }
    }
}

private val organizations = listOf(
    MainViewORG("Arianespace", "https://i.pinimg.com/736x/31/08/9c/31089ce9754297181a92d331ca7d6374.jpg"),
    MainViewORG("Astra", "https://caribbeannewsglobal.com/wp-content/uploads/2024/03/Astra_Square_Logo_Black_28White_Background29-1.jpg"),
    MainViewORG("Bellatrix Aerospace", "https://i.pinimg.com/736x/f5/1b/9e/f51b9ea6f1bfd1d79957368d9f429c21.jpg"),
    MainViewORG("Blue Origin", "https://logolook.net/wp-content/uploads/2021/01/Blue-Origin-Emblem.png"),
    MainViewORG("CAS Space", "https://pbs.twimg.com/profile_images/1627785543063937024/WIi85gLa_400x400.jpg"),
    MainViewORG("China Aerospace Science and Technology Corporation", "https://i.pinimg.com/736x/ca/ba/bb/cababba779f0a47a8f8735bff2008962.jpg"),
    MainViewORG("Firefly Aerospace", "https://maxpolyakov.com/wp-content/uploads/2021/01/firefly_pict_desc.jpg"),
    MainViewORG("Galactic Energy", "https://cdn.sanity.io/images/2vtv415l/production/61fd4b218ebad730d77502be800857d54873b994-1179x1180.png"),
    MainViewORG("Indian Space Research Organization", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Indian_Space_Research_Organisation_Logo.svg/1200px-Indian_Space_Research_Organisation_Logo.svg.png"),
    MainViewORG("INNOSPACE", "https://sky-brokers.com/wp-content/uploads/2022/06/INNOSpace-logo.png"),
    MainViewORG("International Launch Services", "https://download.logo.wine/logo/International_Launch_Services/International_Launch_Services-Logo.wine.png"),
    MainViewORG("Iranian Space Agency", "https://media.tehrantimes.com/d/t/2024/08/23/4/5132675.jpg?ts=1724420772125"),
    MainViewORG("i-Space", "https://upload.wikimedia.org/wikipedia/en/7/77/ISpace_Logo.png"),
    MainViewORG("Japan Aerospace Exploration Agency", "https://i.pinimg.com/736x/5d/42/c5/5d42c5abb91ff3742c3ddf7c9038b53c.jpg"),
    MainViewORG("LandSpace", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP719XLsLfYzejcSMeb6nGNOKxe0DuBNc69pVmRonHsxeDQkonk6eYMz6OSxMhKkBN8sE&usqp=CAU"),
    MainViewORG("Mitsubishi Heavy Industries", "https://pbs.twimg.com/media/CeItBylWoAAwvBw.jpg:large"),
    MainViewORG("National Aeronautics and Space Administration", "https://i.pinimg.com/736x/01/a4/00/01a4004d77fcf937470e1a0edec0ed73.jpg"),
    MainViewORG("Northrop Grumman", "https://brandlogos.net/wp-content/uploads/2022/07/northrop_grumman-logo_brandlogos.net_mqy0p.png"),
    MainViewORG("OneSpace", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXn-lIULYKnER02vuuPjRYOxRIUBQH2VRON-voJzMhOyY2_PP9M9kYISQ4TOG9wa1Qvw&usqp=CAU"),
    MainViewORG("Orienspace", "https://i.pinimg.com/736x/df/a0/35/dfa035fedb26bef5fd13bc4a3cd9c661.jpg"),
    MainViewORG("Relativity Space", "https://www.factoriesinspace.com/img/thumb2/thumbnail_Relativity_White_RGB.png"),
    MainViewORG("Rocket Lab", "https://i.pinimg.com/736x/8b/3d/c3/8b3dc33706aec11737428de5023732a6.jpg"),
    MainViewORG("Russian Federal Space Agency (ROSCOSMOS)", "https://i.pinimg.com/736x/26/b3/6f/26b36f4f34f9ea9f6de5055aa0c26805.jpg"),
    MainViewORG("Sea Launch", "https://danielmarin.naukas.com/files/2016/01/Sea-Launch-Logo-with-Blue-Lettering.jpg"),
    MainViewORG("Skyroot Aerospace", "https://i.pinimg.com/736x/0a/e9/fa/0ae9fa713e86dcd2a1f8be264165d8f9.jpg"),
    MainViewORG("Space One", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxmCAxsfGlecgrA5iKXuwcsLPlgCqM7WNaJA&s"),
    MainViewORG("SpaceX", "https://i.pinimg.com/736x/d9/7c/5b/d97c5b4f87098d7c47893294b1336e87.jpg"),
    MainViewORG("United Launch Alliance", "https://media2.spaceref.com/wp-content/uploads/2022/08/01123807/United_Launch_Alliance-Logo.wine_.png"),
    MainViewORG("Virgin Galactic", "https://www.virgingalactic.com/assets/images/share.png")
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
fun NestedMissionList(list: List<MainViewORG>, navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    val filteredList = remember(searchText) {
        list.filter {
            it.name?.contains(searchText, ignoreCase = true) == true
        }
    }

    Column {
        androidx.compose.material3.OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = { Text("Search Organization") },
            singleLine = true
        )

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
fun MissionCard(org: MainViewORG, onItemClick : () -> Unit) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = {onItemClick()},
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column() {
            org.image?.let {
                org.name?.let { it1 ->
                    GlideImage(
                        model = it,
                        contentDescription = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillBounds
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