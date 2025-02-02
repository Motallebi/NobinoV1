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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.CountryInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.CustomBottomSheet
import com.smcdeveloper.nobinoapp.ui.screens.product.DynamicMoviesGrid
import com.smcdeveloper.nobinoapp.ui.screens.product.InfoCard
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieCard
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import com.smcdeveloper.nobinoapp.ui.screens.search.ParentModalContent
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch





@Composable
fun DemoBottomSheetSearch(homeViewModel: HomeViewModel= hiltViewModel(), navController: NavHostController) {
    // 🔴 Search Query State

    var searchQuery by remember { mutableStateOf("") } // 🔴 Search text state
    val debouncedSearchQuery = rememberDebouncedQuery(searchQuery) // 🔴 Debounced input

    val coroutineScope = rememberCoroutineScope()



    // 🔴 Selected Filters State
    var selectedTags by remember { mutableStateOf(setOf<String>()) }
    var selectedCategories by remember { mutableStateOf(setOf("MOVIE,SERIES")) }
    var selectedCountryIds by remember { mutableStateOf(setOf<String>()) }

    var appliedFilters by remember { mutableStateOf<List<String>>(emptyList()) }

    // 🔴 API Query Parameters (Updated Dynamically)
    var tagsForApi by remember { mutableStateOf("") }
    var categoriesForApi by remember { mutableStateOf("") }
    var countriesForApi by remember { mutableStateOf("") }

    // 🔴 Debounce Filter Changes to Prevent Frequent API Calls
    val debouncedTags = rememberDebouncedQuery(tagsForApi)
    val debouncedCategories = rememberDebouncedQuery(categoriesForApi)
    val debouncedCountries = rememberDebouncedQuery(countriesForApi)
    var selectedCountries by remember { mutableStateOf(setOf<CountryInfo>()) } // 🔴 Store full objects for UI



    // 🔴 Bottom Sheet Visibility
    var isParentSheetVisible by remember { mutableStateOf(false) }
    var isChildSheetVisible by remember { mutableStateOf(false) }
    var selectedFilterType by remember { mutableStateOf<FilterType?>(null) }

     val contryList by homeViewModel.contries.collectAsState()
    val countryListState by homeViewModel.contries.collectAsState()

    LaunchedEffect(Unit) {
        if (countryListState is NetworkResult.Loading) {
            homeViewModel.fetchCountries()
        }
    }







    // 🔴 Fetch Country List (Dynamic API Data)
 //   val countryList = homeViewModel.getCountries().collectAsState(initial = emptyList()).value





    // 🔴 Build Query Parameters When Filters Change
    LaunchedEffect(selectedTags, selectedCategories, selectedCountryIds) {
        tagsForApi = selectedTags.joinToString(",")
        categoriesForApi = selectedCategories.joinToString(",")
        countriesForApi = selectedCountryIds.joinToString(",")
      //  homeViewModel.fetchCountries()
        Log.d("search","ciuntries are"+contryList.data.toString())

    }

    LaunchedEffect(selectedCountries) {


       // homeViewModel.fetchCountries()
        Log.d("search","ciuntries are"+contryList.data.toString())


    }












    // 🔴 Fetch Movies Using Jetpack Paging (Triggers when query or filters change)
    val products = homeViewModel.getMoviesByCategory(
        tag = debouncedTags,
        categoryName = debouncedCategories,
        countries = debouncedCountries,
        name =debouncedSearchQuery,
        size = 20

    ).collectAsLazyPagingItems()


    // 🔴 START OF SCAFFOLD 🔴
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // 🔴 Search Bar with Badge
                SearchBarWithBadge1(

                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it }, // 🔴 Update search text
                    filterCount = appliedFilters.size,
                    onBadgeClick = { isParentSheetVisible = true } // 🔴 Open Parent Bottom Shee
                )

                // 🔴 Show Applied Filters Below Search Bar
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
                        val movie = products[index]
                        if (movie != null) {
                           /* MovieItem(
                                movieTitle = movie.name.toString(),
                                rating = movie.popularityRate.toString()
                            )*/

                            MovieCard(movie = movie) {

                                products[index]?.let {

                                    navController.navigate(Screen.ProductDetails.withArgs("${movie.id}"))



                                }



                            }





                        }
                    }

                    // 🔴 Show Loading Indicator While Fetching More Data
                    products.apply {
                        when {
                            loadState.append is LoadState.Loading -> {
                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }

                            loadState.refresh is LoadState.Loading -> {
                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }

                        }


                    }


                    // DynamicMoviesGrid(products=products)

                    /* products.apply {
                    when {
                        loadState.append is LoadState.Loading -> {

                            item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                        }




                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                        }
                    }*/


                    // 🔴 Movie Grid with Pagination
                    /*   LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                )

                {
                    items(products.itemCount) { index ->
                        val movie = products[index]
                        if (movie != null) {

                            MovieCard(movie) {


                            }

                        }
                    }

                    // 🔴 Show Loading Indicator While Fetching More Data
                    products.apply {
                        when {
                            loadState.append is LoadState.Loading -> {

                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }




                            loadState.refresh is LoadState.Loading -> {
                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }
                        }
                    }
                }*/
                }














            }
        }

    )
    // 🔴 END OF SCAFFOLD 🔴

    // 🔴 Parent Bottom Sheet for Filter Selection
    CustomBottomSheet(
        isVisible = isParentSheetVisible,
        onDismiss = { isParentSheetVisible = false }
    ) {
        Column {
            Text(
                text = "فیلترها", // "Filters"
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                val filterOptions = listOf(
                    FilterType.GENRE to Icons.Default.Folder,
                    FilterType.COUNTRY to Icons.Default.Public,
                    FilterType.YEAR to Icons.Default.DateRange
                )

                items(filterOptions) { (filterType, icon) ->
                    FilterListItem(
                        filterType = filterType,
                        icon = icon,
                        onOpenChild = {
                            selectedFilterType = filterType
                            isParentSheetVisible = false
                            isChildSheetVisible = true
                        }
                    )
                }
            }
        }
    }
    // 🔴 Child Bottom Sheet for Specific Filter Selection

    CustomBottomSheet(
        isVisible = isChildSheetVisible,
        onDismiss = { isChildSheetVisible = false }
    ) {
        Column {
            Text(
                text = selectedFilterType?.label ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (selectedFilterType) {
                FilterType.GENRE -> FilterSelectionSheet(
                    title = "ژانر",
                    items = listOf("عاشقانه", "کمدی", "معمایی", "فانتزی", "اکشن"), // Load from API
                    selectedItems = selectedTags,
                    onItemSelected = { genre, isSelected ->
                        selectedTags = selectedTags.toMutableSet().apply {
                            if (isSelected) add(genre) else remove(genre)
                        }
                        tagsForApi = selectedTags.joinToString(",")
                        appliedFilters = selectedTags.toList()
                    }
                )

                FilterType.YEAR -> FilterSelectionSheet(
                    title = "سال ساخت",
                    items = (2000..2025).map { it.toString() }, // Load from API
                    selectedItems = selectedCategories,
                    onItemSelected = { year, isSelected ->
                        selectedCategories = selectedCategories.toMutableSet().apply {
                            if (isSelected) add(year) else remove(year)
                        }
                        categoriesForApi = selectedCategories.joinToString(",")
                        appliedFilters = selectedCategories.toList()
                    }
                )


                FilterType.COUNTRY -> {


                    when (countryListState) {
                        is NetworkResult.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        is NetworkResult.Error -> {
                            Text(
                                text = "خطا در بارگیری کشورها", // "Error loading countries"
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }


                        is NetworkResult.Success->
                        {


                            contryList.data?.let {
                                FilterCountriesSelectionSheet(
                                    title = "کشور سازنده",
                                    items = it.countryInfo,
                                    selectedItemIds = selectedCountryIds,
                                    onItemSelected = { country, isSelected ->
                                        selectedCountryIds = selectedCountryIds.toMutableSet().apply {
                                            if (isSelected) add(country.id.toString()) else remove(country.id.toString())
                                        }
                                        countriesForApi = selectedCountryIds.joinToString(",")
                                        appliedFilters = selectedCountries.map { it.name }

                                        coroutineScope.launch {
                                            countriesForApi =
                                                selectedCountryIds.joinToString(",") // 🔴 Convert to "1,2,3"
                                            // movieResults = fetchMoviesFromServer(searchQuery, countryIdsForApi)
                                            Log.d(
                                                "CountrySelection",
                                                "Selected Country IDs: $countriesForApi"
                                            )


                                        }






                                    },
                                    onClose = { isChildSheetVisible = false }
                                )
                            }


















                        }





                    }
































                }

                else -> {}
            }
        }
    }
}







@Composable
fun FilterCountriesSelectionSheet(
    title: String,
    items: List<CountryInfo>,
    selectedItemIds: Set<String>, // 🔴 Store only IDs
    onItemSelected: (CountryInfo, Boolean) -> Unit,
    onClose: () -> Unit // 🔴 Handle closing bottom sheet
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) }

    CustomBottomSheet(isVisible = true, onDismiss = onClose) { // 🔴 Use bottom sheet
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(

                )
                .padding(16.dp)
        ) {
            // 🔴 Header with Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = onClose) { // 🔴 Close bottom sheet
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔴 Search Bar with Debouncing
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("جستجو...") }, // "Search..."
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
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
                                text = item.name, // 🔴 Show country name
                                isSelected = item.id.toString() in selectedItemIds, // 🔴 Check by ID
                                onSelected = { isSelected -> onItemSelected(item, isSelected) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
































@Composable
fun DynamicMoviesGrid(
    products: LazyPagingItems<MovieResult.DataMovie.Item>, // A list of mixed data types (movies and informational items)
    modifier: Modifier = Modifier,
    onMovieClick: (MovieResult.DataMovie.Item) -> Unit = {}, // Handles movie card clicks
    onInfoClick: (MovieResult.DataMovie.Item) -> Unit = {} // Handles informational card clicks
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Adjust to your desired grid layout
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    )
    {
        items(products.itemCount)
        {
                index ->
            when (val item = products[index]?.category) {
                "MOVIES"-> {
                    products[index]?.let {
                        MovieCard(
                            movie = it,
                            onClick = { products[index]?.let { movie -> onMovieClick(movie) }

                                Log.d("category clicked..",products.get(index)?.name.toString())



                            }
                        )
                        Log.d("category","Movies")

                    }
                }
                "SERIES" -> {
                    products[index]?.let {
                        InfoCard(
                            info = it,
                            onClick = { products[index]?.let { movie -> onMovieClick(movie) } })
                        Log.d("category","Movies")


                    }



                }
                else->
                {

                    products[index]?.let {
                        MovieCard(
                            movie = it,
                            onClick = { products[index]?.let { movie -> onMovieClick(movie) } }
                        )
                        Log.d("category","others")

                    }







                }




            }
        }
    }
}





