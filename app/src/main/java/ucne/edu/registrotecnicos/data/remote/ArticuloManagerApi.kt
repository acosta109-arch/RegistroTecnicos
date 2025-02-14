package ucne.edu.registrotecnicos.data.remote

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ucne.edu.registrotecnicos.data.remote.dto.ArticuloDto

interface ArticuloManagerApi{
    @GET("api/Articulos")
    suspend fun getArticulos(): List<ArticuloDto>

    @GET("api/Articulos/{id}")
    suspend fun getArticulo(@Path("id")id: Int): ArticuloDto

    @POST("api/Articulos")
    suspend fun saveArticulo(@Body articuloDto: ArticuloDto?): ArticuloDto

    @PUT("api/Articulos/{id}")
    suspend fun actualizarArticulo(
        @Path("id") articuloId: Int,
        @Body articulo: ArticuloDto
    ): ArticuloDto

    @DELETE("api/Articulos/{id}")
    suspend fun deleteArticulo(@Path("id") id: Int): ResponseBody

}