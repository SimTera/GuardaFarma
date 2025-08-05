package com.example.guardafarma.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectionsResponse(
    val routes: List<Route>
)

@Serializable
data class Route(
    @SerialName("overview_polyline")
    val overviewPolyline: OverviewPolyline
)

@Serializable
data class OverviewPolyline(
    val points: String
)