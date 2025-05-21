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
import com.smcdeveloper.nobinoapp.ui.screens.demo.SelectionCheckboxItem
import com.smcdeveloper.nobinoapp.ui.theme.ageSelectedButton
import com.smcdeveloper.nobinoapp.util.Constants.FILTER_AUDIO_SUBTITLE
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel


@Composable
fun FilterSubtitleSelectionSheet(
    // audioSubtitles: List<AudioSubtitle>,
    title: String = "انتخاب زیرنویس",
    selectedSubtitleIds: Set<String>,
    onSubtitleSelected: (AudioSubtitle, Boolean) -> Unit,
    onClose: () -> Unit,
    filterViewModel: FilterViewModel
) {


      val checkState by filterViewModel.subtitleCheckBoxStates.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = FILTER_AUDIO_SUBTITLE.filter { it.name.contains(searchQuery, ignoreCase = true) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Header with Close Button
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


        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 List of Selectable Subtitle Options
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredItems.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { subtitle ->
                        SelectionCheckboxItem(
                            text = subtitle.name, // ✅ Show localized name
                            isSelected = checkState[subtitle.id] ?: false,
                            //subtitle.id in selectedSubtitleIds, // ✅ Check by ID
                            onSelected = { isSelected ->

                                filterViewModel.updateSubtitleCheckBoxState(subtitle.id,isSelected)
                                onSubtitleSelected(
                                    subtitle,
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