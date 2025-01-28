package com.smcdeveloper.nobinoapp.ui.screens.demo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import com.smcdeveloper.nobinoapp.ui.screens.search.ParentModalContent
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun DemoSearch(viewModel: FilterViewModel= hiltViewModel(),
   navHostController: NavHostController
)
{
    //SearchScreen(viewModel,navHostController)
    BasicSearchScreen(viewModel,navHostController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSearchScreen(viewModel: FilterViewModel, navHostController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    // 🔴 Track selected genres & badge count
    var selectedGenres by remember { mutableStateOf(mutableSetOf<String>()) }
    var selectedGenreCount by remember { mutableStateOf(0) }

    val parentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val childSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isParentVisible by remember { mutableStateOf(false) }
    var isChildVisible by remember { mutableStateOf(false) }

    // 🔴 Ensure modal visibility logs for debugging
    LaunchedEffect(isParentVisible) {
        Log.d("SearchScreen", "🔍 Parent Modal Visibility: $isParentVisible")
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // 🔴 Search Bar with Badge
                SearchBarWithBadge(
                    filterCount = selectedGenreCount,
                    onBadgeClick = {
                        Log.d("SearchScreen", "📂 Opening Parent Modal")
                        coroutineScope.launch {
                            isParentVisible = true
                            parentSheetState.show()
                        }
                    }
                )
            }
        }
    )

    // 🔴 Parent Modal (Filter List)
    if (isParentVisible) {
        ModalBottomSheet(
            sheetState = parentSheetState,
            onDismissRequest = {
                Log.d("SearchScreen", "❌ Parent Modal Dismissed")
                coroutineScope.launch {
                    isParentVisible = false
                    parentSheetState.hide()
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            ParentModalContent(
                onClose = {
                    coroutineScope.launch {
                        isParentVisible = false
                        parentSheetState.hide()
                    }
                },
                onOpenChild = { filterType ->
                    if (filterType == FilterType.GENRE) {
                        coroutineScope.launch {
                            isParentVisible = false
                            parentSheetState.hide()
                            isChildVisible = true
                            childSheetState.show()
                        }
                    }
                }
            )
        }
    }

    // 🔴 Child Modal (Genre Selection)
    if (isChildVisible) {
        ModalBottomSheet(
            sheetState = childSheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    isChildVisible = false
                    childSheetState.hide()
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            GenreSelectionModal(
                selectedGenres = selectedGenres,
                selectedGenreCount = selectedGenreCount,
                onGenreSelected = { genre, isSelected ->
                    selectedGenres = selectedGenres.toMutableSet().apply {
                        if (isSelected) add(genre) else remove(genre)
                    }
                    selectedGenreCount = selectedGenres.size
                },
                onClose = {
                    coroutineScope.launch {
                        isChildVisible = false
                        childSheetState.hide()
                    }
                }
            )
        }
    }
}









@Composable
fun SearchBarWithBadge(filterCount: Int, onBadgeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search...") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onBadgeClick) {
            BadgedBox(
                badge = { Badge { Text("$filterCount") } }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter"
                )
            }
        }
    }
}

@Composable
fun ParentModalContent(onClose: () -> Unit, onOpenChild: (FilterType) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Header with Title & Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "فیلتر", // "Filter" in Persian
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 LazyColumn for Filter Options
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val filterOptions = listOf(
                FilterType.GENRE to Icons.Default.Folder,
                FilterType.COUNTRY to Icons.Default.Public,
                FilterType.YEAR to Icons.Default.DateRange,
                FilterType.ACTOR to Icons.Default.Person,
                FilterType.SORT to Icons.Default.Sort,
                FilterType.AUDIO to Icons.Default.VolumeUp,
                FilterType.SUBTITLE to Icons.Default.Subtitles
            )

            items(filterOptions) { (filterType, icon) ->
                FilterListItem(filterType, icon, onOpenChild)
            }
        }
    }
}





@Composable
fun GenreSelectionModal(
    selectedGenres: Set<String>,
    selectedGenreCount: Int,
    onGenreSelected: (String, Boolean) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val genres = listOf(
        "عاشقانه", "کمدی", "معمایی", "صفحه ورزش", "فانتزی", "اجتماعی", "مهیج"
    )

    val filteredGenres = genres.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Header with Badge Counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ژانر", // "Genre" in Persian
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row {
                if (selectedGenreCount > 0) {
                    Badge {
                        Text("$selectedGenreCount", color = Color.White)
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("جستجو...") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 LazyColumn for Genre Selection
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredGenres.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { genre ->
                        GenreCheckboxItem(
                            genre = genre,
                            isSelected = genre in selectedGenres,
                            onGenreSelected = onGenreSelected,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun GenreCheckboxItem(
    genre: String,
    isSelected: Boolean,
    onGenreSelected: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onGenreSelected(genre, !isSelected) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onGenreSelected(genre, it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red, // 🔴 Change color when selected
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = genre,
            color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface
        )
    }
}
















@Composable
fun FilterListItem(option: FilterType, icon: ImageVector, onOpenChild: (FilterType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenChild(option) }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = option.label, color = MaterialTheme.colorScheme.onSurface)
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Next")
    }
}













@Composable
fun ChildModalContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "This is the Child Modal",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClose, // Close the child modal
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close Child Modal")
        }
    }
}




