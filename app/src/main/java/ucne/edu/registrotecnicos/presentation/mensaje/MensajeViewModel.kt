package ucne.edu.registrotecnicos.presentation.mensaje

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.local.entity.MensajeEntity
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.repository.MensajeRepository
import ucne.edu.registrotecnicos.data.repository.TecnicoRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MensajesViewModel @Inject constructor(
    private val mensajeRepository: MensajeRepository,
    private val tecnicoRepository: TecnicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadMensajes()
    }

    private fun loadMensajes() {
        viewModelScope.launch {
            mensajeRepository.getAll().collect { mensajes ->
                _uiState.value = _uiState.value.copy(mensajes = mensajes)
            }
        }
    }

    private fun loadTecnicos() {
        viewModelScope.launch {
            tecnicoRepository.getAll().collect { tecnicos ->
                _uiState.value = _uiState.value.copy(tecnicos = tecnicos)
            }
        }
    }

    fun onDescripcionChange(newDescripcion: String) {
        _uiState.value = _uiState.value.copy(descripcion = newDescripcion)
    }

    fun onTecnicoIdChange(newTecnicoId: String) {
        _uiState.value = _uiState.value.copy(tecnicoId = newTecnicoId)
    }

    fun saveMensaje() {
        val currentState = _uiState.value
        if (currentState.tecnicoId != null && currentState.descripcion.isNotEmpty()) {
            val tecnicoIdInt = currentState.tecnicoId.toIntOrNull()

            if (tecnicoIdInt != null) {
                val fecha = currentState.fecha

                val nuevoMensaje = MensajeEntity(
                    tecnicoId = tecnicoIdInt,
                    descripcion = currentState.descripcion,
                    fecha = Date(currentState.fecha)

                )

                viewModelScope.launch {
                    mensajeRepository.saveMensaje(nuevoMensaje)
                    _uiState.value = _uiState.value.copy(
                        successMessage = "Mensaje guardado con éxito",
                        descripcion = "",
                        tecnicoId = null,
                        fecha = System.currentTimeMillis()
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(errorMessage = "ID de técnico no válido.")
            }
        } else {
            _uiState.value = _uiState.value.copy(errorMessage = "Debe llenar todos los campos.")
        }
    }

    fun nuevoMensaje() {
        _uiState.value = _uiState.value.copy(descripcion = "", tecnicoId = null, fecha = System.currentTimeMillis())
    }
}

data class UiState(
    val mensajes: List<MensajeEntity> = emptyList(),
    val tecnicos: List<TecnicoEntity> = emptyList(),
    val descripcion: String = "",
    val tecnicoId: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val fecha: Long = System.currentTimeMillis()
)

