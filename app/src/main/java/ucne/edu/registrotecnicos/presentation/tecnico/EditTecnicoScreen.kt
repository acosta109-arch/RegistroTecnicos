package edu.ucne.registro_tecnicos.presentation.navigation.tecnico

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.presentation.tecnico.TecnicoViewModel

@Composable
fun EditTecnicoScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    tecnicoId: Int,
    goBack: () -> Unit
) {
    LaunchedEffect(tecnicoId) {
        viewModel.selectTecnico(tecnicoId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditTecnicoBodyScreen(
        uiState = uiState,
        onNombresChange = viewModel::onNombresChange,
        onSueldoChange = viewModel::onSueldoChange,
        save = viewModel::saveTecnico,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTecnicoBodyScreen(
    uiState: TecnicoViewModel.UiState,
    onNombresChange: (String) -> Unit,
    onSueldoChange: (String) -> Unit,
    save: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Editar Técnico",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
                ),
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
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
                label = { Text(text = "Nombres") },
                value = uiState.nombres,
                onValueChange = onNombresChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Sueldo") },
                value = uiState.sueldo?.toString() ?: "",
                onValueChange = { input ->
                    try {
                        onSueldoChange(input.toDoubleOrNull()?.toString() ?: "")
                    } catch (e: NumberFormatException) {
                        onSueldoChange("")
                    }
                },
                isError = uiState.sueldo == null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() }
                ) {
                    Text(text = "Guardar")
                    Icon(Icons.Filled.Add, contentDescription = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { goBack() }
                ) {
                    Text(text = "Cancelar")
                    Icon(Icons.Filled.Refresh, contentDescription = "Cancelar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            uiState.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
