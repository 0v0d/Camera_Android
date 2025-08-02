
package com.example.camera.screen.components

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.camera.viewmodel.CameraViewModel

@Composable
fun CameraScreenContent(
    viewModel: CameraViewModel,
    modifier: Modifier = Modifier,
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let {
        Box(contentAlignment = Alignment.BottomCenter) {
            CameraXViewfinder(
                modifier = modifier.fillMaxSize(),
                surfaceRequest = it,
            )

            Button(
                modifier = Modifier.navigationBarsPadding(),
                onClick = { viewModel.takePicture(context) },
            ) {
                Text("Take Picture")
            }
        }
    }
}