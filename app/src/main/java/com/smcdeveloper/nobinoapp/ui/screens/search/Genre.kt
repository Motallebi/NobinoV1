package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Search
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel

@Composable
fun GenrePage(
    viewModel: FilterViewModel, // Injected ViewModel
    onClose: () -> Unit // Callback for closing the page
) {
    // State for selected genres
    var genreState by remember { mutableStateOf(viewModel.filterCriteria.value.selectedGenres) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ژانر",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        // Search Bar
        TextField(
            value = "", // Placeholder for filtering genres
            onValueChange = {},
            placeholder = { Text("... جستجو", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
            }
        )

        // Genre List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            val genres = listOf("عاشقانه", "کمدی", "معمایی", "صفحه ورزش", "فانتزی", "اجتماعی", "مهیج")
            items(genres) { genre ->
                GenreCheckbox(
                    genre = genre,
                    isSelected = genre in genreState,
                    onCheckedChange = { isChecked ->
                        genreState = if (isChecked) {
                            genreState + genre // Create a new set with the added genre
                        } else {
                            genreState - genre // Create a new set without the removed genre
                        }
                    }
                )
            }
        }

        // Save Button
        Button(
            onClick = {
                viewModel.updateGenres(genreState) // Save updated genres to ViewModel
                onClose() // Close the page
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Save and Close")
        }
    }
}

@Composable
fun GenreCheckbox(
    genre: String,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!isSelected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = genre,
            color = if (isSelected) Color.Red else Color.White,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = isSelected,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color.White
            )
        )
    }
}


