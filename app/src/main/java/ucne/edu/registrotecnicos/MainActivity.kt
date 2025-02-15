package ucne.edu.registrotecnicos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import ucne.edu.registrotecnicos.data.local.database.TecnicoDb
import ucne.edu.registrotecnicos.presentation.navigation.registro_tecnicos_tickets
import ucne.edu.registrotecnicos.ui.theme.RegistroTecnicosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroTecnicosTheme {
                val navHost = rememberNavController()
                val tecnicoDb = Room.databaseBuilder(
                    applicationContext,
                    TecnicoDb::class.java,
                    "TecnicoDb"
                ).build()

                registro_tecnicos_tickets(
                    navHostController = navHost,
                    tecnicoDb = tecnicoDb
                )
            }
        }
    }
}






