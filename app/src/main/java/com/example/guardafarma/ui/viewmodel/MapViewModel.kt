package com.example.guardafarma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guardafarma.data.local.LocationService
import com.example.guardafarma.data.model.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona la ubicación del usuario usando un repositorio.
 */
@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationService
) : ViewModel() {

    private val _location = MutableStateFlow<LocationModel?>(null)
    val location: StateFlow<LocationModel?> = _location

    fun fetchUserLocation() {
        viewModelScope.launch {
            val loc = locationRepository.getLastKnownLocation()
            _location.value = loc
        }
    }
}

//class MapViewModelFactory(
//    private val myRepository: LocationService
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return MapViewModel(myRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

//class MapViewModel(private val locationRepository: LocationService) : ViewModel() {
//
//    private val _location = MutableStateFlow<LocationModel?>(null)
//    val location: StateFlow<LocationModel?> = _location
//
//    fun fetchUserLocation() {
//        viewModelScope.launch {
//            val loc = locationRepository.getLastKnownLocation()
//            _location.value = loc
//        }
//    }
//}