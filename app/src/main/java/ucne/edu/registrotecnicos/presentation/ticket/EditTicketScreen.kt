@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registro_tecnicos.presentation.navigation.ticket

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity
import ucne.edu.registrotecnicos.presentation.ticket.TicketViewModel

@Composable
fun EditTicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBack: () -> Unit
) {
    LaunchedEffect(ticketId) {
        viewModel.selectTicket(ticketId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EditTicketBodyScreen(
        uiState = uiState,
        onFechaChange = viewModel::onFechaChange,
        onPrioridadChange = { newValue: String ->
            val prioridadId = newValue.toIntOrNull()
            viewModel.onPrioridadIdChange(prioridadId)
        },
        onClienteChange = viewModel::onClienteChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onTecnicoIdChange = viewModel::onTecnicoIdChange,
        save = viewModel::saveTicket,
        goBack = goBack
    )
}

@Composable
fun EditTicketBodyScreen(
    uiState: TicketViewModel.UiState,
    onFechaChange: (String) -> Unit,
    onPrioridadChange: (String) -> Unit,
    onClienteChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onTecnicoIdChange: (Int) -> Unit,
    save: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Editar Ticket",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = save) {
                        Icon(Icons.Filled.Check, contentDescription = "Guardar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Fecha") },
                value = uiState.fecha ?: "",
                onValueChange = onFechaChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Prioridad") },
                value = uiState.prioridadId?.toString() ?: "",
                onValueChange = onPrioridadChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Cliente") },
                value = uiState.cliente ?: "",
                onValueChange = onClienteChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Asunto") },
                value = uiState.asunto ?: "",
                onValueChange = onAsuntoChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = uiState.descripcion ?: "",
                onValueChange = onDescripcionChange
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Técnico ID") },
                value = uiState.tecnicoId?.toString() ?: "",
                onValueChange = { newValue ->
                    onTecnicoIdChange(newValue.toIntOrNull() ?: 0)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // For mensajeError
            if (!uiState.mensajeError.isNullOrEmpty()) {
                Text(
                    text = uiState.mensajeError ?: "",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

// For mensajeExito
            if (!uiState.mensajeExito.isNullOrEmpty()) {
                Text(
                    text = uiState.mensajeExito ?: "",
                    color = Color.Green,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() }
                ) {
                    Text(text = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { goBack() }
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}