@Composable
fun SearchBarWithBadge1(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    filterCount: Int,
    onBadgeClick: () -> Unit
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔴 Search Input Field
        TextField(
            value = searchQuery,
            onValueChange = onSearchChange, // 🔴 Update state
            placeholder = { Text("جستجو...") }, // "Search..."
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.weight(1f) // 🔴 Takes most of the row width
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 🔴 Filter Icon with Badge
        IconButton(onClick = onBadgeClick) {
            BadgedBox(
                badge = {
                    if (filterCount > 0) { // 🔴 Show badge only when filters are applied
                        Badge {
                            Text("$filterCount") // 🔴 Show filter count
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}



































@Composable
fun FilterSelectionSheet(
    title: String,
    items: List<String>,
    selectedItems: Set<String>,
    onItemSelected: (String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        SelectionCheckboxItem(
                            text = item,
                            isSelected = item in selectedItems,
                            onSelected = { isSelected -> onItemSelected(item, isSelected) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun rememberDebouncedQuery(inputQuery: String, debounceTime: Long = 500L): String {
    var debouncedQuery by remember { mutableStateOf(inputQuery) }

    LaunchedEffect(inputQuery) {
        delay(debounceTime) // 🔴 Wait for user to stop typing
        debouncedQuery = inputQuery // 🔴 Update the debounced query
    }

    return debouncedQuery
}
