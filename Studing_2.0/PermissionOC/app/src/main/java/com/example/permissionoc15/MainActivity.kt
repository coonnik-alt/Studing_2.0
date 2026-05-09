package com.example.permissionoc15

import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.video.OutputFileResults
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.permissionoc15.databinding.ActivityMainBinding
import com.google.common.util.concurrent.ListenableFuture
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import android.Manifest
import com.bumptech.glide.Glide

private const val FILENAME_FORMAT = "yyyy-MM-dd-mm-ss"
class MainActivity : AppCompatActivity() {

    private var imageCapture : ImageCapture? = null
    private lateinit var executor: Executor
    private lateinit var binding : ActivityMainBinding

    private val name = SimpleDateFormat(FILENAME_FORMAT , Locale.US)
        .format(System.currentTimeMillis())
    val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ map ->

        if (map.values.all { it }){
            startCamera()
        } else {
            Toast.makeText(this, "permission is not Granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executor = ContextCompat.getMainExecutor(this)

        binding.cameraButton.setOnClickListener { takePhoto() }

        checkPermissions()
        }

    fun takePhoto(){
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME , name)
            put(MediaStore.MediaColumns.MIME_TYPE , "image/jpeg" )
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(this@MainActivity , "Photo saved on ${outputFileResults.savedUri}",Toast.LENGTH_SHORT).show()

                    Glide.with(this@MainActivity)
                        .load(outputFileResults.savedUri)
                        .circleCrop()
                        .into(binding.imageView)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@MainActivity , "Saving failed ${exception.message}",Toast.LENGTH_SHORT).show()
                }

            }

        )

    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview , imageCapture)

        }, executor)
    }
     fun checkPermissions(){
         val isAllGranted = REQUEST_PERMISSION.all { permission ->
             ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
         }

     if (isAllGranted) {
         startCamera()
         Toast.makeText(this, "permission is Granted", Toast.LENGTH_SHORT).show()
     } else {
         launcher.launch(REQUEST_PERMISSION)
     }
    }

    companion object {
        private val REQUEST_PERMISSION : Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    }
