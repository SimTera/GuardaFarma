package com.example.guardafarma.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guardafarma.data.model.FarmaciaDTO
import com.example.guardafarma.data.model.LocationModel
import com.example.guardafarma.ui.viewmodel.MapViewModel
import com.example.guardafarma.ui.viewmodel.RutaViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.guardafarma.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// Enum para tipos de transporte
enum class TransportMode(val displayName: String, val apiValue: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    WALKING("A pie", "walking", Icons.Default.Person),
    DRIVING("En coche", "driving", Icons.Default.Person),
    TRANSIT("Transporte público", "transit", Icons.Default.Person),
    BICYCLING("En bicicleta", "bicycling", Icons.Default.Person)
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RouteToFarmaciaScreen(
    farmacia: FarmaciaDTO,
    onBackClick: () -> Unit = {},
    mapViewModel: MapViewModel = hiltViewModel(),
    rutaViewModel: RutaViewModel = hiltViewModel()
) {
    val permisoUbicacion = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val userLocation by mapViewModel.location.collectAsState()
    val polylinePoints by rutaViewModel.polylinePoints.collectAsState()

    var selectedTransport by remember { mutableStateOf(TransportMode.WALKING) }
    var isLoadingRoute by remember { mutableStateOf(false) }
    var routeError by remember { mutableStateOf<String?>(null) }

    // Solicitar ubicación al iniciar
    LaunchedEffect(permisoUbicacion.status) {
        if (permisoUbicacion.status.isGranted) {
            mapViewModel.fetchUserLocation()
        } else {
            permisoUbicacion.launchPermissionRequest()
        }
    }

    // Cargar ruta cuando tengamos ubicación del usuario
    LaunchedEffect(userLocation, selectedTransport) {
        userLocation?.let { userLoc ->
            isLoadingRoute = true
            routeError = null
            try {
                val origin = LatLng(userLoc.latitude, userLoc.longitude)
                val destination = LatLng(farmacia.latitude, farmacia.longitude)
                rutaViewModel.loadRoute(origin, destination)
            } catch (e: Exception) {
                routeError = "Error al calcular la ruta"
            } finally {
                isLoadingRoute = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ruta a ${farmacia.nombreComercial}",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            // Bottom sheet con información de la farmacia y controles
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Información de la farmacia
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = farmacia.nombre,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = farmacia.direccion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            if (farmacia.telefono.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tel: ${farmacia.telefono}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de modo de transporte
                    Text(
                        text = "Modo de transporte:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(TransportMode.values()) { mode ->
                            TransportModeCard(
                                mode = mode,
                                isSelected = selectedTransport == mode,
                                onClick = { selectedTransport = mode }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Estado de la carga
                    when {
                        userLocation == null -> {
                            Text(
                                text = "📍 Obteniendo tu ubicación...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        isLoadingRoute -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Calculando ruta...",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        routeError != null -> {
                            Text(
                                text = "⚠️ $routeError",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        polylinePoints.isNotEmpty() -> {
                            Text(
                                text = "✅ Ruta calculada",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Mapa con la ruta
            RouteMapComponent(
                userLocation = userLocation,
                farmacia = farmacia,
                polylinePoints = polylinePoints
            )
        }
    }
}

@Composable
fun TransportModeCard(
    mode: TransportMode,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = mode.icon,
                contentDescription = mode.displayName,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = mode.displayName,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
fun RouteMapComponent(
    userLocation: LocationModel?,
    farmacia: FarmaciaDTO,
    polylinePoints: String
) {
    val cameraPositionState = rememberCameraPositionState()

    // Centrar entre usuario y farmacia, o en la farmacia si no hay ubicación del usuario
    val targetLatLng = userLocation?.let {
        // Punto medio entre usuario y farmacia
        val midLat = (it.latitude + farmacia.latitude) / 2
        val midLng = (it.longitude + farmacia.longitude) / 2
        LatLng(midLat, midLng)
    } ?: LatLng(farmacia.latitude, farmacia.longitude)

    LaunchedEffect(targetLatLng) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(targetLatLng, 14f)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = com.google.maps.android.compose.MapProperties(
            isMyLocationEnabled = userLocation != null
        )
    ) {
        // Marcador del usuario
        userLocation?.let {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = "Tu posición",
                snippet = "Estás aquí",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        }

        // Marcador de la farmacia destino
        Marker(
            state = MarkerState(LatLng(farmacia.latitude, farmacia.longitude)),
            title = farmacia.nombre,
            snippet = "⭐ DE GUARDIA ⭐",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia)
        )
    }


}