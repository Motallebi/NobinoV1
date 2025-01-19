package com.smcdeveloper.nobinoapp.ui.screens.search

import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp







@Composable
fun CountryPage(
    viewModel: FilterViewModel,
    onClose: () -> Unit
) {
    var countryState by remember { mutableStateOf(viewModel.filterCriteria.value.selectedCountries) }

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
                text = "Country of Origin",
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

        // Search Bar (Optional Placeholder)
        TextField(
            value = "", // Search value placeholder
            onValueChange = {},
            placeholder = { Text("Search countries...", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small),
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

        // Country List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            val countries = listOf("USA", "UK", "India", "France", "Japan", "Germany")
            items(countries) { country ->
                GenreCheckbox(
                    genre = country,
                    isSelected = country in countryState,
                    onCheckedChange = { isChecked ->
                        countryState = if (isChecked) {
                            countryState + country
                        } else {
                            countryState - country
                        }
                    }
                )
            }
        }

        // Save Button
        Button(
            onClick = {
                viewModel.updateCountries(countryState)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Save and Close")
        }
    }
}
