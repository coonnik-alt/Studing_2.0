package com.example.geoposition

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geoposition.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import android.location.LocationManager
import android.view.View
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.user_location.UserLocationLayer

class MainActivity : AppCompatActivity() {

    val API_KEY = "e81248a1-fb78-45bb-bce8-7ad2dc115597"
    private val viewModel: MapViewModel by viewModels()
    val locationPermissionLauncer =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                enableUserLocation()
                Toast.makeText(this, "Разрешение выданно", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Разрешение не выданно", Toast.LENGTH_SHORT).show()
            }
        }

    private var userLocationLayer : UserLocationLayer? = null
    private lateinit var binding: ActivityMainBinding
    private val placemarkTapListeners = mutableListOf<com.yandex.mapkit.map.MapObjectTapListener>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("$API_KEY")
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        checkLocationPermission()
        addMarkers()
        setupZoomButtons()
        setupMyLocationButton()
    }

    private fun setupMyLocationButton() {
        binding.myLocationButton.setOnClickListener {
            val cameraPosition = userLocationLayer?.cameraPosition()

            if (cameraPosition != null) {
                binding.mapview.mapWindow.map.move(cameraPosition)
            } else {
                Toast.makeText(this, "Геопозиция ещё не найдена", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addMarkers() {
        val mapObjects = binding.mapview.mapWindow.map.mapObjects

        viewModel.landmarks.forEach { landmark ->

            val point = com.yandex.mapkit.geometry.Point(landmark.latitude, landmark.longitude)

            val placemark = mapObjects.addPlacemark()
            placemark.geometry = point

            placemark.setIcon(
                com.yandex.runtime.image.ImageProvider.fromResource(
                    this,
                    android.R.drawable.ic_dialog_map
                )
            )
            val listener = com.yandex.mapkit.map.MapObjectTapListener { _, _ ->
                binding.infoTitle.text = landmark.title
                binding.infoDescription.text = landmark.description
                binding.infoPanel.visibility = View.VISIBLE
                true
            }

            placemark.addTapListener(listener)
            placemarkTapListeners.add(listener)
            }
        }



    private fun setupZoomButtons() {
        binding.zoomInButton.setOnClickListener {
            val map = binding.mapview.mapWindow.map
            val position = map.cameraPosition

            map.move(
                CameraPosition(
                    position.target,
                    position.zoom + 1.06f,
                    position.azimuth,
                    position.tilt
                )
            )
        }

        binding.zoomOutButton.setOnClickListener {
            val map = binding.mapview.mapWindow.map
            val position = map.cameraPosition

            map.move(
                CameraPosition(
                    position.target,
                    position.zoom - 1.0f,
                    position.azimuth,
                    position.tilt
                )
            )
        }
    }

    fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableUserLocation()
        } else {
            locationPermissionLauncer.launch(permission)
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }
    private fun enableUserLocation() {
        if (userLocationLayer != null) return

        val mapKit = MapKitFactory.getInstance()

        userLocationLayer = mapKit.createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer?.isVisible = true
        userLocationLayer?.setHeadingModeActive(true)
    }
}
