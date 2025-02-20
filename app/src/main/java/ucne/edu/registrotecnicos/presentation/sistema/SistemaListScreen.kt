package ucne.edu.registrotecnicos.presentation.sistema

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.remote.dto.SistemaDto

@Composable
fun SistemaListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SistemaViewModel = hiltViewModel(),
    createSistema: () -> Unit,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var lastSistemaCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getSistemas()
    }

    LaunchedEffect(uiState.sistemas) {
        if (uiState.sistemas.size > lastSistemaCount) {
            Toast.makeText(context, "Nuevo sistema: ${uiState.sistemas.lastOrNull()?.nombre}", Toast.LENGTH_LONG).show()
        }
        lastSistemaCount = uiState.sistemas.size
    }

    SistemaListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        createSistema = createSistema,
        onEditSistema = onEditSistema,
        onDeleteSistema = onDeleteSistema,
        reloadSistemas = { viewModel.getSistemas() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: SistemaUiState,
    createSistema: () -> Unit,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit,
    reloadSistemas: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredSistemas = uiState.sistemas.filter {
        it.nombre.contains(searchQuery, ignoreCase = true) ||
                it.sistemaId.toString().contains(searchQuery)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Sistemas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Image(
                            painter = painterResource(id = R.drawable.sistemas),
                            contentDescription = "Ir al menú",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        },

        floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                FloatingActionButton(
                    onClick = reloadSistemas,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-16).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Recargar Sistema")
                }

                FloatingActionButton(
                    onClick = createSistema,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-80).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Sistema")
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SearchFilter(searchQuery) { query -> searchQuery = query }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredSistemas) { sistema ->
                        SistemaRow(sistema, onEditSistema, onDeleteSistema)
                    }
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilter(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        label = { Text("Buscar sistema") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        ),
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Buscar")
        }
    )
}

@Composable
fun SistemaRow(
    sistema: SistemaDto,
    onEditSistema: (Int) -> Unit,
    onDeleteSistema: (Int) -> Unit
) {
    Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column(modifier = Modifier.weight(5f)) {
                Text("SistemaId: ${sistema.sistemaId}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Nombre: ${sistema.nombre}", fontSize = 14.sp)
            }
        }
    }
}
