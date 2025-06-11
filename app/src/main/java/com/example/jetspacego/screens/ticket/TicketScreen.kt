import android.content.Context
import android.view.MotionEvent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.jetspacego.model.rocket.RocketModel
import com.example.jetspacego.model.rocket.rocketList
import io.github.sceneview.Scene
import io.github.sceneview.collision.HitResult
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.CylinderNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraManipulator
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironment
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMainLightNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(navController: NavController){
    val context = LocalContext.current
    var selectedModel by remember { mutableStateOf(rocketList[0]) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Rocket 3D Model")
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            // Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedModel.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Rocket") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor()
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
            ModelViewer(context = context, selectedModel)
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

    val modelInstance by produceState<ModelNode?>(initialValue = null, rocketModelURL) {
        // Clear existing value immediately
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
                value = ModelNode(modelInstance = instance, scaleToUnits = 1.0f)
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
                position = Position(y = 0.5f, z = 1.5f)
            },
            cameraManipulator = null,
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
            onFrame = { /* Optional frame update logic */ }
        )
    } ?: run {
        Text("Loading model...", modifier = Modifier.fillMaxSize())
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

