package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.AudioSubtitle
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.CountryInfo
import com.smcdeveloper.nobinoapp.data.model.search.GenreInfo
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.CustomBottomSheet
import com.smcdeveloper.nobinoapp.ui.screens.product.InfoCard
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieCard
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterActorsSelectionSheet
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterAudioSelectionSheet
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterCountriesSelectionSheet
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterSubtitleSelectionSheet
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import com.smcdeveloper.nobinoapp.ui.screens.search.GenreSelectionSheet
import com.smcdeveloper.nobinoapp.ui.screens.search.YearSelectionSheet
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import kotlin.math.abs
import kotlin.math.max










val audioOptions = listOf(
    AudioSubtitle(id = "FARSI", value = "FARSI", name = "فارسی"),
    AudioSubtitle(id = "ENGLISH", value = "ENGLISH", name = "انگلیسی"),
    AudioSubtitle(id = "ARABIC", value = "ARABIC", name = "عربی"),
    AudioSubtitle(id = "FRENCH", value = "FRENCH", name = "فرانسه"),
    AudioSubtitle(id = "TURKISH", value = "TURKISH", name = "ترکیه"),
    AudioSubtitle(id = "RUSSIAN", value = "RUSSIAN", name = "روسیه"),
    AudioSubtitle(id = "SPANISH", value = "SPANISH", name = "اسپانیا"),
    AudioSubtitle(id = "KOREAN", value = "KOREAN", name = "کره"),
    AudioSubtitle(id = "GERMANY", value = "GERMANY", name = "آلمان"),
    AudioSubtitle(id = "INDIAN", value = "INDIAN", name = "هند"),
    AudioSubtitle(id = "JAPANESE", value = "JAPANESE", name = "ژاپن"),
    AudioSubtitle(id = "CHINESE", value = "CHINESE", name = "چینی"),
    AudioSubtitle(id = "ITALIAN", value = "ITALIAN", name = "ایتالیایی"),
    AudioSubtitle(id = "NORWEGIAN", value = "NORWEGIAN", name = "نروژی")
)








@Composable
fun DemoBottomSheetSearch(
    homeViewModel: HomeViewModel= hiltViewModel(),
    navController: NavHostController,
    filterViewModel: FilterViewModel= hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()



)


