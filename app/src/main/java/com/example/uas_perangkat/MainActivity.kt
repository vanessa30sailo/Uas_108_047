package com.example.uas_perangkat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.uas_perangkat.ui.EventScreen
import com.example.uas_perangkat.ui.EventViewModel
import com.example.uas_perangkat.ui.ui.UAS_PerangkatTheme

class MainActivity : ComponentActivity() {

    // ViewModel dipanggil dari activity
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Panggil API
        eventViewModel.loadEvents()

        setContent {
            UAS_PerangkatTheme {
                EventScreen(eventViewModel)
            }
        }
    }
}
