package ucne.edu.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.repository.TecnicoRepository


@Composable
fun TecnicoScreen(
    tecnicoDb: TecnicoDb,
    goBack: ()-> Unit,
    tecnicoRepository: TecnicoRepository
) {
    var nombres by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
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
                            label = { Text(text = "Nombres") },
                            value = nombres,
                            onValueChange = { nombres = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Sueldo") },
                            value = sueldo,
                            onValueChange = { sueldo = it },
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
                                    if (nombres.isBlank()) {
                                        errorMessage = "Nombres vacíos."
                                    } else {
                                        try {
                                            val sueldoDouble = sueldo.toDouble()
                                            scope.launch {
                                                tecnicoRepository.saveTecnico(
                                                    TecnicoEntity(
                                                        nombres = nombres,
                                                        sueldo = sueldoDouble
                                                    )
                                                )
                                                nombres = ""
                                                sueldo = ""
                                            }
                                        } catch (e: NumberFormatException) {
                                            errorMessage = "El sueldo debe ser un número válido."
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

