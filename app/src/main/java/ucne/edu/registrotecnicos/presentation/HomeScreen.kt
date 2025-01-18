package ucne.edu.registrotecnicos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    goToTecnico: () -> Unit,
    goToTickets: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "¡Bienvenido/a!",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Seleccione una",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(onClick = goToTecnico),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4CAF50)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Técnico",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Técnicos",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(onClick = goToTickets),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4CAF50)
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Tickets",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Tickets",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
    }
}
