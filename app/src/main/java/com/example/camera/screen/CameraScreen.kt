package com.example.camera.screen

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.camera.screen.components.CameraScreenContent
import com.example.camera.viewmodel.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    if (cameraPermissionState.status.isGranted) {
        CameraScreenContent(
            viewModel = CameraViewModel(),
            modifier = modifier
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text(text = "Request Camera Permission")
            }
        }
    }
}