package ucne.edu.registrotecnicos.presentation.ticket

import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun DeleteTicketScreen(
    tecnicoDb: TecnicoDb,
    ticketId: Int,
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val ticket = remember { mutableStateOf<TicketEntity?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(ticketId) {
        val t = tecnicoDb.ticketDao().find(ticketId)
        ticket.value = t
        isLoading.value = false
    }

    val ticketEntity = ticket.value
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Text(
                text = "¿Está seguro de Eliminar el Ticket?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading.value) {
                Text(
                    text = "Cargando...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else if (ticketEntity != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Fecha",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.fecha ?: "No disponible",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = "Prioridad",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.prioridadId.toString(),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )

                        Text(
                            text = "Cliente",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.cliente,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )

                        Text(
                            text = "Asunto",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.asunto,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )

                        Text(
                            text = "Descripción",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.descripcion,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )

                        Text(
                            text = "Técnico ID",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = ticketEntity.tecnicoId.toString(),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Button(
                    onClick = {
                        scope.launch {
                            tecnicoDb.ticketDao().delete(ticketEntity)
                            mensajeExito = "Ticket Eliminado con Éxito."
                            launch {
                                kotlinx.coroutines.delay(2000)
                                goBack()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(text = "Eliminar")
                }

                Button(
                    onClick = {
                        goBack()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(text = "Volver")
                }
            } else {
                Text(
                    text = "Ticket no encontrado.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }
    }
}
