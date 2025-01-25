package ucne.edu.registrotecnicos.data.repository

import kotlinx.coroutines.flow.Flow
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity
import javax.inject.Inject

class TecnicoRepository @Inject constructor(
    private val tecnicoDb: TecnicoDb
) {
    suspend fun saveTecnico(tecnico: TecnicoEntity){
        tecnicoDb.tecnicoDao().save(tecnico)
    }

    suspend fun find(id: Int): TecnicoEntity?{
        return tecnicoDb.tecnicoDao().find(id)
    }

    suspend fun delete(tecnico: TecnicoEntity){
        return tecnicoDb.tecnicoDao().delete(tecnico)
    }

    fun getAll(): Flow<List<TecnicoEntity>>{
        return tecnicoDb.tecnicoDao().getAll()
    }

}
