package com.example.guardafarma.ui.screens

import androidx.compose.runtime.*
import com.example.guardafarma.data.local.MessageService
import com.example.guardafarma.ui.viewmodel.FarmaciaViewModel
import com.example.guardafarma.ui.viewmodel.GuardiaViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GuardaFarmaApp() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    when (currentScreen) {
        Screen.Home -> {
            HomeScreen(
                onMapClick = { currentScreen = Screen.Map },
                onBackFromMap = { currentScreen = Screen.Home }
            )
        }
        Screen.Map -> {
            MapScreen(
                onBackClick = { currentScreen = Screen.Home }
            )
        }
    }
}