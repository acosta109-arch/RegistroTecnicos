package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity

@Composable
fun TecnicoListScreen(
    tecnicoList: List<TecnicoEntity>,
    createTecnicos: () -> Unit,
    editTecnicos: (TecnicoEntity) -> Unit,
    deleteTecnicos: (TecnicoEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTecnicos,
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Técnico")
            }
        },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Lista de Técnicos",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                )

                if (tecnicoList.isEmpty()) {
                    Text(
                        text = "No hay técnicos disponibles",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tecnicoList) { tecnico ->
                            TecnicoRow(tecnico, editTecnicos, deleteTecnicos)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TecnicoRow(
    tecnico: TecnicoEntity,
    onEditTecnico: (TecnicoEntity) -> Unit,
    onDeleteTecnico: (TecnicoEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tecnico.nombres,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Sueldo: ${tecnico.sueldo}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }

            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                offset = DpOffset(x = (220).dp, y = 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Editar") },
                    leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = "Editar") },
                    onClick = {
                        onEditTecnico(tecnico)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar") },
                    leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = "Eliminar") },
                    onClick = {
                        onDeleteTecnico(tecnico)
                        expanded = false
                    }
                )
            }
        }
    }
}



