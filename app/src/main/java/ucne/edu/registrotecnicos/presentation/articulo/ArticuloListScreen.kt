package ucne.edu.registrotecnicos.presentation.articulo

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import ucne.edu.registrotecnicos.data.remote.dto.ArticuloDto

@Composable
fun ArticuloListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: ArticuloViewModel = hiltViewModel(),
    createArticulo: () -> Unit,
    onEditArticulo: (Int) -> Unit,
    onDeleteArticulo: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var lastArticleCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getArticulos()
    }

    LaunchedEffect(uiState.articulos) {
        if (uiState.articulos.size > lastArticleCount) {
            Toast.makeText(context, "Nuevo artículo: ${uiState.articulos.lastOrNull()?.descripcion}", Toast.LENGTH_LONG).show()
        }
        lastArticleCount = uiState.articulos.size
    }

    ArticuloListBodyScreen(
        drawerState = drawerState,
        scope = scope,
        uiState = uiState,
        createArticulo = createArticulo,
        onEditArticulo = onEditArticulo,
        onDeleteArticulo = onDeleteArticulo,
        reloadArticulos = { viewModel.getArticulos() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticuloListBodyScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: ArticuloUiState,
    createArticulo: () -> Unit,
    onEditArticulo: (Int) -> Unit,
    onDeleteArticulo: (Int) -> Unit,
    reloadArticulos: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredArticulos = uiState.articulos.filter {
        it.descripcion.contains(searchQuery, ignoreCase = true) ||
                it.articuloId.toString().contains(searchQuery)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lista de Artículos",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Image(
                            painter = painterResource(id = R.drawable.articulos),
                            contentDescription = "Ir al menú",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        },

                floatingActionButton = {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                FloatingActionButton(
                    onClick = reloadArticulos,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-16).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Recargar Artículos")
                }

                FloatingActionButton(
                    onClick = createArticulo,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-80).dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Artículo")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                SearchFilter(searchQuery) { query -> searchQuery = query }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredArticulos) { articulo ->
                        ArticuloRow(articulo, onEditArticulo, onDeleteArticulo)
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
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
        label = { Text("Buscar articulo") },
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
fun ArticuloRow(
    articulo: ArticuloDto,
    onEditArticulo: (Int) -> Unit,
    onDeleteArticulo: (Int) -> Unit
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
                    text = "ArtículoId: ${articulo.articuloId}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Descripción: ${articulo.descripcion}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "Costo: ${articulo.costo}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "Ganancia: ${articulo.ganancia}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "Precio: ${articulo.precio}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }


        }
    }
}
