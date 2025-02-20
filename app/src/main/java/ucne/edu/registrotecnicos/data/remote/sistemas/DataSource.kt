package ucne.edu.registrotecnicos.data.remote.sistemas

import ucne.edu.registrotecnicos.data.remote.dto.SistemaDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val sistemaManagerApi: SistemaManagerApi
){
    suspend fun getSistemas() = sistemaManagerApi.getSistemas()

    suspend fun getSistema(id: Int) = sistemaManagerApi.getSistema(id)

    suspend fun saveSistema(sistemaDto: SistemaDto) = sistemaManagerApi.saveSistema(sistemaDto)

    suspend fun actualizarSistema(id: Int, sistemaDto: SistemaDto) = sistemaManagerApi.actualizarSistema(id, sistemaDto)

    suspend fun deleteSistema(id: Int) = sistemaManagerApi.deleteSistema(id)
}