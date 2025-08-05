package com.example.guardafarma.data.local

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

import com.example.guardafarma.data.model.GuardiaDTO
import com.example.guardafarma.data.model.FarmaciaDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// HE DE MODIFICAR ESTO PARA QUE SEA CON @INJECT

// Interfaz genérica para servicios de datos
interface ApiService<Response> {
    suspend fun execute(): Response
}

// Clase base abstracta para servicios que leen JSON de assets y lo decodifican
abstract class JsonService<Response>(
    private val context: Context,
    private val fileName: String,
    private val typeToken: Type
) : ApiService<Response> {

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX") // ISO8601 para fechas
        .create()

    override suspend fun execute(): Response {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return gson.fromJson(json, typeToken)
    }
}

// Servicio concreto para guardias
class GuardiaService @Inject constructor(
    @ApplicationContext context: Context
) : JsonService<List<GuardiaDTO>>(
    context,
    "Guardias.json",
    object : TypeToken<List<GuardiaDTO>>() {}.type
)

// Servicio concreto para farmacias
class FarmaciaService @Inject constructor(
    @ApplicationContext context: Context
) : JsonService<List<FarmaciaDTO>>(
    context,
    "Farmacias.json",
    object : TypeToken<List<FarmaciaDTO>>() {}.type
)

// Manejo de errores personalizado
sealed class ServiceError(message: String) : Exception(message) {
    object FileNotFound : ServiceError("El archivo no se encontró en assets.")
    class DecodingFailed(e: Throwable) : ServiceError("Error al decodificar el JSON: ${e.localizedMessage}")
}
