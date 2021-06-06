package com.example.whatthatmeans20.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.whatthatmeans20.R
import com.example.whatthatmeans20.databinding.FragmentScanBinding
import java.lang.Exception

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding: FragmentScanBinding get() = _binding!!

    //we removed executors for time being.

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it) {
            startCamera()
        }else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()

        setupViews()
    }

    private fun setupViews() {
        binding.btnMove.setOnClickListener {
            findNavController().navigate(R.id.action_scanFragment_to_wordsFragment)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
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
                    viewLifecycleOwner, cameraSelector, preview
                )
            }catch (e: Exception) {
                Log.d("MainActivity", "StartCamera, use case binding failed ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun requestPermission() {

        val hasCamPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        when {
            hasCamPermission -> startCamera()
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(requireContext())
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}