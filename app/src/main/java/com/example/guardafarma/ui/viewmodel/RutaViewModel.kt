package com.example.guardafarma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.LatLng
import com.example.guardafarma.domain.repository.DirectionsRepository
import javax.inject.Inject
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@HiltViewModel
class RutaViewModel @Inject constructor(
    private val repository: DirectionsRepository
) : ViewModel() {

    private val _polylinePoints = MutableStateFlow("")
    val polylinePoints: StateFlow<String> = _polylinePoints

    fun loadRoute(origin: LatLng, destination: LatLng) {
        viewModelScope.launch {
            _polylinePoints.value = repository.getPolylinePoints(origin, destination)
        }
    }
//    fun loadRoute(origin: LatLng, destination: LatLng, mode: String = "walking") {
//        viewModelScope.launch {
//            _polylinePoints.value = repository.getPolylinePoints(origin, destination, mode)
//        }
//    }
}


//@HiltViewModel  //ahora asi parece que no gusta
//class RutaViewModel @Inject constructor(
//    private val mapService: LocationService // Este se inyecta con Hilt y contiene la lógica Directions
//) : ViewModel() {
//    private val _ruta = MutableStateFlow<List<LatLng>>(emptyList())
//    val ruta: StateFlow<List<LatLng>> = _ruta
//
//    fun calcularRuta(origen: LatLng, destino: LatLng) {
//        viewModelScope.launch {
//            try {
//                val directionsResult = LocationService.getDirections(origen, destino)
//                // Decodifica el polyline a lista de LatLng
//                val puntosRuta = PolyUtil.decode(directionsResult.encodedPolyline)
//                _ruta.value = puntosRuta
//            } catch (e: Exception) {
//                // Maneja errores (puede mostrar in UI o log)
//                _ruta.value = emptyList()
//            }
//        }
//    }
//}


//class RutaViewModel(private val mapService: MapService) : ViewModel() {
//    private val _ruta = MutableStateFlow<RouteResult?>(null)
//    val ruta: StateFlow<RouteResult?> = _ruta
//
//    private val _lookAroundScene = MutableStateFlow<StreetViewData?>(null)
//    val lookAroundScene: StateFlow<StreetViewData?> = _lookAroundScene
//
//    fun calcularRuta(hasta: LatLng) {
//        viewModelScope.launch {
//            try {
//                val rutaCalculada = mapService.getDirections(to = hasta)
//                _ruta.value = rutaCalculada
//            } catch (e: Exception) {
//                // Manejo de error
//            }
//        }
//    }
//
//    fun obtenerLookAround(para: LatLng) {
//        viewModelScope.launch {
//            val scene = mapService.getLookAroundMe(from = para)
//            _lookAroundScene.value = scene
//        }
//    }
//}
