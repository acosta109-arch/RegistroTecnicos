package edu.ucne.registro_tecnicos.presentation.navigation.ticket

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity

@Composable
fun EditTicketScreen(
    tecnicoDb: TecnicoDb,
    ticketId: Int,
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var ticket by remember { mutableStateOf<TicketEntity?>(null) }
    var fecha by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tecnicoId by remember { mutableStateOf(0) }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    LaunchedEffect(ticketId) {
        val loadedTicket = tecnicoDb.ticketDao().find(ticketId)
        ticket = loadedTicket
        if (loadedTicket != null) {
            fecha = loadedTicket.fecha ?: ""
            prioridad = loadedTicket.prioridadId?.toString() ?: ""
            cliente = loadedTicket.cliente ?: ""
            asunto = loadedTicket.asunto ?: ""
            descripcion = loadedTicket.descripcion
            tecnicoId = loadedTicket.tecnicoId
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = "Editar Ticket",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Fecha") },
                value = fecha,
                onValueChange = {
                    fecha = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Prioridad") },
                value = prioridad,
                onValueChange = {
                    prioridad = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Cliente") },
                value = cliente,
                onValueChange = {
                    cliente = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Asunto") },
                value = asunto,
                onValueChange = {
                    asunto = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descripción") },
                value = descripcion,
                onValueChange = {
                    descripcion = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Técnico ID") },
                value = tecnicoId.toString(),
                onValueChange = {
                    tecnicoId = it.toIntOrNull() ?: 0
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (mensajeExito.isNotEmpty()) {
                Text(
                    text = mensajeExito,
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
                    onClick = {
                        scope.launch {
                            if (fecha.isBlank() || prioridad.isBlank() || cliente.isBlank() || asunto.isBlank() || descripcion.isBlank() || tecnicoId == 0) {
                                mensajeError = "Debe completar todos los campos."
                                return@launch
                            }

                            val prioridadValor = prioridad.toIntOrNull()
                            if (prioridadValor == null || prioridadValor <= 0) {
                                mensajeError = "La prioridad debe ser un número mayor que 0."
                                return@launch
                            }

                            val updatedTicket = TicketEntity(
                                ticketId = ticketId,
                                tecnicoId = tecnicoId,
                                descripcion = descripcion,
                                prioridadId = prioridadValor,
                                cliente = cliente,
                                asunto = asunto,
                                fecha = fecha
                            )

                            tecnicoDb.ticketDao().save(updatedTicket)

                            mensajeExito = "Ticket actualizado con éxito."

                            kotlinx.coroutines.delay(2000)
                            goBack()
                        }
                    }
                ) {
                    Text(text = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        goBack()
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}


