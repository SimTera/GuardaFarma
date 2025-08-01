package com.example.guardafarma.repository

import java.time.*
import java.time.format.DateTimeFormatter

class DateUtility {
   private val madridZone = ZoneId.of("Europe/Madrid")

    // Versión moderna de fechaAjustada()
    fun fechaAjustada(): LocalDate {
        val ahora = LocalDateTime.now(madridZone)
        return if (ahora.hour < 9) ahora.minusDays(1).toLocalDate()
        else ahora.toLocalDate()
    }

    // Formateo de fechas
    fun formatearFecha(date: LocalDate): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}