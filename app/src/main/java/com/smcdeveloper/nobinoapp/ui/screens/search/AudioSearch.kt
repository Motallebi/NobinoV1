package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.data.model.AudioSubtitle

@Composable
fun FilterAudioSelectionSheet(
    title: String = "انتخاب زبان صوت",
    //  audioSubtitles: List<AudioSubtitle>,
    selectedAudioIds: Set<String>,
    onAudioSelected: (AudioSubtitle, Boolean) -> Unit,
    onClose: () -> Unit


) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Header with Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 List of Selectable Audio Options
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(audioOptions.chunked(2)) { rowItems ->

                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { audio ->
                        SelectionCheckboxItem2(
                            text = audio.name, // ✅ Show localized name
                            isSelected = audio.id in selectedAudioIds, // ✅ Check by ID
                            onSelected = { isSelected ->
                                onAudioSelected(
                                    audio,
                                    isSelected
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
