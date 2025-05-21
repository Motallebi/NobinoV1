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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.AudioSubtitle
import com.smcdeveloper.nobinoapp.ui.theme.ageSelectedButton
import com.smcdeveloper.nobinoapp.util.Constants.FILTER_AUDIO_SUBTITLE
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel

@Composable
fun FilterAudioSelectionSheet(
    title: String = "انتخاب زبان صوت",
    //  audioSubtitles: List<AudioSubtitle>,
    selectedAudioIds: Set<String>,
    onAudioSelected: (AudioSubtitle, Boolean) -> Unit,
    onClose: () -> Unit,
    filterViewModel: FilterViewModel


) {


     val checkBoxState by filterViewModel.audioCheckBoxStates.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = FILTER_AUDIO_SUBTITLE.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Header with Close Button


        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = {
                Icon(painterResource(R.drawable.bottom_nav_serach_not_selected), contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.colors(

                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.ageSelectedButton,
                unfocusedContainerColor = MaterialTheme.colorScheme.ageSelectedButton,
                unfocusedIndicatorColor = Color.Transparent








            )



        )












        // 🔴 List of Selectable Audio Options
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredItems.chunked(2)) { rowItems ->

                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { audio ->
                        SelectionCheckboxItem2(
                            text = audio.name, // ✅ Show localized name
                            isSelected = checkBoxState[audio.id] ?: false,

                            //audio.id in selectedAudioIds, // ✅ Check by ID
                            onSelected = { isSelected ->

                                filterViewModel.updateAudioCheckBoxState(audio.id,isSelected)
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
