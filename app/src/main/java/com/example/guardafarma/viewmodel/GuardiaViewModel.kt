package com.example.guardafarma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.example.guardafarma.model.GuardiaDTO
import com.example.guardafarma.model.FarmaciaDTO
import com.example.guardafarma.repository.DateUtility // Esto esta bien asi
import com.example.guardafarma.repository.GuardiaService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GuardiaViewModel @Inject constructor (
    private val guardiaService : GuardiaService
) : ViewModel() {
    private val _guardias = MutableStateFlow<List<GuardiaDTO>>(emptyList())
    val guardias: StateFlow<List<GuardiaDTO>> = _guardias

    private val _farmaciaDeHoy = MutableStateFlow<FarmaciaDTO?>(null)
    val farmaciaDeHoy: StateFlow<FarmaciaDTO?> = _farmaciaDeHoy

    fun cargarGuardias() {
        viewModelScope.launch {
            try {
               // val json = context.assets.open("Guardias.json").bufferedReader().use { it.readText() }
                val lista = guardiaService.execute()
                _guardias.value = lista
            } catch (e: Exception) {
                // Manejo de error
            }
        }
    }

    fun obtenerFarmaciaDeHoy(farmacias: List<FarmaciaDTO>) {
        val fechaHoy = DateUtility().formatearFecha(DateUtility().fechaAjustada())
        val guardiaHoy = _guardias.value.firstOrNull { it.fecha == fechaHoy }
        _farmaciaDeHoy.value = guardiaHoy?.let { guardia ->
            farmacias.firstOrNull { it.id == guardia.idFarmacia }
        }
    }
}
