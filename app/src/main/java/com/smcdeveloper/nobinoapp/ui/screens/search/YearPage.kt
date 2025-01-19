package com.smcdeveloper.nobinoapp.ui.screens.search


import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel



import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.unit.sp








@Composable
fun YearPage(
    viewModel: FilterViewModel,
    onClose: () -> Unit
) {
    // State for selected start and end years
    var startYear by remember { mutableStateOf<String?>(null) }
    var endYear by remember { mutableStateOf<String?>(null) }

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
                text = "سال ساخت",
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

        // Input Row for Start and End Years
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Start Year Section
            Text(
                text = "از تاریخ",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            YearInputField(
                selectedYear = startYear,
                onSelectYear = { startYear = it },
                onClearYear = { startYear = null },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp)) // Space between fields

            // End Year Section
            Text(
                text = "تا",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            YearInputField(
                selectedYear = endYear,
                onSelectYear = { endYear = it },
                onClearYear = { endYear = null },
                modifier = Modifier.weight(1f)
            )
        }

        // Scrollable Year List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            val years = (2000..2025).map { it.toString() }
            items(years) { year ->
                YearItem(
                    year = year,
                    isSelected = year == startYear || year == endYear,
                    onSelect = {
                        if (startYear == null) {
                            startYear = it
                        } else {
                            endYear = it
                        }
                    }
                )
            }
        }

        // Save Button
        Button(
            onClick = {
                val selectedYears = listOfNotNull(startYear, endYear).toSet()
                viewModel.updateYears(selectedYears)
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

@Composable
fun YearInputField(
    selectedYear: String?,
    onSelectYear: (String) -> Unit,
    onClearYear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.DarkGray, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display selected year or placeholder
        Text(
            text = selectedYear ?: "انتخاب کنید",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    // Optional: Handle clicks for custom interaction
                },
            style = MaterialTheme.typography.body1
        )
        // Clear Button
        if (selectedYear != null) {
            IconButton(onClick = onClearYear) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun YearItem(
    year: String,
    isSelected: Boolean,
    onSelect: (String) -> Unit
) {
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)
    val textColor by animateColorAsState(if (isSelected) Color.White else Color.Gray)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onSelect(year) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = year,
            color = textColor,
            style = MaterialTheme.typography.body1
        )
    }
}
