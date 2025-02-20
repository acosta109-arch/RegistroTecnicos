package ucne.edu.registrotecnicos.data.remote.sistemas

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ucne.edu.registrotecnicos.data.remote.dto.SistemaDto

interface SistemaManagerApi{
    @GET("api/Sistemas")
    suspend fun getSistemas(): List<SistemaDto>

    @GET("api/Sistemas/{id}")
    suspend fun getSistema(@Path("id")id: Int): SistemaDto

    @POST("api/Sistemas")
    suspend fun saveSistema(@Body sistemaDto: SistemaDto?): SistemaDto

    @PUT("api/Sistemas/{id}")
    suspend fun actualizarSistema(
        @Path("id") sistemaId: Int,
        @Body articulo: SistemaDto
    ): SistemaDto

    @DELETE("api/Sistemas/{id}")
    suspend fun deleteSistema(@Path("id") id: Int): ResponseBody

}