package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import com.smcdeveloper.nobinoapp.ui.screens.search.ParentModalContent
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMediumCheckBox
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import kotlinx.coroutines.launch


@Composable
fun DemoSearch(viewModel: FilterViewModel= hiltViewModel(),
               navHostController: NavHostController
)
{
    //SearchScreen(viewModel,navHostController)
    //BasicSearchScreen(viewModel,navHostController)
    DemoDialogSearch(viewModel,navHostController)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSearchScreen(viewModel: FilterViewModel, navHostController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    var selectedGenres by remember { mutableStateOf(mutableSetOf<String>()) }
    var selectedCountries by remember { mutableStateOf(mutableSetOf<String>()) }
    var selectedYears by remember { mutableStateOf(mutableSetOf<String>()) }

    var appliedFilters by remember { mutableStateOf<List<String>>(emptyList()) }

    val parentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val childSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isParentVisible by remember { mutableStateOf(false) }
    var isChildVisible by remember { mutableStateOf(false) }
    var selectedFilterType by remember { mutableStateOf<FilterType?>(null) }
    var searchQuery by remember { mutableStateOf("") } // 🔴 Search input
    var AllCheckBoxesClear by remember { mutableStateOf(false) } // 🔴 Search input




    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // 🔴 Search Bar with Badge
                // 🔴 Search Bar with API Call
                SearchBarWithBadge(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query ->
                        searchQuery = query
                        coroutineScope.launch {
                            //movieResults = fetchMoviesFromServer(query) // 🔴 API Call
                        }
                    },
                    filterCount = appliedFilters.size,
                    onBadgeClick = {
                        coroutineScope.launch {
                            isParentVisible = true
                            parentSheetState.show()
                        }
                    }
                )

                // 🔴 Show Selected Filters as Chips Below Search Bar
                if (appliedFilters.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(appliedFilters) { filter ->
                            FilterChip(text = filter, onRemove = {
                                appliedFilters = appliedFilters - filter
                            })
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // 🔴 Movie Search Results Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(10) { index ->
                        MovieItem(movieTitle = "آرور شات", rating = "IMDB 7.2")
                    }
                }
            }
        }
    )

    // 🔴 Parent Modal (Filter List)
    if (isParentVisible) {
        ModalBottomSheet(
            sheetState = parentSheetState,
            onDismissRequest = {
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
                    coroutineScope.launch {
                        selectedFilterType = filterType
                        isParentVisible = false
                        parentSheetState.hide()
                        isChildVisible = true
                        childSheetState.show()
                    }
                }
            )
        }
    }

    // 🔴 Child Modals (Dynamic Based on Selected Filter)
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
            when (selectedFilterType) {
                FilterType.GENRE -> FilterSelectionModal(
                    title = "ژانر", // "Genre"
                    items = listOf("عاشقانه", "کمدی", "معمایی", "فانتزی", "اکشن"), // 🔴 Load from API
                    selectedItems = selectedGenres,
                    onItemSelected = { genre, isSelected ->
                        selectedGenres = selectedGenres.toMutableSet().apply {
                            if (isSelected) add(genre) else remove(genre)
                        }
                        appliedFilters = selectedGenres.toList()
                    },
                    onClose = { coroutineScope.launch { isChildVisible = false; childSheetState.hide() } }
                )

                FilterType.YEAR -> FilterSelectionModal(
                    title = "سال ساخت", // "Year of Production"
                    items = (2000..2025).map { it.toString() }, // 🔴 Load from API
                    selectedItems = selectedYears,
                    onItemSelected = { year, isSelected ->
                        selectedYears = selectedYears.toMutableSet().apply {
                            if (isSelected) add(year) else remove(year)
                        }
                        appliedFilters = selectedYears.toList()
                    },
                    onClose = { coroutineScope.launch { isChildVisible = false; childSheetState.hide() } }
                )

                FilterType.COUNTRY -> FilterSelectionModal(
                    title = "کشور سازنده", // "Country of Origin"
                    items = listOf(
                        "ایران", "ارمنستان", "ترکیه", "صربستان", "آذربایجان",
                        "کره جنوبی", "لهستان", "اوکراین", "هند", "چین",
                        "ژاپن", "عراق", "نروژ", "برزیل"
                    ), // 🔴 Load from API
                    selectedItems = selectedCountries,
                    onItemSelected = { country, isSelected ->
                        selectedCountries = selectedCountries.toMutableSet().apply {
                            if (isSelected) add(country) else remove(country)
                        }
                        appliedFilters = selectedCountries.toList()
                    },
                    onClose = { coroutineScope.launch { isChildVisible = false; childSheetState.hide() } }
                )

                else -> {}
            }
        }
    }



}










