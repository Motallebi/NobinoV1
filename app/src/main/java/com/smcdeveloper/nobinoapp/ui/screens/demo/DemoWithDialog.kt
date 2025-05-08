package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.CountryInfo
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieCard
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import com.smcdeveloper.nobinoapp.ui.screens.search.ParentModalContent
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DemoDialogSearch(
    viewModel: FilterViewModel = hiltViewModel(), navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()

)

{
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") } // 🔴 User input
    //  var movieResults by remember { mutableStateOf(emptyList<Movie>()) } // 🔴 Search results

    var selectedGenres by remember { mutableStateOf(mutableSetOf<String>()) }
    // var selectedCountries by remember { mutableStateOf(setOf<CountryInfo>()) }

    var selectedYears by remember { mutableStateOf(mutableSetOf<String>()) }

    var appliedFilters by remember { mutableStateOf<List<String>>(emptyList()) }

    var isParentDialogVisible by remember { mutableStateOf(false) }
    var isChildDialogVisible by remember { mutableStateOf(false) }
    var selectedFilterType by remember { mutableStateOf<FilterType?>(null) }
    //  var selectedCountryIds by remember { mutableStateOf(setOf<String>()) }

    var selectedCountryIds by remember { mutableStateOf(setOf<String>()) } // 🔴 Store only IDs for API
    var selectedCountries by remember { mutableStateOf(setOf<CountryInfo>()) } // 🔴 Store full objects for UI
    // val countryIdsForApi = selectedCountryIds.joinToString(",") // 🔴 Convert to "1,2,3"
    var countryIdsForApi by remember { mutableStateOf("") }


    val products = homeViewModel.getMoviesByCategory(
        tag = "",
        categoryName = "",
        countries = countryIdsForApi,
        name = "",
        size = 20
    )
        .collectAsLazyPagingItems() // 🔴 This should be called inside a Composable scope


    val contryList by homeViewModel.contries.collectAsState()

    LaunchedEffect(Unit) {

        homeViewModel.fetchCountries()


    }













    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                // .padding(horizontal = 8.dp)
                // .background(Color.Red)
            )

            {
                // 🔴 Search Bar with API Call
                SearchBarWithBadge(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query ->
                        searchQuery = query
                        coroutineScope.launch {
                            //  movieResults = fetchMoviesFromServer(query) // 🔴 API Call
                        }
                    },
                    filterCount = appliedFilters.size,
                    onBadgeClick = {
                        isParentDialogVisible = true
                    }
                )

                // 🔴 Show Selected Filters Below Search Bar
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



                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                )

                {
                    items(products.itemCount) { index ->


                        products[index]?.let {
                            MovieCard(
                                movie = it,
                                /*onClick = { products[index]?.let { movie -> onMovieClick(movie) }

                                    Log.d("category clicked..",products.get(index)?.name.toString())



                                }*/
                                onClick = {}


                            )
                            Log.d("category", "Movies")


                        }
                    }
                }


                // 🔴 Movie Grid Based on Search Results


                // 🔴 Parent Dialog (Filter Options)
            }

        })





    if (isParentDialogVisible) {
        val icon = Icons.Default.ChevronRight
        AlertDialog(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .fillMaxWidth(),
            onDismissRequest = { isParentDialogVisible = false },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { isParentDialogVisible = false }) {
                    Text("بستن") // "Close"
                }
            },
            title = { Text("فیلترها") }, // "Filters"

            text = {


                LazyColumn {
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
                        FilterListItem(
                            filterType = filterType,
                            icon = icon, // 🔴 Now correctly passing the icon
                            onOpenChild = {
                                selectedFilterType = filterType
                                isParentDialogVisible = false
                                isChildDialogVisible = true
                            }
                        )
                    }
                }
            }
        )
    }


    // 🔴 Child Dialog (Dynamic Filter Selection)


    if (isChildDialogVisible) {
        AlertDialog(
            onDismissRequest = { isChildDialogVisible = false },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { isChildDialogVisible = false }) {
                    Text("بستن") // "Close"
                }
            },
            title = { Text(selectedFilterType?.label ?: "") }, // Dynamic Title
            text = {
                when (selectedFilterType) {
                    FilterType.GENRE -> FilterSelectionDialog(
                        title = "ژانر",
                        items = listOf(
                            "عاشقانه",
                            "کمدی",
                            "معمایی",
                            "فانتزی",
                            "اکشن"
                        ), // 🔴 Load from API
                        selectedItems = selectedGenres,
                        onItemSelected = { genre, isSelected ->
                            selectedGenres = selectedGenres.toMutableSet().apply {
                                if (isSelected) add(genre) else remove(genre)
                            }
                            appliedFilters = selectedGenres.toList()
                        }
                    )


                    FilterType.YEAR -> FilterSelectionDialog(
                        title = "سال ساخت",
                        items = (2000..2025).map { it.toString() }, // 🔴 Load from API
                        selectedItems = selectedYears,
                        onItemSelected = { year, isSelected ->
                            selectedYears = selectedYears.toMutableSet().apply {
                                if (isSelected) add(year) else remove(year)
                            }
                            appliedFilters = selectedYears.toList()
                        }
                    )

                    FilterType.COUNTRY -> contryList.data?.let {
                        FilterContriesSelectionDialog(
                            title = "کشور سازنده",
                            items = it.countryInfo, // 🔴 Load from API
                            selectedItemIds = selectedCountryIds,
                            onItemSelected =
                            { country, isSelected ->
                                selectedCountryIds =
                                    selectedCountryIds.toMutableSet().apply {
                                        if (isSelected) add(country.id.toString()) else remove(
                                            country.id.toString()
                                        )
                                    }

                                selectedCountries =
                                    selectedCountries.toMutableSet().apply {
                                        if (isSelected) add(country) else remove(country)
                                    }




                                Log.d(
                                    "CountrySelection",
                                    "Selected Country IDs: $selectedCountryIds"
                                )
                                appliedFilters =
                                    selectedCountries.map { it.name } // 🔴 Show names instead of IDs in UI


                                // 🔴 Call API immediately when a checkbox is clicked
                                coroutineScope.launch {
                                    countryIdsForApi =
                                        selectedCountryIds.joinToString(",") // 🔴 Convert to "1,2,3"
                                    // movieResults = fetchMoviesFromServer(searchQuery, countryIdsForApi)
                                    Log.d(
                                        "CountrySelection",
                                        "Selected Country IDs: $countryIdsForApi"
                                    )


                                }


                            }
                        )
                    }

                    FilterType.ACTOR -> TODO()
                    FilterType.SORT -> TODO()
                    FilterType.AUDIO -> TODO()
                    FilterType.SUBTITLE -> TODO()
                    else -> TODO()
                }

            }
        )
    }


}


@Composable
fun FilterContriesSelectionDialog(
    title: String,
    items: List<CountryInfo>,
    selectedItemIds: Set<String>, // 🔴 Store only IDs
    onItemSelected: (CountryInfo, Boolean) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems =
        items.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 List of Selectable Items (Country Names, but track by ID)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredItems.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        SelectionCheckboxItem(
                            text = item.name, // 🔴 Show country name in checkbox
                            isSelected = item.id.toString() in selectedItemIds, // 🔴 Check by ID
                            onSelected = { isSelected ->
                                onItemSelected(
                                    item,
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


@Composable
fun FilterSelectionDialog(
    title: String,
    items: List<String>,
    selectedItems: Set<String>,
    onItemSelected: (String, Boolean) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = items.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 🔴 Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 List of Selectable Items
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredItems.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        SelectionCheckboxItem(
                            text = item,
                            isSelected = item in selectedItems,
                            onSelected = { isSelected ->
                                onItemSelected(
                                    item,
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


@Composable
fun rememberDebounced(
    query: String,
    delayMillis: Long = 500L,
    onQueryChanged: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(query) {
        delay(delayMillis) // 🔴 Wait before making the API call
        onQueryChanged(query)
    }


}







