package ucne.edu.registrotecnicos.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ucne.edu.registrotecnicos.data.remote.sistemas.DataSource
import ucne.edu.registrotecnicos.data.remote.Resource
import ucne.edu.registrotecnicos.data.remote.dto.SistemaDto
import retrofit2.HttpException
import android.util.Log
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val dataSource: DataSource
) {
    fun getSistemas(): Flow<Resource<List<SistemaDto>>> = flow {
        try {
            emit(Resource.Loading())
            val sistemas = dataSource.getSistemas()
            emit(Resource.Success(sistemas))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("SistemaRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexión $errorMessage"))
        } catch (e: Exception) {
            Log.e("SistemaRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    suspend fun update(id: Int, sistemaDto: SistemaDto) =
        dataSource.actualizarSistema(id, sistemaDto)

    suspend fun find(id: Int) = dataSource.getSistema(id)

    suspend fun save(sistemaDto: SistemaDto) = dataSource.saveSistema(sistemaDto)

    suspend fun delete(id: Int) = dataSource.deleteSistema(id)
}