@Composable
fun FilterSelectionModal(
    title: String, // 🔴 Dynamic title (e.g., "Genre", "Country", "Year")
    items: List<String>, // 🔴 List of filter options from the server
    selectedItems: Set<String>, // 🔴 Set of selected items
    onItemSelected: (String, Boolean) -> Unit, // 🔴 Selection callback
    onClose: () -> Unit, // 🔴 Close modal callback
    isAllClear:Boolean=false
) {
    var searchQuery by remember { mutableStateOf("") }



    // 🔴 Filter the items based on search input
    val filteredItems = items.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        // 🔴 Header with Title & Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Row {
                if (selectedItems.isNotEmpty()) {
                    Badge { Text("${selectedItems.size}", color = Color.White) }
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
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 LazyColumn for Selection (Two-Column Layout)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredItems.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        SelectionCheckboxItem(
                            text = item,
                            isSelected =    item in selectedItems,
                            onSelected = { isSelected -> onItemSelected(item, isSelected) },
                            modifier = Modifier.weight(1f),
                           // onClearAll = {isAllClear}
                        )
                    }
                }
            }
        }
    }
}





@Composable
fun SelectionCheckboxItem(
    text: String,
    isSelected: Boolean,
   // isCleared:Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
  //  onClearAll:()->Boolean= { false }

)
{
    Row(
        modifier = modifier
               // onSelected(!isSelected)
            .clickable { }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked =isSelected,
            onCheckedChange = onSelected,

            /*{
                Log.d("check", "Check changed $it")
                onSelected(it)




                Log.d("check", "Check changed $it")



                              },*/




            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red, // 🔴 Turns red when selected
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.nobinoMediumCheckBox

        )
    }
}














@Composable
fun FilterChip(text: String, onRemove: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onRemove, modifier = Modifier.size(20.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Remove Filter", tint = Color.Red)
            }
        }
    }
}


@Composable
fun MovieItem(movieTitle: String, rating: String) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth().height(200.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.nobino_logo), // Replace with actual image
                contentDescription = "Movie Poster",
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(movieTitle, style = MaterialTheme.typography.bodyMedium)
            Text(rating, style = MaterialTheme.typography.labelSmall, color = Color.Yellow)
        }
    }
}















@Composable
fun SearchBarWithBadge(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit, // 🔴 Callback for user input
    filterCount: Int,
    onBadgeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 🔴 Search Input Field
        TextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) }, // 🔴 Calls function on user input
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 🔴 Filter Badge Button
        IconButton(onClick = onBadgeClick) {
            BadgedBox(
                badge = { if (filterCount > 0) Badge { Text("$filterCount") } }
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
fun ParentModalContent(onClose: () -> Unit, onApplyFilters: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Filters", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        // Filter options...

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onApplyFilters()
                onClose()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Filters")
        }
    }
}






