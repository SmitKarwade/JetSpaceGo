package com.example.jetspacego.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.jetspacego.model.MissionFilterType
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.paging.SpaceViewModel
import kotlinx.coroutines.delay
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MissionList(navController: NavController){
    DisplayMission(navController)
}


@Composable
fun DisplayMission(navController: NavController, viewModel: SpaceViewModel = hiltViewModel()) {
    val name = navController.previousBackStackEntry?.savedStateHandle?.get<String>("msn_name")
//    val missionFlow = remember(name) { viewModel.getLaunchFlow(name) }
//    val displayItems = missionFlow.collectAsLazyPagingItems()

    var selectedFilter by remember { mutableStateOf(MissionFilterType.ALL) }
    val nowIso = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    val launchFlow = remember(selectedFilter) {
        when (selectedFilter) {
            MissionFilterType.ALL ->
                viewModel.getLaunchFlow(ordering = "-net", searchQuery = name)
            MissionFilterType.UPCOMING ->
                viewModel.getLaunchFlow(
                    windowStartAfter = nowIso,
                    ordering = "net",
                    searchQuery = name
                )
            MissionFilterType.PAST ->
                viewModel.getLaunchFlow(
                    windowEndBefore = nowIso,
                    ordering = "-net",
                    searchQuery = name
                )
            MissionFilterType.NEXT ->
                viewModel.getLaunchFlow(
                    windowStartAfter = nowIso,
                    ordering = "net",
                    limit = 1,
                    searchQuery = name
                )
        }
    }

    val items = launchFlow.collectAsLazyPagingItems()

    val isLoading = items.loadState.refresh is androidx.paging.LoadState.Loading
    val isError = items.loadState.refresh is androidx.paging.LoadState.Error
    val isEmpty = items.itemCount == 0 && !isLoading && !isError


    Column {
        MissionFilterChips(
            selectedFilter = selectedFilter,
            onFilterSelected = {
                selectedFilter = it
            }
        )

        if (isEmpty) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No mission found", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = items.itemCount,
                    contentType = { index -> "missionContent" },
                    span = { index ->
                        if (index == 0) GridItemSpan(2) else GridItemSpan(1)
                    }
                ) { index ->
                    val mission = items[index]
                    mission?.let {
                        if (index == 0) {
                            CountdownMissionCard(mission = it) {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("msn", it)
                                navController.navigate("details")
                            }
                        } else {
                            MissionCard(mission = it) {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("msn", it)
                                navController.navigate("details")
                            }
                        }
                    }
                }



                items.apply {
                    when {
                        loadState.refresh is androidx.paging.LoadState.Loading -> {
                            item(span = { GridItemSpan(2) }) {
                                Text("Refreshing...", modifier = Modifier.fillMaxWidth().padding(16.dp))
                            }
                        }
                        loadState.append is androidx.paging.LoadState.Loading -> {
                            item(span = { GridItemSpan(2) }) {
                                Text("Loading more...", modifier = Modifier.fillMaxWidth().padding(16.dp))
                            }
                        }
                        loadState.refresh is androidx.paging.LoadState.Error -> {
                            val e = loadState.refresh as androidx.paging.LoadState.Error
                            item(span = { GridItemSpan(2) }) {
                                Text("Error refreshing: ${e.error.localizedMessage}", modifier = Modifier.fillMaxWidth().padding(16.dp))
                            }
                        }
                        loadState.append is androidx.paging.LoadState.Error -> {
                            val e = loadState.append as androidx.paging.LoadState.Error
                            item(span = { GridItemSpan(2) }) {
                                Text("Error loading more: ${e.error.localizedMessage}", modifier = Modifier.fillMaxWidth().padding(16.dp))
                            }
                        }
                    }
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
        colors = CardDefaults.cardColors(containerColor = Color(0x802E5979))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            mission.image?.imageUrl?.let {
                mission.name?.let { it1 ->
                    com.bumptech.glide.integration.compose.GlideImage(
                        model = it,
                        contentDescription = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(275.dp)
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )
            }

            mission.launchServiceProvider?.name?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            mission.net?.let {
                Text(
                    text = "Launch Date",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            mission.net?.let {
                Text(
                    text = "${getMissionLaunchDate(it)}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.border(2.dp, Color(0xE6324B73), RoundedCornerShape(4.dp)).align(Alignment.Start)) {
                Text(text = getMissionStatus(mission.net.toString()), color = Color.White,
                    fontSize = 12.sp, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun MissionFilterChips(
    selectedFilter: MissionFilterType,
    onFilterSelected: (MissionFilterType) -> Unit
) {
    Row(modifier = Modifier.padding(16.dp).wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.background(Color(0xE6324B73), RoundedCornerShape(8.dp)).padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)){
            Text("Filter", modifier = Modifier.padding(4.dp))
        }
        VerticalDivider(modifier = Modifier.padding(start = 8.dp, end = 16.dp).height(30.dp), thickness = 2.dp, color = Color(0XB3FFFFFF))
        MissionFilterType.values().forEach { filter ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter.displayName) },
                modifier = Modifier.padding(end = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedLabelColor = Color.Black,
                    labelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(borderColor = Color(0xE6324B73), borderWidth = 2.dp, enabled = true, selected = false,
                    selectedBorderColor = Color.White
                )
            )
        }
    }
}



fun getMissionStatus(net: String): String {
    return try {
        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val launchTime = ZonedDateTime.parse(net, formatter)
        val currentTime = ZonedDateTime.now()

        if (launchTime.isAfter(currentTime)) {
            "Upcoming"
        } else {
            "Past"
        }
    } catch (e: Exception) {
        "Unknown"
    }
}

fun getMissionLaunchDate(net: String): String {
    return try {
        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        val launchTime = ZonedDateTime.parse(net, formatter)

        return launchTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        "Unknown"
    }
}

@Composable
fun CountdownMissionCard(mission: Results, onItemClick: () -> Unit) {
    val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val launchTime = remember {
        try {
            ZonedDateTime.parse(mission.net, formatter)
        } catch (e: Exception) {
            null
        }
    }

    var remainingTime by remember { mutableStateOf("") }


    LaunchedEffect(launchTime) {
        while (true) {
            launchTime?.let {
                val now = ZonedDateTime.now()
                val duration = java.time.Duration.between(now, launchTime)
                if (!duration.isNegative) {
                    val hours = duration.toHours()
                    val minutes = duration.toMinutes() % 60
                    val seconds = duration.seconds % 60
                    remainingTime = String.format("%02dh %02dm %02ds", hours, minutes, seconds)
                } else {
                    remainingTime = "Launched"
                }
            }
            delay(1000L)
        }
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x802E5979))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            mission.image?.imageUrl?.let {
                mission.name?.let { it1 ->
                    GlideImage(
                        model = it,
                        contentDescription = it1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Next launch in",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$remainingTime",
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                fontSize = 28.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = mission.name ?: "Unknown",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = mission.launchServiceProvider?.name ?: "",
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            mission.net?.let {
                Text(
                    text = "Launch Date",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            mission.net?.let {
                Text(
                    text = "${getMissionLaunchDate(it)}",
                    fontSize = 14.sp,
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