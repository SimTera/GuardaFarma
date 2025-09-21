package es.munvall.guardafarma.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import es.munvall.guardafarma.data.model.LocationModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import es.munvall.guardafarma.data.model.FarmaciaDTO
import es.munvall.guardafarma.ui.viewmodel.GuardiaViewModel
import com.google.maps.android.compose.MapProperties
import es.munvall.guardafarma.R

//private val Unit.drawable: Any


/**
 * Componente de Google Maps para mostrar la ubicación del usuario y una lista de puntos de interés.
 * - Centra el mapa en el usuario si existe.
 * - Dibuja marcadores para cada punto de `markers` (ej: farmacias) con icono de farmacia.
 */

@Composable
fun GoogleMapComponent(
    userLocation: LocationModel?,
    farmacias: List<FarmaciaDTO>,
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
        properties = MapProperties(
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
        farmacias.forEach { farmacia ->
            val isGuardia = farmaciaDeHoy?.let {
                it.id == farmacia.id
            } ?: false
            val snippet = if (isGuardia) {
                "⭐ DE GUARDIA ⭐"
            } else {
                // Para farmacias que no están de guardia, mostrar información adicional
                buildString {
                    append(farmacia.direccion)
                    if (farmacia.telefono.isNotEmpty()) {
                        append("\nTel: ${farmacia.telefono}")
                    }
                }
            }
            Marker(
                state = MarkerState(LatLng(farmacia.latitude, farmacia.longitude)),
                title = farmacia.nombre,
                snippet = snippet,
                icon = if (isGuardia) {
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia)
                } else {
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacy_secundary)
                }
            )
        }
    }
}