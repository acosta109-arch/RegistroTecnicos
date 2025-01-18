package ucne.edu.registrotecnicos.presentation.ticket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity
import ucne.edu.registrotecnicos.data.repository.TecnicoRepository
import ucne.edu.registrotecnicos.data.repository.TicketRepository

@Composable
fun TicketScreen(
    tecnicoDb: TecnicoDb,
    goBack: () -> Unit,
    tecnicoRepository: TecnicoRepository,
    ticketRepository: TicketRepository
) {
    var fecha by remember { mutableStateOf("") }
    var prioridadId by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tecnicoId by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    val tecnicosList = tecnicoRepository.getAll().collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Añadir nuevo ticket",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            label = { Text(text = "Fecha") },
                            value = fecha,
                            onValueChange = { fecha = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Prioridad ID") },
                            value = prioridadId,
                            onValueChange = { prioridadId = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Cliente") },
                            value = cliente,
                            onValueChange = { cliente = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Asunto") },
                            value = asunto,
                            onValueChange = { asunto = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Descripción") },
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            label = { Text(text = "Técnico") },
                            value = tecnicoId,
                            onValueChange = { tecnicoId = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.padding(2.dp))
                        errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo"
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (fecha.isBlank() || prioridadId.isBlank() || cliente.isBlank() || asunto.isBlank() || descripcion.isBlank() || tecnicoId.isBlank()) {
                                        errorMessage = "Todos los campos son obligatorios."
                                    } else {
                                        try {
                                            val prioridad = prioridadId.toInt()
                                            val tecnicoIdInt = tecnicoId.toInt()
                                            scope.launch {
                                                ticketRepository.saveTicket(
                                                    TicketEntity(
                                                        fecha = fecha,
                                                        prioridadId = prioridad,
                                                        cliente = cliente,
                                                        asunto = asunto,
                                                        descripcion = descripcion,
                                                        tecnicoId = tecnicoIdInt
                                                    )
                                                )
                                                fecha = ""
                                                prioridadId = ""
                                                cliente = ""
                                                asunto = ""
                                                descripcion = ""
                                                tecnicoId = ""
                                            }
                                        } catch (e: NumberFormatException) {
                                            errorMessage = "La prioridad y el Técnico deben ser números válidos."
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Guardar"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
            }
        }
    }
}