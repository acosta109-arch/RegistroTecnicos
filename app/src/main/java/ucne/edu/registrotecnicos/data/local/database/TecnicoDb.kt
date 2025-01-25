package ucne.edu.registrotecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ucne.edu.registrotecnicos.data.local.dao.MensajeDao
import ucne.edu.registrotecnicos.data.local.dao.TecnicoDao
import ucne.edu.registrotecnicos.data.local.dao.TicketDao
import ucne.edu.registrotecnicos.data.local.entity.MensajeEntity
import ucne.edu.registrotecnicos.data.local.entity.TecnicoEntity
import ucne.edu.registrotecnicos.data.local.entity.TicketEntity

@Database(
    entities = [
        TecnicoEntity::class,
        TicketEntity::class,
        MensajeEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TecnicoDb : RoomDatabase() {
    abstract fun tecnicoDao(): TecnicoDao
    abstract fun ticketDao(): TicketDao
    abstract fun mensajeDao(): MensajeDao
}
