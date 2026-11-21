package com.rafayet.oneuiisland.shizuku

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rikka.shizuku.Shizuku

@Composable
fun ShizukuConnectCard() {
    var connected by remember { mutableStateOf(false) }
    var permissionGranted by remember { mutableStateOf(false) }

    fun refreshStatus() {
        connected = Shizuku.pingBinder()
        permissionGranted = connected &&
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
    }

    DisposableEffect(Unit) {
        val listener = object : Shizuku.OnRequestPermissionResultListener {
            override fun onRequestPermissionResult(
                requestCode: Int,
                grantResult: Int
            ) {
                if (requestCode == 7101) {
                    refreshStatus()
                }
            }
        }

        Shizuku.addRequestPermissionResultListener(listener)

        onDispose {
            Shizuku.removeRequestPermissionResultListener(listener)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Shizuku Connection", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                when {
                    !connected -> "Shizuku Server: Not Connected"
                    permissionGranted -> "Permission: Granted"
                    else -> "Permission: Not Granted"
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    refreshStatus()

                    if (connected && !permissionGranted) {
                        Shizuku.requestPermission(7101)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Connect / Request Permission")
            }

            OutlinedButton(
                onClick = { refreshStatus() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Refresh Status")
            }
        }
    }
}
