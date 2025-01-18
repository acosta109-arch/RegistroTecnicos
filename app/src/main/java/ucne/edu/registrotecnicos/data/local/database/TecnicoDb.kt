package ucne.edu.registrotecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ucne.edu.registrotecnicos.data.local.dao.TecnicoDao
import ucne.edu.registrotecnicos.data.local.dao.TicketDao
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity

@Database(
    entities = [
        TecnicoEntity::class,
        TicketEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TecnicoDb : RoomDatabase() {
    abstract fun tecnicoDao(): TecnicoDao
    abstract fun ticketDao(): TicketDao
}