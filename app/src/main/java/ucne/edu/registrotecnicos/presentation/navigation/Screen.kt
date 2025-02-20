package ucne.edu.registrotecnicos.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object TecnicoList : Screen()

    @Serializable
    data class Tecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data class EditTecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data class DeleteTecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data object TicketList : Screen()

    @Serializable
    data class Ticket(val ticketId: Int) : Screen()

    @Serializable
    data class EditTicket(val ticketId: Int) : Screen()

    @Serializable
    data class DeleteTicket(val ticketId: Int) : Screen()

    @Serializable
    data class Mensaje(val mensajeId: Int): Screen()

    @Serializable
    data object ArticuloList: Screen()

    @Serializable
    data class Articulo(val articuloId: Int): Screen()

    @Serializable
    data object  SistemaList: Screen()

    @Serializable
    data class  Sistema(val sistemaId: Int): Screen()
}