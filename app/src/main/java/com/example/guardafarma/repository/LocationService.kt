package com.example.guardafarma.repository

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.example.guardafarma.model.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class LocationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // En LocationService o RouteService (según tu estructura) he de crearlo entero:
    suspend fun getDirections(origin: LatLng, destination: LatLng): String {
        // TODO: Implementar - Llamar a Google Directions API
        // y devolver el "overview_polyline" de la ruta.
        throw NotImplementedError("Esta función se debe implementar: fetch directions route")
    }

    suspend fun getLastKnownLocation(): LocationModel? {
        return try {
            val loc = fusedLocationClient.lastLocation.await()
            loc?.let {
                LocationModel("Tu ubicación", it.latitude, it.longitude)
            }
        } catch (e: SecurityException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}

// Esto seria sin Hilt
//class LocationService(
//    private val context: Context
//) {
//    private val fusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(context)
//
//    suspend fun getLastKnowLocation(): LocationModel? {
//        return try {
//            val location = fusedLocationClient.lastLocation.await ()
//            location.let {
//                LocationModel(
//                    name = "Tu ubicación",
//                    latitude = it.latitude,
//                    longitude = it.longitude
//                )
//            }
//        } catch (e: SecurityException) {
//            null // No se tienen permisos
//        } catch (e: Exception) {
//            null // Otros posibles problemas
//        }
//    }
//}