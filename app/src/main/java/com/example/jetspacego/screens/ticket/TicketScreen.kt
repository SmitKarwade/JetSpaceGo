import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.jetspacego.model.rocket.RocketModel
import com.example.jetspacego.model.rocket.rocketList
import com.example.jetspacego.viewmodel.RocketViewModel
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraManipulator
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import java.util.Base64

import androidx.media3.datasource.ByteArrayDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(navController: NavController, viewModel: RocketViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var selectedModel by remember { mutableStateOf(rocketList[0]) }
    var expanded by remember { mutableStateOf(false) }
    var playRequested by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Rocket 3D Model", modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded},
                modifier = Modifier.padding(8.dp)
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = selectedModel.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Rocket") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF0F0F0),
                        unfocusedContainerColor = Color(0xFFF0F0F0),
                        disabledContainerColor = Color(0xFFF0F0F0),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    rocketList.forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model.name) },
                            onClick = {
                                selectedModel = model
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            ModelViewer(context = context, selectedModel)

        }

        // FAB layered on top using Box
        FloatingActionButton(
            onClick = { playRequested = true },
            containerColor = Color(0xFF1E88E5),
            contentColor = Color.White,
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "🔊", fontSize = 22.sp)
        }

        if (playRequested) {
            RocketAudioPlayer(
                viewModel = viewModel,
                rocketDesc = selectedModel.description,
                playRequested = playRequested,
                onPlaybackHandled = {
                    playRequested = false
                }
            )
        }

        LaunchedEffect(selectedModel) {
            viewModel.clearAudioBase64()
        }
    }
}



@Composable
fun ModelViewer(context: Context, rocketModelURL: RocketModel) {
    val engine = rememberEngine()
    val view = rememberView(engine)

    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)
    val cameraManipulator = rememberCameraManipulator()

    val modelInstance by produceState<ModelNode?>(initialValue = null, rocketModelURL) {
        value = null

        val file = withContext(Dispatchers.IO) {
            try {
                downloadModelFile(context, rocketModelURL.url)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        file?.let {
            try {
                val instance = modelLoader.createModelInstance(file = it)
                value = ModelNode(modelInstance = instance, scaleToUnits = 0.4f)
            } catch (e: Exception) {
                e.printStackTrace()
                value = null
            }
        }
    }



    val isZoomedIn = remember(rocketModelURL) { mutableStateOf(false) }

    modelInstance?.let { modelNode ->
        Scene(
            modifier = Modifier.fillMaxSize(),
            engine = engine,
            view = view,
            renderer = rememberRenderer(engine),
            scene = rememberScene(engine),
            modelLoader = modelLoader,
            materialLoader = materialLoader,
            environmentLoader = environmentLoader,
            collisionSystem = rememberCollisionSystem(view),
            mainLightNode = rememberMainLightNode(engine) {
                intensity = 100_000.0f
            },
            cameraNode = rememberCameraNode(engine) {
                position = Position(z = 2.5f)
            },
            cameraManipulator = cameraManipulator,
            childNodes = remember(rocketModelURL) {
                mutableListOf(modelNode)
            },
//            onGestureListener = remember(rocketModelURL) {
//                rememberOnGestureListener(
//                    onDoubleTapEvent = { _, tappedNode ->
//                        tappedNode?.let {
//                            if (isZoomedIn.value) {
//                                it.scale /= 0.5f
//                            } else {
//                                it.scale *= 0.5f
//                            }
//                            isZoomedIn.value = !isZoomedIn.value
//                        }
//                    }
//                )
//            },
            onTouchEvent = { _, hitResult ->
                hitResult?.let {
                    println("World tapped : ${it.worldPosition}")
                }
                false
            },
            onFrame = { }
        )
    } ?: run {
        Text("Loading model...", modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun RocketAudioPlayer(
    viewModel: RocketViewModel,
    rocketDesc: String,
    playRequested: Boolean,
    onPlaybackHandled: () -> Unit
) {
    val context = LocalContext.current
    val audioBase64 by viewModel.audioBase64.collectAsState()


    LaunchedEffect(playRequested) {
        if (playRequested) {
            viewModel.getRocketAudio(rocketDesc)
        }
    }

    LaunchedEffect(playRequested, audioBase64) {
        if (playRequested && audioBase64 != null) {
            playBase64WithExoPlayer(context, audioBase64!!)
            onPlaybackHandled()
        }
    }
}




suspend fun downloadModelFile(context: Context, url: String): File {
    val file = File(context.cacheDir, "model.glb")
    withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        file.outputStream().use { output ->
            connection.inputStream.use { input ->
                input.copyTo(output)
            }
        }
    }
    return file
}

private var exoPlayer: ExoPlayer? = null

@androidx.annotation.OptIn(UnstableApi::class)
fun playBase64WithExoPlayer(context: Context, base64: String) {
    exoPlayer?.release() // Clean previous
    exoPlayer = ExoPlayer.Builder(context).build().apply {
        val decodedBytes = Base64.getDecoder().decode(base64)
        val dataSourceFactory = DataSource.Factory {
            ByteArrayDataSource(decodedBytes)
        }

        val mediaItem = MediaItem.fromUri("memory://audio.mp3")
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)

        setMediaSource(mediaSource)
        prepare()
        play()

        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    release()
                    exoPlayer = null
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                android.util.Log.e("AudioError", "Playback error: ${error.message}")
                release()
                exoPlayer = null
            }
        })
    }
}