@Composable
fun GenreSelectionModal(
    selectedGenres: Set<String>,
    selectedGenreCount: Int, // 🔴 New parameter for count
    onGenreSelected: (String, Boolean) -> Unit,
    onClose: () -> Unit
) {
    val genres = listOf("عاشقانه", "کمدی", "معمایی", "فانتزی", "اکشن")

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("ژانر", style = MaterialTheme.typography.titleLarge)
            Row {
                if (selectedGenreCount > 0) { // 🔴 Show badge only if filters are selected
                    Badge {
                        Text("$selectedGenreCount", color = Color.White)
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(genres) { genre ->
                GenreCheckboxItem(
                    genre = genre,
                    isSelected = genre in selectedGenres,
                    onGenreSelected = onGenreSelected
                )
            }
        }
    }
}



@Composable
fun GenreCheckboxItem(
    genre: String,
    isSelected: Boolean,
    onGenreSelected: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onGenreSelected(genre, !isSelected) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onGenreSelected(genre, it) },
            colors = CheckboxDefaults.colors(checkedColor = Color.Red)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(genre, color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface)
    }
}













@Composable
fun FilterListItem(filterType: FilterType, icon: ImageVector, onOpenChild: (FilterType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenChild(filterType) }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = filterType.label,
                style = MaterialTheme.typography.nobinoMedium


                )
        }
        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Next")
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

@Composable
fun CountrySelectionModal(
    selectedCountries: Set<String>,
    selectedCountryCount: Int,
    onCountrySelected: (String, Boolean) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val countries = listOf(
        "ایران", "ارمنستان", "ترکیه", "صربستان", "آذربایجان",
        "کره جنوبی", "لهستان", "اوکراین", "هند", "چین",
        "ژاپن", "عراق", "نروژ", "برزیل"
    )

    val filteredCountries = countries.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        // 🔴 Header with Badge Counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("کشور سازنده", style = MaterialTheme.typography.titleLarge) // "Country of Origin"
            Row {
                if (selectedCountryCount > 0) {
                    Badge {
                        Text("$selectedCountryCount", color = Color.White)
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

        // 🔴 LazyColumn for Country Selection (Two-Column Layout)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredCountries.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { country ->
                        SelectionCheckboxItem(
                            text = country,
                            isSelected = country in selectedCountries,
                            onSelected = { isSelected -> onCountrySelected(country, isSelected) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun SelectionCheckboxItem1(
    text: String,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onSelected(!isSelected) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelected(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red, // 🔴 Turns red when selected
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface
        )
    }
}














@Composable
fun CountryCheckboxItem(
    country: String,
    isSelected: Boolean,
    onCountrySelected: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onCountrySelected(country, !isSelected) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onCountrySelected(country, it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red, // 🔴 Turns red when selected
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = country,
            color = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun YearSelectionModal(
    selectedYears: Set<String>,
    onYearSelected: (String, Boolean) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // 🔴 List of years (Modify range if needed)
    val years = (2000..2025).map { it.toString() }
    val filteredYears = years.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        // 🔴 Header with Title & Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("سال ساخت", style = MaterialTheme.typography.titleLarge) // "Year of Production"
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 Search Bar for Filtering Years
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 LazyColumn for Year Selection
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredYears) { year ->
                YearItem(
                    year = year,
                    isSelected = year in selectedYears,
                    onYearSelected = onYearSelected
                )
            }
        }
    }
}





@Composable
fun YearItem(
    year: String,
    isSelected: Boolean,
    onYearSelected: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onYearSelected(year, !isSelected) }
            .background(if (isSelected) Color.Red else Color.Transparent) // 🔴 Turns red when selected
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = year,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}








@Composable
fun YearInputField(
    label: String,
    year: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // 🔴 Fix: Allow custom modifiers
) {
    Column(
        modifier = modifier // 🔴 Fix: Apply modifier (weight will work)
            .clickable { onClick() }
            .padding(8.dp)
            .background(
                if (isSelected) Color.Red else MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = year,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun YearItem(
    year: String,
    isSelected: Boolean,
    isStartYear: Boolean,
    onYearSelected: (String, Boolean, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onYearSelected(year, isSelected, isStartYear) }
            .background(if (isSelected) Color.Red else Color.Transparent)
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = year,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}













