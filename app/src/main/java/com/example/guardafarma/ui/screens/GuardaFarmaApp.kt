package com.example.guardafarma.ui.screens

import androidx.compose.runtime.*
import com.example.guardafarma.data.local.MessageService
import com.example.guardafarma.ui.viewmodel.FarmaciaViewModel
import com.example.guardafarma.ui.viewmodel.GuardiaViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import com.example.guardafarma.data.model.FarmaciaDTO

@Composable
fun GuardaFarmaApp() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    var targetFarmacia by remember { mutableStateOf<FarmaciaDTO?>(null) }

    when (currentScreen) {
        Screen.Home -> {
            HomeScreen(
                onMapClick = { currentScreen = Screen.Map },
                onNavigateToFarmacia = { farmacia ->
                    targetFarmacia = farmacia
                    currentScreen = Screen.RouteToFarmacia

                },
                onBackFromMap = { currentScreen = Screen.Home }
            )
        }
        Screen.Map -> {
            MapScreen(
                onBackClick = { currentScreen = Screen.Home }
            )
        }
        Screen.RouteToFarmacia -> {
            RouteToFarmaciaScreen(
                farmacia = targetFarmacia!!,
                onBackClick = { currentScreen = Screen.Home }
            )
        }
    }
}