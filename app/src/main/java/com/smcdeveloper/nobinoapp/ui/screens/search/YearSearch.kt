package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.max


@Composable
fun YearSelectionSheet(
    selectedFromYear: Int?,
    selectedToYear: Int?,
    onYearSelected: (Int, Boolean) -> Unit,
    onClose: () -> Unit
) {
    val years = (1990..2025).toList()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔴 Header with Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "انتخاب سال", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 "From" and "To" Year Fields INSIDE Year Selection Sheet
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            YearInputField(label = "از", selectedYear = selectedFromYear)
            Text(
                "تا",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            YearInputField(label = "تا", selectedYear = selectedToYear)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 Animated Year Selection List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp), // Controls visible area
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 32.dp)
            ) {
                itemsIndexed(years) { index, year ->
                    YearItem(
                        year = year,
                        isSelected = year == selectedFromYear || year == selectedToYear,
                        index = index,
                        listState = listState,
                        onYearSelected = { isFromYear -> onYearSelected(year, isFromYear) }
                    )
                }
            }
        }
    }

    // 🔴 Scroll to selected year when opened
    LaunchedEffect(Unit) {
        val defaultYear = selectedFromYear ?: 2023
        val index = years.indexOf(defaultYear)
        if (index != -1) {
            listState.animateScrollToItem(index) // 🔥 Smooth animation to selected year
        }
    }
}


@Composable
fun YearItem(
    year: Int,
    isSelected: Boolean,
    index: Int,
    listState: LazyListState,
    onYearSelected: (Boolean) -> Unit
) {
    val centerIndex = listState.firstVisibleItemIndex + 2 // Center the middle item
    val distance = abs(index - centerIndex)

    // 🔴 Scaling Effect
    val scale by animateFloatAsState(
        targetValue = max(0.8f, 1.2f - (distance * 0.1f)),
        animationSpec = tween(durationMillis = 300),
        label = "Year Scaling"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onYearSelected(index < listState.layoutInfo.totalItemsCount / 2) }
            .background(
                if (isSelected) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (16.sp * scale)),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun YearInputField(label: String, selectedYear: Int?) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // ✅ Ensures full width inside LazyColumn
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedYear?.toString() ?: label, // ✅ Show label if no year is selected
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
