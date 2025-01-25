package ucne.edu.registrotecnicos.data.repository

import kotlinx.coroutines.flow.Flow
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.MensajeEntity
import javax.inject.Inject

class MensajeRepository @Inject constructor(
    private val tecnicoDb: TecnicoDb
){
    suspend fun saveMensaje(mensaje: MensajeEntity){
        tecnicoDb.mensajeDao().save(mensaje)
    }

    suspend fun find(id: Int): MensajeEntity?{
        return tecnicoDb.mensajeDao().find(id)
    }

    suspend fun delete(mensaje: MensajeEntity){
        return tecnicoDb.mensajeDao().delete(mensaje)
    }

    suspend fun getAll(): Flow<List<MensajeEntity>>{
        return tecnicoDb.mensajeDao().getAll()
    }
}