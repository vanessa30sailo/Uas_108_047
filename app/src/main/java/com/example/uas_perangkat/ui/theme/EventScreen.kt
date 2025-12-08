package com.example.uas_perangkat.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uas_perangkat.data.Event

@Composable
fun EventScreen(viewModel: EventViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("Daftar Event", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))

        when {
            viewModel.isLoading -> {
                Text("Loading...")
            }
            viewModel.errorMessage.isNotEmpty() -> {
                Text("Error: ${viewModel.errorMessage}")
            }
            else -> {
                for (event in viewModel.eventList) {
                    EventItem(event)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = event.title, style = MaterialTheme.typography.titleMedium)
        Text(text = event.date)
        Text(text = event.location)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
fun PreviewEventScreen() {
    val vm = EventViewModel().apply {
        eventList = listOf(
            Event(
                id = 1,
                title = "Seminar Android",
                date = "2025-12-10",
                location = "Kupang",
                description = "Belajar Retrofit"
            )
        )
    }

    EventScreen(vm)
}


