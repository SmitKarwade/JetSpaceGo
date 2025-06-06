package com.example.jetspacego.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetspacego.model.MainViewORG
import com.example.jetspacego.model.launches.Agencies
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.screens.main.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }
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

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            when (selectedTab) {
                0 -> result?.let {
                    OverviewTab(it, navController = navController)
                }
                1 -> result?.mission?.agencies?.let { AgenciesTab(it) }
                2 -> result?.let { MoreInfoTab(it) }
            }
        }
    }
}

@Composable
fun OverviewTab(result: Results, navController: NavController) {
    val list = listOf(
        MainViewORG(result?.pad?.location?.image?.name, result?.pad?.location?.image?.imageUrl),
        MainViewORG(result?.pad?.image?.name, result?.pad?.image?.imageUrl),
//        MainViewORG(result?.pad?.agencies?.get(0)?.image?.name, result?.pad?.agencies?.get(0)?.image?.imageUrl),
//        MainViewORG(result?.mission?.agencies?.get(0)?.image?.name, result?.mission?.agencies?.get(0)?.image?.imageUrl),
        MainViewORG(result?.image?.name, result?.image?.imageUrl),
        MainViewORG("Location", result?.pad?.location?.mapImage)
    )

    Column(modifier = Modifier.padding(16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))
        result.launchServiceProvider?.name?.let { Text(text = it, style = MaterialTheme.typography.displaySmall) }
        Spacer(modifier = Modifier.height(8.dp))
        if(result.pad?.agencies?.size != 0) {
            if (result.pad?.agencies?.get(0)?.country?.size != 0) {
                result.pad?.agencies?.get(0)?.country?.get(0)?.name?.let { Text(text = it, style = TextStyle(color = Color.Black, fontSize = 14.sp), modifier = Modifier.border(1.dp,
                    Color.Gray, RoundedCornerShape(7.dp)).padding(4.dp)) }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        result.name?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
        Spacer(modifier = Modifier.height(8.dp))
        result.mission?.description?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
        Spacer(modifier = Modifier.height(20.dp))
        AutoScrollingCarousel(list)
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Type", style = TextStyle(color = Color.Gray, fontSize = 14.sp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${result.launchServiceProvider?.type?.name}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(20.dp))
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
fun AutoScrollingCarousel(items: List<MainViewORG>) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500L)
            val nextIndex = (listState.firstVisibleItemIndex + 1) % items.size
            listState.animateScrollToItem(nextIndex)
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index ->
            CarouselCard(item = items[index])
        }
    }
}

@Composable
fun CarouselCard(item: MainViewORG) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .wrapContentSize()
    ) {
        item.image?.let { GlideImage(model = it,
            contentDescription = null.toString(),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.width(400.dp)
                .height(500.dp)
                .clip(shape = RoundedCornerShape(12.dp))) }
        Spacer(modifier = Modifier.height(8.dp))
        item.name?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}



@Composable
fun AgenciesTab(agencies: ArrayList<Agencies>) {
    if(agencies?.size != 0){
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
    }else{
        Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Text(
                text = "No Agencies",
                modifier = Modifier.align(Alignment.Center)
            )
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




