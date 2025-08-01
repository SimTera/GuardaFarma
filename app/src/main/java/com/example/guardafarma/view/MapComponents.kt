package com.example.guardafarma.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.guardafarma.model.LocationModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.example.guardafarma.R

/**
 * Componente de Google Maps para mostrar la ubicación del usuario y una lista de puntos de interés.
 * - Centra el mapa en el usuario si existe.
 * - Dibuja marcadores para cada punto de `markers` (ej: farmacias) con icono de farmacia.
 */

@Composable
fun GoogleMapComponent(
    userLocation: LocationModel?,
    markers: List<LocationModel>,
    defaultPosition: LatLng = LatLng(41.5348, 2.1826), // Santa Perpètua de Mogoda
    defaultZoom: Float = 15f
) {
    val cameraPositionState = rememberCameraPositionState()

    val targetLatLng = userLocation?.let { LatLng(it.latitude, it.longitude) } ?: defaultPosition

    LaunchedEffect(targetLatLng) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(targetLatLng, defaultZoom))
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = com.google.maps.android.compose.MapProperties(isMyLocationEnabled = true)
    ) {
        userLocation?.let {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = "Tu posición",
                snippet = "Estas aquí."
            )
        }
        markers.forEach { location ->
            Marker(
                state = MarkerState(LatLng(location.latitude, location.longitude)),
                title = location.name,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia)
            )
        }
    }
}