package com.example.guardafarma.model
import com.google.android.gms.maps.model.LatLng

data class FarmaciaDTO(
    val id: Int,
    val nombre: String,
    val nombreComercial: String,
    val direccion: String,
    val telefono: String,
    val latitude: Double,
    val longitude: Double

) {
    val coordenadas: LatLng //es una propiedad calculada, cada vez que lo uso se crea un objeto
        get() = LatLng(latitude, longitude)
}
