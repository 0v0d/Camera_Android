package com.example.camera.viewmodel

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest = _surfaceRequest.asStateFlow()

    private val cameraPreviewUseCase = androidx.camera.core.Preview.Builder().build().apply {
        setSurfaceProvider { surfaceRequest ->
            _surfaceRequest.value = surfaceRequest
        }
    }

    suspend fun bindCameraUseCases(appContent: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContent)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, cameraPreviewUseCase
        )
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }
}
