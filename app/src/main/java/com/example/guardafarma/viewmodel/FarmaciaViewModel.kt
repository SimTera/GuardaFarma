package com.example.guardafarma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.example.guardafarma.model.FarmaciaDTO
import com.example.guardafarma.repository.FarmaciaService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FarmaciaViewModel @Inject constructor (
    private val farmaciaService: FarmaciaService
) : ViewModel() {
    private val _farmacias = MutableStateFlow<List<FarmaciaDTO>>(emptyList())
    val farmacias: StateFlow<List<FarmaciaDTO>> = _farmacias

    fun cargarDatos() {
        viewModelScope.launch {
            try {
                // val json = context.assets.open("Farmacias.json").bufferedReader().use { it.readText() }
                val lista = farmaciaService.execute() //esto biene del tema del json y ahi aparece esto y no cargarDatos
                _farmacias.value = lista
            } catch (e: Exception) {
                // Manejo de error
            }
        }
    }

    fun buscarFarmacia(id: Int): FarmaciaDTO? {
        return _farmacias.value.firstOrNull { it.id == id }
    }
}