package com.example.guardafarma.repository

import android.content.Context
import com.example.guardafarma.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MessageService @Inject constructor (
    @ApplicationContext private val context: Context
) {
    fun mensajeAleatorio(): String {
        val mensajes: List<Int> = listOf(
            //esto va en res, values, strings
            R.string.mensaje1,
            R.string.mensaje2,
            R.string.mensaje3,
            R.string.mensaje4,
            R.string.mensaje5,
            R.string.mensaje6,
            R.string.mensaje7,
            R.string.mensaje8,
            R.string.mensaje9,
            R.string.mensaje10
        )
        return context.getString(mensajes.random())
    }

}