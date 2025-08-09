package com.example.guardafarma.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object DateUtility {
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