package com.example.guardafarma.ui.screens

import androidx.compose.runtime.*
import com.example.guardafarma.data.model.LocationModel
import com.example.guardafarma.ui.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.hilt.navigation.compose.hiltViewModel


/**
 * Pantalla principal: gestiona la lógica de permisos de ubicación y muestra el mapa.
 * Si el usuario otorga permiso, solicita y muestra su ubicación, más los marcadores definidos.
 */

@OptIn(ExperimentalPermissionsApi::class) //Esto es porque usa una clase que es experimental y puede fallar
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val permisoUbicacion = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val userLocation by viewModel.location.collectAsState()

    // PRUEBA: Lista hardcodeada de farmacias/puntos de interés
    val farmacias = listOf(
        LocationModel("Picasso", 41.530597,2.183367),
        LocationModel("La Creueta", 41.536810, 2.179458),
        LocationModel("La Rambla", 41.534765, 2.180581),
        LocationModel("B. Rico", 41.523547, 2.192853),
        LocationModel("J. Guillen", 41.533514, 2.185812),
        LocationModel("MC. Serrano", 41.531478, 2.179139),
        LocationModel("E. Ordal", 41.53540, 2.18143),
        LocationModel("C. Mella", 41.518966, 2.188255),
        LocationModel("M. Morato",41.527638, 2.180733)
    )

    // Pedimos permiso de ubicación y la ubicacion solo si aún no la tenemos
    LaunchedEffect(permisoUbicacion.status) {
        if (permisoUbicacion.status.isGranted) {
            viewModel.fetchUserLocation()
        } else {
            permisoUbicacion.launchPermissionRequest()
        }
    }

    // Pasamos la ubicación del usuario y los marcadores a tu GoogleMapComponent
    GoogleMapComponent(
        userLocation = userLocation,
        markers = farmacias
    )
}