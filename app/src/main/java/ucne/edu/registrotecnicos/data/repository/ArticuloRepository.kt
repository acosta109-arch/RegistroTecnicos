package ucne.edu.registrotecnicos.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.registrotecnicos.data.remote.DataSource
import ucne.edu.registrotecnicos.data.remote.Resource
import ucne.edu.registrotecnicos.data.remote.dto.ArticuloDto
import javax.inject.Inject

class ArticuloRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun getArticulos(): Flow<Resource<List<ArticuloDto>>> = flow {
        try{
            emit(Resource.Loading())
            val articulos = dataSource.getArticulos()
            emit(Resource.Success(articulos))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("ArticuloRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexion $errorMessage"))
        }catch (e: Exception){
            Log.e("ArticuloRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))

        }
    }

    suspend fun update(id: Int, articuloDto: ArticuloDto) =
        dataSource.actualizarArticulo(id, articuloDto)

    suspend fun find(id: Int) = dataSource.getArticulo(id)

    suspend fun save(articuloDto: ArticuloDto) = dataSource.saveArticulo(articuloDto)

    suspend fun delete(id: Int) = dataSource.deleteArticulo(id)
}