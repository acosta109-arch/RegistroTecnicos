package ucne.edu.registrotecnicos.presentation.sistema

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ucne.edu.registrotecnicos.data.remote.Resource
import ucne.edu.registrotecnicos.data.remote.dto.SistemaDto
import ucne.edu.registrotecnicos.data.repository.SistemaRepository
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SistemaUiState())
    val uiState = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        getSistemas()
    }

    fun save() {
        viewModelScope.launch {
            if (isValid()) {
                sistemaRepository.save(_uiState.value.toEntity())
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            sistemaRepository.delete(id)
        }
    }

    fun update() {
        viewModelScope.launch {
            sistemaRepository.update(
                _uiState.value.sistemaId, SistemaDto(
                    sistemaId = _uiState.value.sistemaId,
                    nombre = _uiState.value.nombre
                )
            )
        }
    }

    fun new() {
        _uiState.value = SistemaUiState()
    }

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(
                nombre = nombre,
                errorMessage = if (nombre.isBlank()) "Debes rellenar el campo Nombre"
                else null
            )
        }
    }

    fun find(sistemaId: Int) {
        viewModelScope.launch {
            if (sistemaId > 0) {
                val sistemaDto = sistemaRepository.find(sistemaId)
                if (sistemaDto.sistemaId != 0) {
                    _uiState.update {
                        it.copy(
                            sistemaId = sistemaDto.sistemaId,
                            nombre = sistemaDto.nombre
                        )
                    }
                }
            }
        }
    }

    fun getSistemas() {
        viewModelScope.launch {
            sistemaRepository.getSistemas().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                sistemas = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return uiState.value.nombre.isNotBlank()
    }
}

fun SistemaUiState.toEntity() = SistemaDto(
    sistemaId = sistemaId,
    nombre = nombre
)

data class SistemaUiState(
    val sistemaId: Int = 0,
    val nombre: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val sistemas: List<SistemaDto> = emptyList()
)
