package com.example.guardafarma.domain.repository

import android.content.Context
import com.example.guardafarma.data.network.DirectionsApiService
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DirectionsRepository @Inject constructor(
    private val api: DirectionsApiService,
    @ApplicationContext private val context: Context
) {
    suspend fun getPolylinePoints(origin: LatLng, destination: LatLng): String {
        val originStr = "${origin.latitude},${origin.longitude}"
        val destStr = "${destination.latitude},${destination.longitude}"
        val apiKey = context.getString(R.string.google_maps_key) // esto esta en en secrets.propiertes

        return try {
            val response = api.getDirections(originStr, destStr, apiKey)
            response.routes.firstOrNull()?.overviewPolyline?.points ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}