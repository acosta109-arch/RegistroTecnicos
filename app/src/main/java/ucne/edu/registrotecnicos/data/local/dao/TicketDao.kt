package ucne.edu.registrotecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity

@Dao
interface TicketDao {
    @Upsert
    suspend fun save(ticket: TicketEntity)

    @Query(
        """
            SELECT *
            FROM Tickets
            WHERE ticketId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int?): TicketEntity?

    @Delete
    suspend fun delete(tecnico: TicketEntity)

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>


}