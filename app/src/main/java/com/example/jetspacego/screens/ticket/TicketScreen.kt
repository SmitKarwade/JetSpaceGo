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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
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


@Composable
fun TicketScreen(navController: NavController){
    val context = LocalContext
    Column {
        Text("Rocket 3D Model")
        Spacer(modifier = Modifier.height(16.dp))
        ModelViewer(context = context.current)
    }
}


@Composable
fun ModelViewer(context: Context) {

// Filament 3D Engine
    val engine = rememberEngine()
    val view = rememberView(engine)

// Asset loaders
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val environmentLoader = rememberEnvironmentLoader(engine)

    val modelNode = remember {
        ModelNode(
            modelInstance = modelLoader.createModelInstance(
                assetFileLocation = "rocketAnim.glb"
            ),
            scaleToUnits = 1.0f
        )
    }

    val isZoomedIn = remember { mutableStateOf(false) }


    Scene(
        modifier = Modifier.fillMaxSize(),
        engine = engine,

        // Core rendering components
        view = view,
        renderer = rememberRenderer(engine),
        scene = rememberScene(engine),

        // Asset loaders
        modelLoader = modelLoader,
        materialLoader = materialLoader,
        environmentLoader = environmentLoader,

        // Collision System
        collisionSystem = rememberCollisionSystem(view),

        mainLightNode = rememberMainLightNode(engine) {
        intensity = 100_000.0f
        },

        // Set up environment lighting and skybox from an HDR file
//        environment = rememberEnvironment(environmentLoader) {
//            environmentLoader.createHDREnvironment(
//                assetFileLocation = "environments/sky_2k.hdr"
//            )!!
//        },

        // Configure camera position
        cameraNode = rememberCameraNode(engine) {
            position = Position(y = 0.5f, z = 1.5f)
        },

        // Enable user interaction with the camera
        cameraManipulator = null,

        // Add 3D models and objects to the scene
        childNodes = rememberNodes {
            // Add a glTF model
            add(modelNode)

            // Add a 3D cylinder with custom material
//            add(
//                CylinderNode(
//                    engine = engine,
//                    radius = 0.2f,
//                    height = 2.0f,
//                    // Simple colored material with physics properties
//                    materialInstance = materialLoader.createColorInstance(
//                        color = Color.Blue,
//                        metallic = 0.5f,
//                        roughness = 0.2f,
//                        reflectance = 0.4f
//                    )
//                ).apply {
//                    // Define the node position and rotation
//                    transform(
//                        position = Position(y = 1.0f),
//                        rotation = Rotation(x = 90.0f)
//                    )
//                })
        },

        // Handle user interactions
        onGestureListener = rememberOnGestureListener(
            onDoubleTapEvent = { event, tappedNode ->
                tappedNode?.let {
                    if (isZoomedIn.value) {
                        it.scale /= 0.5f  // Zoom out
                    } else {
                        it.scale *= 0.5f  // Zoom in
                    }
                    isZoomedIn.value = !isZoomedIn.value
                }
            }

        ),

        // Handle tap event on the scene
        onTouchEvent = { event: MotionEvent, hitResult: HitResult? ->
            hitResult?.let { println("World tapped : ${it.worldPosition}") }
            false
        },

        // Frame update callback
        onFrame = { frameTimeNanos ->
            // Handle per-frame updates here
        }
    )
}
