package com.example.plantmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var moisturePercent by remember { mutableStateOf<Int?>(null) }
            var moistureRaw by remember { mutableStateOf<Int?>(null) }
            var status by remember { mutableStateOf("Connecting to cloud...") }

            LaunchedEffect(Unit) {
                while (true) {
                    try {
                        withContext(Dispatchers.IO) {
                            val url = "https://api.thingspeak.com/channels/3211478/feeds/last.json"
                            val response = URL(url).readText()
                            val json = JSONObject(response)

                            moisturePercent = json.getString("field1").toIntOrNull()
                            moistureRaw = json.getString("field2").toIntOrNull()
                        }
                        status = "Connected to cloud ☁️"
                    } catch (e: Exception) {
                        status = "Error: ${e.message}"
                    }
                    delay(5000)
                }
            }

            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🌱 Plant Monitor",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = status,
                            color = if (status.contains("Connected")) Color.Green else Color.Gray
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        if (moisturePercent != null) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF5F5F5)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$moisturePercent%",
                                        fontSize = 80.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4CAF50)
                                    )
                                    Text(
                                        text = "Moisture Level",
                                        fontSize = 20.sp,
                                        color = Color.Gray
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(text = "Raw: $moistureRaw")

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "📡 Data from ThingSpeak Cloud",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}