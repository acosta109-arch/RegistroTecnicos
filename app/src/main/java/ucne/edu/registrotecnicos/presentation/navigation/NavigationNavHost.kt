package ucne.edu.registrotecnicos.presentation.navigation

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
import ucne.edu.registrotecnicos.presentation.tecnico.TecnicoScreen
import ucne.edu.registrotecnicos.presentation.tecnico.DeleteTecnicoScreen
import ucne.edu.registrotecnicos.presentation.ticket.DeleteTicketScreen
import ucne.edu.registrotecnicos.presentation.ticket.TicketScreen

@Composable
fun registro_tecnicos_tickets(tecnicoDb: TecnicoDb, navHostController: NavHostController) {
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
                }
            )
        }

        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                tecnicoList,
                createTecnicos = {
                    navHostController.navigate(Screen.Tecnico(0))
                },
                editTecnicos = { tecnico ->
                    navHostController.navigate(Screen.EditTecnico(tecnico.tecnicoId ?: 0))
                },
                deleteTecnicos = { tecnico ->
                    navHostController.navigate(Screen.DeleteTecnico(tecnico.tecnicoId ?: 0))
                }
            )
        }

        composable<Screen.Tecnico> {
            TecnicoScreen(
                tecnicoDb = tecnicoDb,
                tecnicoRepository = tecnicoRepository,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.EditTecnico> {
            val args = it.toRoute<Screen.EditTecnico>()
            EditTecnicoScreen(
                tecnicoDb = tecnicoDb,
                tecnicoId = args.tecnicoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteTecnico> {
            val args = it.toRoute<Screen.DeleteTecnico>()
            DeleteTecnicoScreen(
                tecnicoDb = tecnicoDb,
                tecnicoId = args.tecnicoId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.TicketList> {
            TicketListScreen(
                ticketList = ticketList,
                tecnicoList = tecnicoList,
                createTicket = {
                    navHostController.navigate(Screen.Ticket(0))
                },
                editTicket = { ticket ->
                    navHostController.navigate(Screen.EditTicket(ticket.ticketId ?: 0))
                },
                deleteTicket = { ticket ->
                    navHostController.navigate(Screen.DeleteTicket(ticket.ticketId ?: 0))
                }
            )
        }

        val ticketRepository = TicketRepository(tecnicoDb)
        composable<Screen.Ticket> {
            TicketScreen(
                tecnicoDb = tecnicoDb,
                tecnicoRepository = tecnicoRepository,
                ticketRepository = ticketRepository,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.EditTicket> {
            val args = it.toRoute<Screen.EditTicket>()
            EditTicketScreen(
                tecnicoDb = tecnicoDb,
                ticketId = args.ticketId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }

        composable<Screen.DeleteTicket> {
            val args = it.toRoute<Screen.DeleteTicket>()
            DeleteTicketScreen(
                tecnicoDb = tecnicoDb,
                ticketId = args.ticketId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

