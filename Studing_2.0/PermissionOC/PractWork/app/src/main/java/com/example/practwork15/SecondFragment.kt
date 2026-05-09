package com.example.practwork15

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.practwork15.databinding.FragmentFirstBinding
import com.example.practwork15.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.getValue

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var imageCapture : ImageCapture? = null
    private lateinit var executor : Executor

    private lateinit var db: DataBaseHelper

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startCamera()
                } else {
                    Toast.makeText(requireContext(), "Камера не разрешена", Toast.LENGTH_SHORT).show()
                }
        }

     fun checkCameraPermission () {
        val isGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
        if(isGranted) {
            startCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSecondBinding.bind(view)

        db = DataBaseHelper(requireContext())
        executor = ContextCompat.getMainExecutor(requireContext())

        binding.backButton.setOnClickListener {

            Toast.makeText(requireContext(), "переход", Toast.LENGTH_SHORT).show()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FirstFragment())
                .addToBackStack(null)
                .commit()
        }
        checkCameraPermission()

        binding.captureButton.setOnClickListener {
            takePhoto()
        }



    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val fileName = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss",
            Locale.US
        ).format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireActivity().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri

                    Toast.makeText(requireContext(), "Фото сохранено", Toast.LENGTH_SHORT).show()

                    if (savedUri != null) {

                        val currentDate = SimpleDateFormat(
                            "dd.MM.yyyy HH:mm",
                            Locale.getDefault()
                        ).format(Date())

                        db.insertItem(savedUri.toString(), currentDate)

                        Glide.with(this@SecondFragment)
                            .load(savedUri)
                            .centerCrop()
                            .into(binding.photoPreview)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    fun startCamera () {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )

        },executor )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}