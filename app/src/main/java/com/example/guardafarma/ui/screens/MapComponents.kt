package com.example.guardafarma.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.guardafarma.data.model.LocationModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.example.guardafarma.R
import com.example.guardafarma.ui.viewmodel.GuardiaViewModel

/**
 * Componente de Google Maps para mostrar la ubicación del usuario y una lista de puntos de interés.
 * - Centra el mapa en el usuario si existe.
 * - Dibuja marcadores para cada punto de `markers` (ej: farmacias) con icono de farmacia.
 */

@Composable
fun GoogleMapComponent(
    userLocation: LocationModel?,
    markers: List<LocationModel>,
    viewModel: GuardiaViewModel,
    defaultPosition: LatLng = LatLng(41.5348, 2.1826), // Santa Perpètua de Mogoda
    defaultZoom: Float = 15f
) {
    val cameraPositionState = rememberCameraPositionState()

    val targetLatLng = userLocation?.let { LatLng(it.latitude, it.longitude) } ?: defaultPosition

    val farmaciaDeHoy by viewModel.farmaciaDeHoy.collectAsState()


    LaunchedEffect(targetLatLng) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(targetLatLng, defaultZoom))
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = com.google.maps.android.compose.MapProperties(
            isMyLocationEnabled = userLocation != null // Solo si tenemos ubicación
        )
    ) {
        userLocation?.let {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = "Tu posición",
                snippet = "Estas aquí."
            )
        }
        markers.forEach { location ->
            val isGuardia = farmaciaDeHoy?.let {
                it.latitude == location.latitude && it.longitude == location.longitude
            } ?: false
            Marker(
                state = MarkerState(LatLng(location.latitude, location.longitude)),
                title = location.name,
                snippet = if (isGuardia) "⭐ DE GUARDIA ⭐" else "Farmacia",
                icon = if (isGuardia) {
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia)
                } else {
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacy_secundary)
                }
            )
        }
    }
}