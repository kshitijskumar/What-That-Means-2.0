package com.example.whatthatmeans20

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatthatmeans20.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraExecutor: ExecutorService

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it) {
            startCamera()
        }else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestPermission() {

        val hasCamPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        when {
            hasCamPermission -> startCamera()
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.perm_rationale_title))
                    .setMessage(getString(R.string.reason_for_camera_perm))
                    .setPositiveButton(
                        "Request Permission"
                    ) { dialog, _ ->
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        dialog.dismiss()
                    }
                    .show()
            }
            else -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener( {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )
            }catch (e: Exception) {
                Log.d("MainActivity", "StartCamera, use case binding failed ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
