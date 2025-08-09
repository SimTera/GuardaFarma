package com.example.guardafarma.domain.repository

import android.content.Context
//import androidx.viewbinding.BuildConfig
import com.example.guardafarma.data.network.DirectionsApiService
import com.google.android.gms.maps.model.LatLng
import com.example.guardafarma.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DirectionsRepository @Inject constructor(
    private val api: DirectionsApiService,
    @ApplicationContext private val context: Context
) {
    suspend fun getPolylinePoints(origin: LatLng, destination: LatLng): String {
        val originStr = "${origin.latitude},${origin.longitude}"
        val destStr = "${destination.latitude},${destination.longitude}"
        //val apiKey = context.getString(R.string.MAPS_API_KEY)  // esto esta en en secrets.propiertes
        val apiKey = BuildConfig.MAPS_API_KEY
        //Log.d("API_KEY", apiKey)
        return try {
            val response = api.getDirections(originStr, destStr, apiKey)
            response.routes.firstOrNull()?.overviewPolyline?.points ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}