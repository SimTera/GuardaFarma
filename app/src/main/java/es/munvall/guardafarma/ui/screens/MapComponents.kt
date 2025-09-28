package es.munvall.guardafarma.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import es.munvall.guardafarma.data.model.LocationModel
import es.munvall.guardafarma.data.model.FarmaciaDTO
import es.munvall.guardafarma.ui.viewmodel.GuardiaViewModel
import es.munvall.guardafarma.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

//private val Unit.drawable: Any


/**
 * Componente de Google Maps para mostrar la ubicación del usuario y una lista de puntos de interés.
 * - Centra el mapa en el usuario si existe.
 * - Dibuja marcadores para cada punto de `markers` (ej: farmacias) con icono de farmacia.
 */

@Composable
fun OsmMapComponent(
    userLocation: LocationModel?,
    farmacias: List<FarmaciaDTO>,
    viewModel: GuardiaViewModel,
    defaultLat: Double = 41.5348,
    defaultLon: Double = 2.1826,
    defaultZoom: Double = 15.0
){
    val context = LocalContext.current
    val farmaciaDeHoy by viewModel.farmaciaDeHoy.collectAsState()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                // Config inicial camera
                controller.setZoom(defaultZoom)
                controller.setCenter(GeoPoint(
                    userLocation?.latitude ?: defaultLat,
                    userLocation?.longitude ?: defaultLon)
                )

                //Para mostrar ubicacion 🔹 y seguir usuario
                val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(ctx), this)
                locationOverlay.enableMyLocation()
                locationOverlay.enableFollowLocation()
                overlays.add(locationOverlay)
            }
        },
        update = { mapView ->
            mapView.overlays.removeIf { it is Marker }

            //Añadir marcador de usuario
            userLocation?.let {
                val marker = Marker(mapView).apply {
                    position = GeoPoint(it.latitude, it.longitude)
                    title = "Tu posición"
                    snippet = "Estas aquí"

                }
                mapView.overlays.add(marker)
            }

            //Añadir farmacias al mapa
            farmacias.forEach { farmacia ->
                val deGuardia = farmaciaDeHoy?.id == farmacia.id
                val marker = Marker(mapView).apply {
                    position = GeoPoint(farmacia.latitude, farmacia.longitude)
                    title = farmacia.nombre
                    snippet = if (deGuardia) "⭐ DE GUARDIA ⭐" else farmacia.direccion
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    // Iconos personalizados
                    icon = ContextCompat.getDrawable(context, if (deGuardia) R.drawable.ic_farmacia else R.drawable.ic_farmacy_secundary)
                }
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
    )
}

// Esto lo quito porque google es muy caro para una app gratuita
//fun GoogleMapComponent(
//    userLocation: LocationModel?,
//    farmacias: List<FarmaciaDTO>,
//    viewModel: GuardiaViewModel,
//    defaultPosition: LatLng = LatLng(41.5348, 2.1826), // Santa Perpètua de Mogoda
//    defaultZoom: Float = 15f
//) {
//    val cameraPositionState = rememberCameraPositionState()
//
//    val targetLatLng = userLocation?.let { LatLng(it.latitude, it.longitude) } ?: defaultPosition
//
//    val farmaciaDeHoy by viewModel.farmaciaDeHoy.collectAsState()
//
//
//    LaunchedEffect(targetLatLng) {
//        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(targetLatLng, defaultZoom))
//    }
//

//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState,
//        properties = MapProperties(
//            isMyLocationEnabled = userLocation != null // Solo si tenemos ubicación
//        )
//    ) {
//        userLocation?.let {
//            Marker(
//                state = MarkerState(LatLng(it.latitude, it.longitude)),
//                title = "Tu posición",
//                snippet = "Estas aquí."
//            )
//        }
//        farmacias.forEach { farmacia ->
//            val isGuardia = farmaciaDeHoy?.let {
//                it.id == farmacia.id
//            } ?: false
//            val snippet = if (isGuardia) {
//                "⭐ DE GUARDIA ⭐"
//            } else {
//                // Para farmacias que no están de guardia, mostrar información adicional
//                buildString {
//                    append(farmacia.direccion)
//                    if (farmacia.telefono.isNotEmpty()) {
//                        append("\nTel: ${farmacia.telefono}")
//                    }
//                }
//            }
//            Marker(
//                state = MarkerState(LatLng(farmacia.latitude, farmacia.longitude)),
//                title = farmacia.nombre,
//                snippet = snippet,
//                icon = if (isGuardia) {
//                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia)
//                } else {
//                    BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacy_secundary)
//                }
//            )
//        }
//    }
//}