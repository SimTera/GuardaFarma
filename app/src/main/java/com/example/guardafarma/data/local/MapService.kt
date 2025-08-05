package com.example.guardafarma.data.local
//
//import android.content.Context
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//
//class MapService @Inject constructor(
//    @ApplicationContext private val contex: Context
//) {
//    private val fusedLocationClient = LocationService.getFusedLocationProviderClient(context)
//
//    // Obtener ubicación actual
//    suspend fun getUserLocation(): Location? = suspendCoroutine { cont ->
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location -> cont.resume(location) }
//            .addOnFailureListener { cont.resume(null) }
//    }
//    // Para calcular rutas: debes llamar a la API REST de Google Directions
//    // y parsear la respuesta (esto requiere clave de API y llamada HTTP)
//    // Para "Look Around": usa StreetViewPanoramaView en el layout y setea la posición
//}