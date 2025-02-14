package ucne.edu.registrotecnicos.data.remote

import ucne.edu.registrotecnicos.data.remote.dto.ArticuloDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val articuloManagerApi: ArticuloManagerApi
){
    suspend fun getArticulos() = articuloManagerApi.getArticulos()

    suspend fun getArticulo(id: Int) = articuloManagerApi.getArticulo(id)

    suspend fun saveArticulo(articuloDto: ArticuloDto) = articuloManagerApi.saveArticulo(articuloDto)

    suspend fun actualizarArticulo(id: Int, articuloDto: ArticuloDto) = articuloManagerApi.actualizarArticulo(id, articuloDto)

    suspend fun deleteArticulo(id: Int) = articuloManagerApi.deleteArticulo(id)
}