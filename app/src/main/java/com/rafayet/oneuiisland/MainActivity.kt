package com.rafayet.oneuiisland

import android.content.Intent
import com.rafayet.oneuiisland.shizuku.ShizukuConnectCard
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafayet.oneuiisland.island.IslandService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF8AB4F8),
                    background = Color(0xFF080B12),
                    surface = Color(0xFF151A24)
                )
            ) {
                HomeScreen(
                    onStartIsland = { startIsland() },
                    onStopIsland = { stopIsland() }
                )
            }
        }
    }

    private fun startIsland() {
        if (Settings.canDrawOverlays(this)) {
            startService(Intent(this, IslandService::class.java))
        } else {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }

    private fun stopIsland() {
        stopService(Intent(this, IslandService::class.java))
    }
}

@Composable
fun HomeScreen(
    onStartIsland: () -> Unit,
    onStopIsland: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF080B12)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "OneUI Island",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Samsung Dynamic Experience",
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(45.dp))

            Box(
                modifier = Modifier
                    .width(190.dp)
                    .height(48.dp)
                    .background(Color.Black, RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OneUI Island",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = onStartIsland,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Island")
            }

            OutlinedButton(
                onClick = onStopIsland,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Stop Island")
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Island Features",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            FeatureCard("🔋", "Charging Animation")
            FeatureCard("🎵", "Music Player")
            FeatureCard("📞", "Incoming Calls")
            FeatureCard("🔔", "Notifications")
            FeatureCard("⚙️", "Island Settings")
        }
    }
}

@Composable
fun FeatureCard(
    emoji: String,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(
                Color(0xFF171E2B),
                RoundedCornerShape(16.dp)
            )
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 23.sp)

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
