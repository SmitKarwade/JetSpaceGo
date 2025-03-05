package com.example.jetspacego

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetspacego.repository.SpaceRepository
import com.example.jetspacego.ui.theme.JetSpaceGoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: SpaceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetSpaceGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        repository = repository
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, repository: SpaceRepository) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        val missions = repository.getMissions(30, 0)
        Log.d("Missions", missions.results.size.toString())
        Log.d("Missions", missions.results.toString())
    }

    //GlideImage(model = "https://thespacedevs-prod.nyc3.digitaloceanspaces.com/media/images/luna_1_impactor_image_thumbnail_20240813082956.jpeg", contentDescription = "Luna Image")

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetSpaceGoTheme {

    }
}