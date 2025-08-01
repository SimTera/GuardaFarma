package com.example.guardafarma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.guardafarma.repository.LocationService
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import com.google.maps.android.PolyUtil


@HiltViewModel
class RutaViewModel @Inject constructor(
    private val mapService: LocationService // Este se inyecta con Hilt y contiene la lógica Directions
) : ViewModel() {
    private val _ruta = MutableStateFlow<List<LatLng>>(emptyList())
    val ruta: StateFlow<List<LatLng>> = _ruta

    fun calcularRuta(origen: LatLng, destino: LatLng) {
        viewModelScope.launch {
            try {
                val directionsResult = LocationService.getDirections(origen, destino)
                // Decodifica el polyline a lista de LatLng
                val puntosRuta = PolyUtil.decode(directionsResult.encodedPolyline)
                _ruta.value = puntosRuta
            } catch (e: Exception) {
                // Maneja errores (puede mostrar in UI o log)
                _ruta.value = emptyList()
            }
        }
    }
}


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
