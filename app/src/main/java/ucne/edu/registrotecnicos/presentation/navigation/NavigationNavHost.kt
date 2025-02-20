package ucne.edu.registrotecnicos.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registro_tecnicos.presentation.navigation.tecnico.EditTecnicoScreen
import edu.ucne.registro_tecnicos.presentation.navigation.ticket.EditTicketScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.ticket.TicketListScreen
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.repository.TecnicoRepository
import ucne.edu.registrotecnicos.data.repository.TicketRepository
import ucne.edu.registrotecnicos.presentation.HomeScreen
import ucne.edu.registrotecnicos.presentation.articulo.ArticuloListScreen
import ucne.edu.registrotecnicos.presentation.articulo.ArticuloScreen
import ucne.edu.registrotecnicos.presentation.mensaje.MensajeScreen
import ucne.edu.registrotecnicos.presentation.sistema.SistemaListScreen
import ucne.edu.registrotecnicos.presentation.sistema.SistemaScreen
import ucne.edu.registrotecnicos.presentation.tecnico.TecnicoScreen
import ucne.edu.registrotecnicos.presentation.tecnico.DeleteTecnicoScreen
import ucne.edu.registrotecnicos.presentation.ticket.DeleteTicketScreen
import ucne.edu.registrotecnicos.presentation.ticket.TicketScreen

@Composable
fun registro_tecnicos_tickets(tecnicoDb: TecnicoDb, navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val tecnicoList by tecnicoDb.tecnicoDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    val ticketList by tecnicoDb.ticketDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    val scope = rememberCoroutineScope()
    val tecnicoRepository = TecnicoRepository(tecnicoDb)
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            HomeScreen(
                goToTecnico = {
                    navHostController.navigate(Screen.TecnicoList)
                },
                goToTickets = {
                    navHostController.navigate(Screen.TicketList)
                },
                goToArticulos = {
                    navHostController.navigate(Screen.ArticuloList)
                },
                goToSistemas = {
                    navHostController.navigate(Screen.SistemaList)
                }
            )
        }

        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                drawerState = drawerState,
                scope = scope,
                createTecnico = {
                    navHostController.navigate(Screen.Tecnico(0))
                },
                onEditTecnico = { tecnico ->
                    navHostController.navigate(Screen.EditTecnico(tecnico))
                },
                onDeleteTecnico = { tecnico ->
                    navHostController.navigate(Screen.DeleteTecnico(tecnico))
                }
            )
        }


        composable<Screen.Tecnico> {
            val args = it.toRoute<Screen.Tecnico>()
            TecnicoScreen(
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.EditTecnico> {
            val args = it.toRoute<Screen.EditTecnico>()
            EditTecnicoScreen(
                tecnicoId = args.tecnicoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteTecnico> {
            val args = it.toRoute<Screen.DeleteTecnico>()
            DeleteTecnicoScreen(
                tecnicoId = args.tecnicoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.TicketList> {
            TicketListScreen(
                drawerState = drawerState,
                scope = scope,
                createTicket = {
                    navHostController.navigate(Screen.Ticket(0))
                },
                onEditTicket = { ticket ->
                    navHostController.navigate(Screen.EditTicket(ticket.ticketId ?: 0))
                },
                onDeleteTicket = { ticket ->
                    navHostController.navigate(Screen.DeleteTicket(ticket.ticketId ?: 0))
                }
            )
        }

        composable<Screen.Ticket> {
            val args = it.toRoute<Screen.Ticket>()
            TicketScreen(
                goBack = {
                    navHostController.navigateUp()
                },
                goToMensajeScreen = { navHostController.navigate("MensajeScreen") }
            )
        }
        composable("MensajeScreen") {
            MensajeScreen()
        }

        composable<Screen.EditTicket> {
            val args = it.toRoute<Screen.EditTicket>()
            EditTicketScreen(
                ticketId = args.ticketId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteTicket> {
            val args = it.toRoute<Screen.DeleteTicket>()
            DeleteTicketScreen(
                ticketId = args.ticketId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.ArticuloList> {
            ArticuloListScreen(
                drawerState = drawerState,
                scope = scope,
                createArticulo = {
                    navHostController.navigate(Screen.Articulo(0))
                },
                onEditArticulo = { tecnico ->
                    navHostController.navigate(Screen.EditTecnico(tecnico))
                },
                onDeleteArticulo = { tecnico ->
                    navHostController.navigate(Screen.DeleteTecnico(tecnico))
                }
            )
        }

        composable<Screen.Articulo> {
            val args = it.toRoute<Screen.Articulo>()
            ArticuloScreen(
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.SistemaList> {
            SistemaListScreen(
                drawerState = drawerState,
                scope = scope,
                createSistema = {
                    navHostController.navigate(Screen.Sistema(0))
                },
                onEditSistema = { tecnico ->
                    navHostController.navigate(Screen.EditTecnico(tecnico))
                },
                onDeleteSistema = { tecnico ->
                    navHostController.navigate(Screen.DeleteTecnico(tecnico))
                }
            )
        }

        composable<Screen.Sistema> {
            val args = it.toRoute<Screen.Sistema>()
            SistemaScreen(
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

