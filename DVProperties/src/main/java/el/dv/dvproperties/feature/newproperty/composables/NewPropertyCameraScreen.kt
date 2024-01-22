package el.dv.dvproperties.feature.newproperty.composables

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import el.dv.presentation.camerax.CameraScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewPropertyCameraScreen(
    controller: LifecycleCameraController,
    scaffoldState: BottomSheetScaffoldState
) {
    BottomSheetScaffold(
        sheetContent = {

        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CameraScreen(controller = controller, modifier = Modifier.fillMaxSize())
        }
    }
}
