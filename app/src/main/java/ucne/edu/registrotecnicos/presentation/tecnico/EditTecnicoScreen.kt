package edu.ucne.registro_tecnicos.presentation.navigation.tecnico

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
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity

@Composable
fun EditTecnicoScreen(
    tecnicoDb: TecnicoDb,
    tecnicoId: Int,
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var tecnico by remember { mutableStateOf<TecnicoEntity?>(null) }
    var nombres by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    LaunchedEffect(tecnicoId) {
        val loadedTecnico = tecnicoDb.tecnicoDao().find(tecnicoId)
        tecnico = loadedTecnico
        if (loadedTecnico != null) {
            nombres = loadedTecnico.nombres
            sueldo = loadedTecnico.sueldo?.toString() ?: ""
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = "Editar Técnico",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
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
                label = { Text(text = "Nombres") },
                value = nombres,
                onValueChange = {
                    nombres = it
                    mensajeError = ""
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Sueldo") },
                value = sueldo,
                onValueChange = {
                    sueldo = it
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
                            if (nombres.isBlank() || sueldo.isBlank()) {
                                mensajeError = "Debe completar todos los campos."
                                return@launch
                            }

                            val sueldoValor = sueldo.toDoubleOrNull()
                            if (sueldoValor == null || sueldoValor <= 0) {
                                mensajeError = "El sueldo debe ser un número mayor que 0."
                                return@launch
                            }

                            val updatedTecnico = TecnicoEntity(
                                tecnicoId = tecnicoId,
                                nombres = nombres,
                                sueldo = sueldoValor
                            )
                            tecnicoDb.tecnicoDao().save(updatedTecnico)

                            mensajeExito = "Técnico actualizado con éxito."

                            launch {
                                kotlinx.coroutines.delay(2000)
                                goBack()
                            }
                        }
                    }
                ) {
                    Text(text = "Modificar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        goBack()
                    }
                ) {
                    Text(text = "Volver")
                }
            }
        }
    }
}