{


    // 🔴 Search Query State

    var searchQuery by remember { mutableStateOf("") } // 🔴 Search text state
    val debouncedSearchQuery = rememberDebouncedQuery(searchQuery) // 🔴 Debounced input

    val coroutineScope = rememberCoroutineScope()


    // 🔴 Selected Filters State
    var selectedTags by remember { mutableStateOf(setOf<String>()) }
    var selectedCategories by remember { mutableStateOf(setOf("MOVIE,SERIES")) }
    var selectedCountryIds by remember { mutableStateOf(setOf<String>()) }
    var selectedGenreIds by remember { mutableStateOf(setOf<String>()) }
    val selectedGenres by remember { mutableStateOf(setOf<GenreInfo>()) } // 🔴 Store full objects for UI



    var selectedActors by remember { mutableStateOf(setOf<String>()) }

    var selectedFromYear by remember { mutableStateOf<Int?>(null) }
    var selectedToYear by remember { mutableStateOf<Int?>(null) }
    var selectedAudioIds by remember { mutableStateOf(setOf<String>()) }
    var selectedSubtitleIds by remember { mutableStateOf(setOf<String>()) }


    var appliedFilters by remember { mutableStateOf<List<String>>(emptyList()) }

    // 🔴 API Query Parameters (Updated Dynamically)
    var tagsForApi by remember { mutableStateOf("") }
    var categoriesForApi by remember { mutableStateOf("") }
    var countriesForApi by remember { mutableStateOf("") }
    var genresForApi by remember { mutableStateOf("") }


    // 🔴 Debounce Filter Changes to Prevent Frequent API Calls
    val debouncedTags = rememberDebouncedQuery(tagsForApi)
    val debouncedCategories = rememberDebouncedQuery(categoriesForApi)
    val debouncedCountries = rememberDebouncedQuery(countriesForApi)
    val debouncedGenres = rememberDebouncedQuery(genresForApi)


    val selectedCountries by remember { mutableStateOf(setOf<CountryInfo>()) } // 🔴 Store full objects for UI



    // 🔴 Bottom Sheet Visibility
    var isParentSheetVisible by remember { mutableStateOf(false) }
    var isChildSheetVisible by remember { mutableStateOf(false) }
    var selectedFilterType by remember { mutableStateOf<FilterType?>(null) }

    val contryList by searchViewModel.contries.collectAsState()
    val countryListState by searchViewModel.contries.collectAsState()

    val actorState = filterViewModel.actors.collectAsState()
    val genreState = filterViewModel.genres.collectAsState()

    var shouldFetchGenres by remember { mutableStateOf(true) } // ✅ Boolean flag






    LaunchedEffect(Unit) {
        if (countryListState is NetworkResult.Loading) {
            searchViewModel.fetchCountries()
        }
    }
// 🔴 Fetch genres when screen is first opened
    LaunchedEffect(shouldFetchGenres) { // ✅ Runs when shouldFetchGenres changes
        if (shouldFetchGenres) {
            filterViewModel.fetchGenres()
            shouldFetchGenres = false // ✅ Prevent multiple API calls
        }
    }


    val actors = when (val state = actorState.value) {
        is NetworkResult.Success -> {
            Log.d("ActorAPI", "✅ Successfully loaded ${state.data?.size} actors")
            state.data
        }

        is NetworkResult.Error -> {
            Log.e("ActorAPI", "❌ Error loading actors: ${state.message}")
            emptyList()
        }

        is NetworkResult.Loading -> {
            Log.d("ActorAPI", "⏳ Loading actors...")
            emptyList()
        }
    }


    val genres = when (val state = genreState.value) {
        is NetworkResult.Success -> {
            Log.d("genres", "✅ Successfully genres ${state.data?.size} genres")
            state.data
        }

        is NetworkResult.Error -> {
            Log.e("genres", "❌ Error loading genres: ${state.message}")
            emptyList()
        }

        is NetworkResult.Loading -> {
            Log.d("genres", "⏳ Loading genres...")
            emptyList()
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
        Log.d("search", "ciuntries are" + contryList.data.toString())

    }

    LaunchedEffect(selectedCountries) {


        // homeViewModel.fetchCountries()
        Log.d("search", "ciuntries are" + contryList.data.toString())


    }



    val searchText by searchViewModel.searchText.collectAsState()
    val movies = searchViewModel.moviesFlow1.collectAsLazyPagingItems()
    val isSearching by searchViewModel.isSearching.collectAsState()








    // 🔴 Fetch Movies Using Jetpack Paging (Triggers when query or filters change)
    val products = searchViewModel.getMovies(
        tag = debouncedTags,
        categoryName = debouncedCategories,
        countries = debouncedCountries,
        name = debouncedSearchQuery,
        size = 20

    ).collectAsLazyPagingItems()





    val isLoading by homeViewModel.isLoading1.collectAsState()






    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {}





        Log.d("search", "Demo Isloading....${isLoading}")




        // 🔴 START OF SCAFFOLD 🔴
        Scaffold(
            content = { paddingValues ->
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)

                )


                {

                    Box(modifier = Modifier.fillMaxWidth()
                        .height(100.dp)
                        //.background(Color.Red),
                       // contentAlignment = Alignment.Center

                    )
                    {


                      /*  if(true) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp),
                                color = Color.Green



                            )
                        }*/


                    }





//                    if(false)
//                    {
//                        Text("isloading")
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .padding(16.dp)
//
//
//                        ) // 🔄 Show Load
//
//                    }


                    TextField(
                        value = searchText,
                        onValueChange = searchViewModel::onSearchTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Search") }
                    )


                    if(isSearching) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )


                        {
                            Log.d("search", "movie are found ${movies.itemCount}")


                            val movieName = listOf("mv1", "mv2", "mv3")


                            items(
                                count = movies.itemCount,
                                key = { index -> movies[index]?.id ?: index },
                                contentType = movies.itemContentType { "Comments" }
                            )
                            { index ->
                               Text("Movie name is : ${movies[index]!!.name}")
                                Log.d("search", "movie name is ${movies[index]?.name}")
                            }












                        }


                    }


























                    // 🔴 Search Bar with Badge
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {























                        SearchBarWithBadge1(

                            searchQuery = searchQuery,
                            onSearchChange = { searchQuery = it }, // 🔴 Update search text
                            filterCount = appliedFilters.size,
                            onBadgeClick = {
                                isParentSheetVisible = true
                            },
                            onSuggestionClick = {

                                    selected ->
                                searchQuery =
                                    selected.name.toString() // Update the search field


                            },
                            suggestions = products


                            // 🔴 Open Parent Bottom Shee
                        )
                    }

                    // 🔴 Show Applied Filters Below Search Bar
                    if (appliedFilters.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
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
                        items(movies.itemCount) { index ->
                            val movie = movies[index]
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
                                    //  item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                                }

                                loadState.refresh is LoadState.Loading -> {
                                    // item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
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
                        FilterType.YEAR to Icons.Default.DateRange,
                        FilterType.ACTOR to Icons.Default.Person,
                        FilterType.SORT to Icons.Default.Sort,
                        FilterType.AUDIO to Icons.Default.Audiotrack,
                        FilterType.SUBTITLE to Icons.Default.Subtitles


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
                    FilterType.GENRE -> {


                        if (genres != null) {
                            GenreSelectionSheet(
                                // viewModel = filterViewModel, // ✅ Pass ViewModel
                                items = genres,
                                selectedItemIds = selectedGenreIds,
                                onItemSelected = { genre, isSelected ->
                                    selectedGenreIds = selectedGenreIds.toMutableSet().apply {
                                        if (isSelected) add(genre.id.toString()) else remove(
                                            genre.id.toString()
                                        )
                                    }
                                },

                                onClose = { isChildSheetVisible = false }
                            )
                        }

                    }


                    FilterType.SUBTITLE -> {

                        FilterSubtitleSelectionSheet(
                            selectedSubtitleIds = selectedSubtitleIds,
                            onSubtitleSelected = { sub, isSelected ->
                                selectedSubtitleIds = selectedSubtitleIds.toMutableSet().apply {
                                    if (isSelected) add(sub.id) else remove(sub.id)
                                }
                                appliedFilters =
                                    selectedTags.toList() + selectedSubtitleIds.toList() + selectedSubtitleIds.toList()
                            },

                            // audioSubtitles = AUDIO_SUBTITLES,

                            onClose = { isChildSheetVisible = false }

                        )


                    }


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


                            is NetworkResult.Success -> {


                                contryList.data?.let {
                                    FilterCountriesSelectionSheet(
                                        title = "کشور سازنده",
                                        items = it.countryInfo,
                                        selectedItemIds = selectedCountryIds,
                                        onItemSelected = { country, isSelected ->
                                            selectedCountryIds =
                                                selectedCountryIds.toMutableSet().apply {
                                                    if (isSelected) add(country.id.toString()) else remove(
                                                        country.id.toString()
                                                    )
                                                }
                                            /*   countriesForApi = selectedCountryIds.joinToString(",")
                                               // 🔴 Update applied filters to show country names*/
                                            appliedFilters =
                                                appliedFilters.toMutableList().apply {
                                                    if (isSelected) add(country.name) else remove(
                                                        country.name
                                                    )
                                                }

                                            // 🔴 Update API query parameter
                                            countriesForApi =
                                                selectedCountryIds.joinToString(",")

                                            // 🔴 Debugging log
                                            Log.d(
                                                "CountrySelection",
                                                "Selected Country IDs: $countriesForApi, Names: $appliedFilters"
                                            )
                                            /*
                                                                                    coroutineScope.launch {
                                                                                        countriesForApi =
                                                                                            selectedCountryIds.joinToString(",") // 🔴 Convert to "1,2,3"
                                                                                        // movieResults = fetchMoviesFromServer(searchQuery, countryIdsForApi)
                                                                                        Log.d(
                                                                                            "CountrySelection",
                                                                                            "Selected Country IDs: $countriesForApi"
                                                                                        )*/


                                        },
                                        onClose = { isChildSheetVisible = false }
                                    )
                                }


                            }


                        }


                    }


                    FilterType.ACTOR -> {


                        if (actors != null) {
                            FilterActorsSelectionSheet(
                                title = "انتخاب بازیگر",
                                actors = actors,
                                selectedActorIds = selectedActors,
                                onActorSelected = { actor, isSelected ->
                                    selectedActors = selectedActors.toMutableSet().apply {
                                        if (isSelected) add(actor.id.toString()) else remove(
                                            actor.id.toString()
                                        )
                                    }


                                    appliedFilters = appliedFilters.toMutableList().apply {
                                        if (isSelected) add(actor.name) else remove(actor.name)
                                    }


                                },
                                onSearchQueryChanged = { query ->
                                    filterViewModel.fetchActors(
                                        query
                                    )
                                }, // 🔴 Fetch actors dynamically


                                onClose = { isChildSheetVisible = false }
                            )
                        }


                    }

                    FilterType.YEAR -> {


                        YearSelectionSheet(
                            selectedFromYear = selectedFromYear,
                            selectedToYear = selectedToYear,
                            onYearSelected = { year, isFromYear ->
                                if (isFromYear) selectedFromYear = year else selectedToYear =
                                    year

                                // 🔴 Update Applied Filters
                                appliedFilters = listOfNotNull(
                                    selectedFromYear?.let { "از $it" },
                                    selectedToYear?.let { "تا $it" }
                                )
                            },

                            onClose = { isChildSheetVisible = false }
                        )


                    }


                    FilterType.AUDIO -> {

                        FilterAudioSelectionSheet(
                            selectedAudioIds = selectedAudioIds,
                            onAudioSelected = { audio, isSelected ->
                                selectedAudioIds = selectedAudioIds.toMutableSet().apply {
                                    if (isSelected) add(audio.id) else remove(audio.id)
                                }
                                appliedFilters =
                                    selectedTags.toList() + selectedAudioIds.toList() + selectedSubtitleIds.toList()
                            },

                            // audioSubtitles = AUDIO_SUBTITLES,

                            onClose = { isChildSheetVisible = false }

                        )


                    }


                    else -> {}
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
            { index ->
                when (val item = products[index]?.category) {
                    "MOVIES" -> {
                        products[index]?.let {
                            MovieCard(
                                movie = it,
                                onClick = {
                                    products[index]?.let { movie -> onMovieClick(movie) }

                                    Log.d(
                                        "category clicked..",
                                        products.get(index)?.name.toString()
                                    )


                                }
                            )
                            Log.d("category", "Movies")

                        }
                    }

                    "SERIES" -> {
                        products[index]?.let {
                            InfoCard(
                                info = it,
                                onClick = { products[index]?.let { movie -> onMovieClick(movie) } })
                            Log.d("category", "Movies")


                        }


                    }

                    else -> {

                        products[index]?.let {
                            MovieCard(
                                movie = it,
                                onClick = { products[index]?.let { movie -> onMovieClick(movie) } }
                            )
                            Log.d("category", "others")

                        }


                    }


                }
            }
        }
    }


    @Composable
    fun SearchBarWithBadge3(
        searchQuery: String,
        onSearchChange: (String) -> Unit,
        filterCount: Int,
        onBadgeClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(24.dp)),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )

        {
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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    fun SearchBarWithBadge4(
        searchQuery: String,
        onSearchChange: (String) -> Unit,
        filterCount: Int,
        onBadgeClick: () -> Unit,
        suggestions: LazyPagingItems<MovieResult.DataMovie.Item>,
        onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit


    ) {
        var expanded by remember { mutableStateOf(false) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            elevation = CardDefaults.cardElevation()
        )

        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                // .padding(LocalSpacing.current.small)
                //.clip(LocalShape.current.biggerSmall)
                //  .background(Color.Red)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(Color.Black, shape = RoundedCornerShape(50.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                )


                {


                    TextField(


                        value = searchQuery,

                        onValueChange = {
                            onSearchChange(it)
                            expanded = it.isNotEmpty() // Show suggestions when text is entered
                        },


                        //onValueChange = onSearchChange,


                        placeholder = { Text("جستجو...", color = Color.Red) },
                        textStyle = TextStyle(color = Color.White),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .background(Color.DarkGray, shape = RoundedCornerShape(24.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        //  trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Red) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Red
                            )
                        }

                    )


                    // 🔴 Filter Icon with Badge (Left Side)
                    IconButton(onClick = onBadgeClick) {
                        BadgedBox(
                            badge = {
                                if (filterCount > 0) { // Show badge only when filters are applied
                                    Badge { Text("$filterCount") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ImageSearch,
                                contentDescription = "Filter",
                                tint = Color.Green

                            )
                        }
                    }




                    if (expanded && suggestions.itemCount > 0) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(vertical = 4.dp)
                        )
                        {
                            items(suggestions.itemCount) { index ->
                                Text(
                                    text = suggestions[index]?.name.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            suggestions[index]?.let { onSuggestionClick(it) }
                                            expanded = false // Hide suggestions after selection
                                        }
                                        .padding(12.dp)
                                )
                            }

                            suggestions.apply {

                                when {
                                    loadState.append is LoadState.Loading -> {
                                        item {
                                            CircularProgressIndicator(
                                                modifier = Modifier.padding(
                                                    8.dp
                                                )
                                            )
                                        }
                                    }

                                    loadState.append is LoadState.Error -> {
                                        item { Text("Error loading more data", color = Color.Red) }
                                    }
                                }


                            }


                        }


                    }


                }

            }
        }


    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBarWithBadge5(
        searchQuery: String,
        onSearchChange: (String) -> Unit,
        filterCount: Int,
        onBadgeClick: () -> Unit,
        suggestions: LazyPagingItems<MovieResult.DataMovie.Item>,
        onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        var color: Color

        if (expanded)
            color = Color.Green
        else
            color = Color.Red
        Log.d("search", "sugeest size: ${suggestions.itemCount}")


        Column {
            // 🔴 Search Input Field

            AnimatedVisibility(visible = expanded)

            {

                Card(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    elevation = CardDefaults.cardElevation()
                )

                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color.Black, shape = RoundedCornerShape(50.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )

                    {
                        TextField(
                            value = searchQuery,
                            onValueChange = {

                                onSearchChange(it)
                                expanded =
                                    it.isNotEmpty() // 🔴 Show suggestions when text is entered
                            },
                            placeholder = { Text("جستجو...", color = Color.Red) },


                            textStyle = TextStyle(


                                color = color


                            ),


                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(Color.DarkGray, shape = RoundedCornerShape(24.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Red
                                )
                            }
                        )

                        // 🔴 Filter Icon with Badge
                        IconButton(onClick = onBadgeClick) {
                            BadgedBox(
                                badge = {
                                    if (filterCount > 0) {
                                        Badge { Text("$filterCount") }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ImageSearch,
                                    contentDescription = "Filter",
                                    tint = Color.Green
                                )
                            }
                        }
                    }









                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(vertical = 4.dp)
                            .height(50.dp)
                            .clickable {

                                expanded = false


                            }
                    ) {
                        items(suggestions.itemCount) { index ->
                            suggestions[index]?.let { movie ->
                                SuggestionTextItem(
                                    movie.name.toString(),
                                    onClick = { onSuggestionClick(movie); expanded = false })
                            }
                        }
                    }


                }


            }

            // 🔴 Display Suggestions Below Search Bar
            if (expanded && suggestions.itemCount > 0) {
                Log.d("search", "Lazy sugeest size: ${suggestions.itemCount > 0}")

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBarWithBadge1(
        searchQuery: String,
        onSearchChange: (String) -> Unit,
        filterCount: Int,
        onBadgeClick: () -> Unit,
        suggestions: LazyPagingItems<MovieResult.DataMovie.Item>,
        onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        val textColor = if (expanded) Color.Green else Color.Red

        Column {
            // 🔴 Search Input Field
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(Color.Black, shape = RoundedCornerShape(50.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = {
                                onSearchChange(it)
                                expanded = it.isNotEmpty() && suggestions.itemCount > 0
                            },
                            placeholder = { Text("جستجو...", color = Color.Red) },
                            textStyle = TextStyle(Color.White),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(Color.DarkGray, shape = RoundedCornerShape(24.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Red
                                )
                            }
                        )

                        // 🔴 Filter Icon with Badge
                        IconButton(onClick = onBadgeClick) {
                            BadgedBox(
                                badge = {
                                    if (filterCount > 0) {
                                        Badge { Text("$filterCount") }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ImageSearch,
                                    contentDescription = "Filter",
                                    tint = Color.Green
                                )
                            }
                        }
                    }

                    // 🔴 Expandable Card for Suggestions
                    AnimatedVisibility(visible = expanded) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp), // Show only 2 items at a time (each ~50.dp)
                            elevation = CardDefaults.cardElevation()
                        ) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(suggestions.itemCount) { index ->
                                    suggestions[index]?.let { movie ->
                                        SuggestionTextItem(
                                            movie.name.toString(),
                                            onClick = {
                                                onSuggestionClick(movie)
                                                expanded = false // Collapse after selection
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun SuggestionTextItem(text: String, onClick: () -> Unit) {
        Text(
            text = text,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }







    @Composable
    fun FilterSelectionSheet(
        title: String,
        items: List<String>,
        selectedItems: Set<String>,
        onItemSelected: (String, Boolean) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //  Text(text = title, style = MaterialTheme.typography.titleLarge)

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






    @Composable
    fun SelectionCheckboxItem2(
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
                onCheckedChange = onSelected,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red, // ✅ Change color when selected
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














