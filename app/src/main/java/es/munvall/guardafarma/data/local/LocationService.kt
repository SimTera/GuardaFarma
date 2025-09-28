package es.munvall.guardafarma.data.local

import android.content.Context
import es.munvall.guardafarma.data.model.LocationModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

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