package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.screens.home.getAllMovies
import com.smcdeveloper.nobinoapp.ui.screens.home.getSlider
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall

@Composable
fun Search(navController: NavHostController) {

    SearchPage(
        {},

3
    )


}





@Composable
fun SearchPage(onRowClick: (String) -> Unit, filterCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Bar with Search Bar and Badge Icon
        TopBarWithBadge(filterCount)

        // Filters Section
        FilterSection(onRowClick = onRowClick)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBadge(filterCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Bar
        TextField(
            value = "",
            onValueChange = {}, // Update with state management logic
            placeholder = { Text("Search...", color = Color.Gray) },
            modifier = Modifier
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = Color.Black,
                //backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Badge Icon for Filter Count
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable { /* Open Filters or Settings */ }
        ) {
            Icon(
                imageVector = Icons.Default.FilterList, // Replace with a custom filter icon if needed
                contentDescription = "Filter Icon",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )

            // Badge
            if (filterCount > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Red, shape = CircleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filterCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.nobinoSmall,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

























@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {}, // Update with state management logic
        placeholder = { Text("Search...", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.Black,
             // = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
        }
    )
}

@Composable
fun FilterSection(onRowClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp)
    ) {
        // Filter rows
        FilterRow("Genre", onRowClick)
        FilterRow("Country", onRowClick)
        FilterRow("Year", onRowClick)
        FilterRow("Actors", onRowClick)
        FilterRow("Sorting", onRowClick)
        FilterRow("Audio", onRowClick)
        FilterRow("Subtitles", onRowClick)
    }
}

@Composable
fun FilterRow(label: String, onRowClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRowClick(label) }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}



























