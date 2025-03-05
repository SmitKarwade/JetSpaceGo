package com.example.jetspacego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.jetspacego.model.Result
import com.example.jetspacego.paging.SpaceViewModel
import com.example.jetspacego.ui.theme.JetSpaceGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainUI{
                NestedMissionList()
            }
        }
    }
}

@Composable
fun MainUI(content: @Composable () -> Unit){
    JetSpaceGoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                content()
            }

        }
    }
}


@Composable
fun NestedMissionList(viewModel: SpaceViewModel = hiltViewModel()) {
    val launches = viewModel.launchFlow.collectAsLazyPagingItems()

    val missionList = List(launches.itemCount){index ->
        launches[index]
    }

    val groupedMissions = missionList.groupBy { mission ->
        mission?.agencies?.get(0)?.abbrev
    }

    LazyColumn {
        groupedMissions.forEach(){index, value ->
            item {
                if (index != null && value != null) {
                    MissionCategoryRow(index, value)
                }
            }
        }
    }
}

@Composable
fun MissionCategoryRow(abbrev: String, missions: List<com.example.jetspacego.model.Result?>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = abbrev,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        LazyRow {
            items(missions) { mission ->
                if (mission != null) {
                    MissionCard(mission)
                }
            }
        }
    }
}

@Composable
fun MissionCard(mission: Result) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            GlideImage(
                model = mission.image.imageUrl,
                contentDescription = mission.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = mission.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = mission.agencies[0].name,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetSpaceGoTheme {

    }
}