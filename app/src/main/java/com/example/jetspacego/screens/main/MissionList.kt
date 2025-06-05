package com.example.jetspacego.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.paging.SpaceViewModel

@Composable
fun MissionList(navController: NavController){
    DisplayMission(navController)
}


@Composable
fun DisplayMission(navController: NavController, viewModel: SpaceViewModel = hiltViewModel()) {
    val name = navController.previousBackStackEntry?.savedStateHandle?.get<String>("msn_name")
//    val displayItems = viewModel.launchFlow.collectAsLazyPagingItems()

    val missionFlow = remember(name) { viewModel.getLaunchFlow(name) }
    val displayItems = missionFlow.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(displayItems.itemCount) { index ->
            displayItems[index]?.let {
                MissionCard(it) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("msn", it)
                    navController.navigate("details")
                }
            }
        }

        displayItems.apply {
            when {
                loadState.refresh is androidx.paging.LoadState.Loading -> {
                    item { Text("Refreshing...", modifier = Modifier.fillParentMaxSize().padding(16.dp)) }
                }
                loadState.append is androidx.paging.LoadState.Loading -> {
                    item { Text("Loading more...", modifier = Modifier.fillMaxWidth().padding(16.dp)) }
                }
                loadState.refresh is androidx.paging.LoadState.Error -> {
                    val e = loadState.refresh as androidx.paging.LoadState.Error
                    item { Text("Error refreshing: ${e.error.localizedMessage}", modifier = Modifier.fillParentMaxSize().padding(16.dp)) }
                }
                loadState.append is androidx.paging.LoadState.Error -> {
                    val e = loadState.append as androidx.paging.LoadState.Error
                    item { Text("Error loading more: ${e.error.localizedMessage}", modifier = Modifier.fillMaxWidth().padding(16.dp)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MissionCard(mission: Results, onItemClick : () -> Unit) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = {onItemClick()},
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            mission.image?.imageUrl?.let {
                mission.name?.let { it1 ->
                    com.bumptech.glide.integration.compose.GlideImage(
                        model = it,
                        contentDescription = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            mission.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            mission.launchServiceProvider?.name?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideImage(model: String, contentDescription: String, modifier: Modifier, contentScale: ContentScale) {
    com.bumptech.glide.integration.compose.GlideImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}