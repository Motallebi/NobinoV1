package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo





















@Composable
fun FilterActorsSelectionSheet(
    title: String,
    actors: List<PersonInfo>,
    selectedActorIds: Set<String>,
    onActorSelected: (PersonInfo, Boolean) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

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

        // 🔴 Search Bar
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchQueryChanged(it) // 🔴 Fetch actors from API based on input
            },
            placeholder = { Text("جستجو بازیگر...") }, // "Search Actor..."
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 Actor List as a Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 🔴 Two columns like in your image
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(actors) { actor ->
                ActorGridItem(
                    actor = actor,
                    isSelected = actor.id.toString() in selectedActorIds.toString(),
                    onActorSelected = onActorSelected
                )
            }
        }
    }
}







@Composable
fun ActorSelectionItem(
    actor: PersonInfo,
    isSelected: Boolean,
    onActorSelected: (PersonInfo, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onActorSelected(actor, !isSelected) }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            // 🔴 Circular Actor Image
            Image(
                painter = rememberAsyncImagePainter("https://vod.nobino.ir/vod/" + actor.imagePath),
                contentDescription = actor.name,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(2.dp, if (isSelected) Color.Red else Color.Gray, CircleShape)
            )

            // 🔴 Show Checkmark if Selected
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 🔴 Actor Name
        Text(
            text = actor.name,
            color = if (isSelected) Color.Red else Color.Black
        )
    }
}



@Composable
fun ActorGridItem(
    actor: PersonInfo,
    isSelected: Boolean,
    onActorSelected: (PersonInfo, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onActorSelected(actor, !isSelected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔴 Actor Name
        Text(
            text = actor.name,
            color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        // 🔴 Circular Actor Image
        Image(
            painter = rememberAsyncImagePainter("https://vod.nobino.ir/vod/" + actor.imagePath),
            contentDescription = actor.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}