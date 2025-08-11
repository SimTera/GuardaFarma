package com.example.guardafarma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guardafarma.ui.theme.GuardaFarmaTheme
import com.example.guardafarma.ui.viewmodel.FarmaciaViewModel
import com.example.guardafarma.ui.viewmodel.GuardiaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMapClick: () -> Unit = {},
    onBackFromMap: () -> Unit = {},
    farmaciaViewModel: FarmaciaViewModel = hiltViewModel(),
    guardiaViewModel: GuardiaViewModel = hiltViewModel()
) {
    val farmacias by farmaciaViewModel.farmacias.collectAsState()
    val farmaciaDeHoy by guardiaViewModel.farmaciaDeHoy.collectAsState()

    // Cargar datos al iniciar
    LaunchedEffect(Unit) {
        farmaciaViewModel.cargarDatos()
        guardiaViewModel.cargarGuardias()
    }

    // Actualizar farmacia de hoy cuando cambien los datos
    LaunchedEffect(farmacias) {
        if (farmacias.isNotEmpty()) {
            guardiaViewModel.obtenerFarmaciaDeHoy(farmacias)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "GuardaFarma",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // Título principal
            Text(
                text = "Encuentra tu farmacia de guardia",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            // Card con información de la farmacia de hoy
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Farmacia de Guardia Hoy",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (farmaciaDeHoy != null) {
                        Text(
                            text = farmaciaDeHoy!!.nombre,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = farmaciaDeHoy!!.direccion,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (farmaciaDeHoy!!.telefono.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tel: ${farmaciaDeHoy!!.telefono}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Text(
                            text = "Cargando información...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón principal para ver mapa
            Button(
                onClick = onMapClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ver Mapa de Farmacias",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Botón para refrescar datos
            OutlinedButton(
                onClick = {
                    guardiaViewModel.cargarGuardias()
                    farmaciaViewModel.cargarDatos()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Actualizar Datos")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Mensaje aleatorio (si lo implementas)
            Text(
                text = "💊 Encuentra siempre la farmacia más cercana",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GuardaFarmaTheme {
        // Versión SIMPLE sin ViewModels para que funcione el preview
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "GuardaFarma",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { }, // Vacío para el preview
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Mapa de Farmacias")
            }
        }
    }
}