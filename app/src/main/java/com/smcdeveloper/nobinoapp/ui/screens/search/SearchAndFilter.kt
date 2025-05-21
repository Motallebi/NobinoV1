package com.smcdeveloper.nobinoapp.ui.screens.search


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.AudioSubtitle
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.CountryInfo
import com.smcdeveloper.nobinoapp.data.model.search.Filter
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.FilterBottomSheet
import com.smcdeveloper.nobinoapp.ui.component.MainFilterScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.FilterChip
import com.smcdeveloper.nobinoapp.ui.screens.demo.FilterListItem
import com.smcdeveloper.nobinoapp.ui.screens.demo.SelectionCheckboxItem
import com.smcdeveloper.nobinoapp.ui.screens.product.InfoCard
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieCard
import com.smcdeveloper.nobinoapp.ui.screens.product.SeriesCard
import com.smcdeveloper.nobinoapp.ui.theme.searchIndicatorLine
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
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
)*/




@Composable
fun TestSearch(
    homeViewModel: HomeViewModel= hiltViewModel(),
    navController: NavHostController,
    filterViewModel: FilterViewModel= hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    tags:String=""



)

{


    val searchQuery by searchViewModel.searchQuery
    val searchedProducts = searchViewModel.searchedProducts.collectAsLazyPagingItems()

    Scaffold(
        content = { paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp)

            )

            {

                SearchWidget(


                    text = searchQuery,
                    onTextChange = {searchViewModel.updateSearchQuery(query = it)
                        Log.d("newsearch ", "text changed$it")


                                   },
                    onSearchClicked = {
                        searchViewModel.searchProducts(query = it)

                      //  searchViewModel.updateSearchQuery(query = it)


                        Log.d("newsearch ",it)


                                      },
                    onCloseClicked =  { navController.popBackStack()}









                )


                LazyColumn() {

                    items(searchedProducts.itemCount)
                    {
                            index-> Row()
                    {
                        searchedProducts[index]?.name?.let { Text(it) }


                    }




                    }






                }













            }



            }
    )



















}







@Composable
fun PerformSearch( performSearch:Boolean,tags:String="",query: String,
    homeViewModel: HomeViewModel= hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
                   navController: NavHostController






)
{
   /* LaunchedEffect(performSearch) {
        Log.d("search2", "performing search......")
        Log.d("search2", "performing search......")

        searchViewModel.updateSearchParams(
            countries = "",
            category = listOf("SERIES,MOVIES"),
            tag = tags,
            query = query,
            persons = ""


        )
      //  searchViewModel.onSearchTextChange("نمو")




    }*/

  //  val movies = searchViewModel.moviesFlow.collectAsLazyPagingItems()
    // ShowSearchMovies(movies,navController)


}



//////////
//Changing Search.....






@Composable
fun BottomSheetSearch(
    homeViewModel: HomeViewModel= hiltViewModel(),
    navController: NavHostController,
    filterViewModel: FilterViewModel= hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    tags:String="",
    categoryName:String="",
    categoryId:Int=1




)



{
    var scrollToIndex: (suspend (Int) -> Unit)? by remember { mutableStateOf(null) }
    val searchScope = rememberCoroutineScope()



    Log.d("search", "tag is... $tags")



  //  var tags:String=""


    // 🔴 Search Query State

    var searchQuery by remember { mutableStateOf("") } // 🔴 Search text state
    val gridState = rememberLazyGridState()







    // 🔴 Selected Filters State
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    var selectedCategories by remember { mutableStateOf(setOf("MOVIE,SERIES")) }
    var selectedCountryIds by remember { mutableStateOf(setOf<String>()) }

    var selectedGenreIds  by remember { mutableStateOf(setOf<String>()) }





    val selectedGenres by remember { mutableStateOf(setOf<String>("9999")) } // 🔴 Store full objects for UI



    var selectedActors by remember { mutableStateOf(setOf<String>()) }
    var selectedActorsIds by remember { mutableStateOf(setOf<String>()) }



    var selectedFromYear by remember { mutableStateOf<Int?>(null) }
    var selectedToYear by remember { mutableStateOf<Int?>(null) }
    var selectedAudioIds by remember { mutableStateOf(setOf<String>()) }
    var selectedSubtitleIds by remember { mutableStateOf(setOf<String>()) }


    var appliedFilters by remember { mutableStateOf<List<Filter>>(emptyList()) }

    // 🔴 API Query Parameters (Updated Dynamically)
    var tagsForApi by remember { mutableStateOf("") }
    var categoriesForApi by remember { mutableStateOf(emptyList<String>()) }
    var countriesForApi by remember { mutableStateOf("") }
    var genresForApi by remember { mutableStateOf("") }
    var actorsForApi by remember { mutableStateOf("") }
    var audioForApi by remember { mutableStateOf("") }
    var subTitleForApi by remember { mutableStateOf("") }



    val selectedCountries by remember { mutableStateOf(setOf<CountryInfo>()) } // 🔴 Store full objects for UI



    // 🔴 Bottom Sheet Visibility
    var isParentSheetVisible by remember { mutableStateOf(false) }
    var isChildSheetVisible by remember { mutableStateOf(false) }
    var selectedFilterType by remember { mutableStateOf<FilterType?>(null) }

    val contryList by filterViewModel.contries.collectAsState()
    val countryListState by filterViewModel.contries.collectAsState()

    val actorList = filterViewModel.actors.collectAsState()
    val genreList = filterViewModel.genres.collectAsState()

   // var shouldFetchGenres by remember { mutableStateOf(true) } // ✅ Boolean flag
   // val performSearch by remember { mutableStateOf(false) }
    var firstSearch by remember { mutableStateOf(false) }
    var isAllCheckBoxesclear by remember { mutableStateOf(false) }
    var showRemoveAllCheckBoxesIcon by remember { mutableStateOf(false) }
    var _selectedYearFrom by remember { mutableStateOf("1920") }
    var _selectedYearto by remember { mutableStateOf("2025") }
    val selectedYears = mutableListOf(_selectedYearFrom,_selectedYearto)
    var isYearSelectorVisible by remember { mutableStateOf(false) }






   /* PerformSearch(
        performSearch = true,
        navController = navController,
        query = searchQuery





    )*/







    val movies = searchViewModel.moviesFlow.collectAsLazyPagingItems()


    Log.d("search..",movies.itemCount.toString())













  /*  LaunchedEffect(performSearch) {
       *//* Log.d("search2","performing search......")
        Log.d("search2","performing search......")

        searchViewModel.updateSearchParams(
            countries = "",
            category = listOf("SERIES,MOVIES"),
            tag = tagsForApi,
            query = ""


        )
        searchViewModel.onSearchTextChange("")*//*









*//*
        if(firstSearch)
        {

            Log.d("search","performing firstsearch......")
            searchViewModel.updateSearchParams(
                countries = "",
                category = "SERIES,MOVIES",
                tag = tagsForApi,
                query = ""


            )

            firstSearch=false

        }

        else{
            Log.d("search","performing othersearch......")
            searchViewModel.updateSearchParams(
                countries = "",
                category = "SERIES,MOVIES",
                tag = tagsForApi,
                query = ""


            )
            firstSearch=false

        }*//*





        if (tags.isNotBlank()) {


            selectedGenreIds = selectedGenreIds.toMutableSet().apply {
                 add(categoryId.toString())
            }


            Log.d("gn", selectedGenreIds.toString())

            appliedFilters = appliedFilters.toMutableList().apply {
                add(categoryName.toString())

            }
        }

    }*/

  //  val moviesFlow = searchViewModel.moviesFlow.collectAsState()














    LaunchedEffect(Unit) {

           // searchViewModel.fetchCountries()
         //   filterViewModel.fetchGenres()





















    }
// 🔴 Fetch genres when screen is first opened
   /* LaunchedEffect(shouldFetchGenres) { // ✅ Runs when shouldFetchGenres changes
        if (shouldFetchGenres) {
            filterViewModel.fetchGenres()
            shouldFetchGenres = false // ✅ Prevent multiple API calls
        }
    }*/


    val actors = when (val state = actorList.value) {
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

    val genres = when (val state = genreList.value) {
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




















    // 🔴 Fetch Country List (Dynamic API Data)
    //   val countryList = homeViewModel.getCountries().collectAsState(initial = emptyList()).value


    // 🔴 Build Query Parameters When Filters Change
    LaunchedEffect(

         appliedFilters
       // selectedGenreIds,
       // selectedCategories,
      //  selectedCountryIds,
      //  selectedGenres,
     //   tags,

      //  selectedActors,
       // selectedActorsIds)
   // LaunchedEffect(Unit)


    )










    {

        Log.d("SearchLaunched","Performing Search......")




        if(selectedCategories.isEmpty())
        {

            categoriesForApi.plus("MOVIES,SERIES")


        }

        tagsForApi = selectedGenreIds.joinToString(",")



        categoriesForApi = selectedCategories.toList()

        countriesForApi = selectedCountryIds.joinToString(",")
        actorsForApi =selectedActorsIds.joinToString (",")
        audioForApi=selectedAudioIds.joinToString(",")
        subTitleForApi=selectedSubtitleIds.joinToString(",")


        //  homeViewModel.fetchCountries()
        Log.d("search", "ciuntries are" + contryList.data.toString())
        Log.d("search2", "tags are" + tagsForApi.toString())
        Log.d("search", "actors are" + actorsForApi.toString())


        searchViewModel.updateSearchParams(

            countries = countriesForApi,
            tag = tagsForApi,
            category = categoriesForApi,
            //query = searchQuery.ifEmpty { searchQuery1 }
            query = searchQuery,
            persons =actorsForApi,
            sounds = audioForApi,
            subtitle = subTitleForApi




        )



    }




    val searchText by searchViewModel.searchText.collectAsState()

    val isSearching by searchViewModel.isSearching.collectAsState()



    Log.d("search", "total... "+movies.itemCount.toString() )









    // 🔴 Fetch Movies Using Jetpack Paging (Triggers when query or filters change)
    val produmcts = searchViewModel.getMovies(
        tag = tagsForApi,
        categoryName = categoriesForApi,
        countries = countriesForApi,
        name = searchText,
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
                        // .background(Color.Blue)
                        .padding(paddingValues)
                        .padding(horizontal = 8.dp)

                )


                {

                   /* Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Magenta)
                        .height(50.dp)
                        .padding(bottom = 20.dp)
                        //.background(Color.Red),
                       // contentAlignment = Alignment.Center

                    )
                    {
                        Text("Search")


                      *//*  if(true) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp),
                                color = Color.Green



                            )
                        }*//*


                    }*/





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

/*
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

*/
























                    // 🔴 Search Bar with Badge
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                        Log.d("saerch","Composite "+movies.itemCount.toString())























                        SearchBarWithBadge1(

                            searchQuery = searchText,
                            // onSearchChange = { searchQuery = it }, // 🔴 Update search text
                            filterCount = appliedFilters.size,
                            onBadgeClick = {
                                isParentSheetVisible = true
                            },
                            onSuggestionClick = {

                                    selected ->
                                searchQuery =
                                    selected.name.toString() // Update the search field


                            },
                            //  suggestions = movies,
                            viewModel = searchViewModel,
                            tags =selectedGenreIds.joinToString(",") ,
                            countries =countriesForApi ,
                          //  genres = genresForApi,
                            actors = actorsForApi,
                            audio = selectedAudioIds.joinToString(",") ,
                            subtitle = selectedSubtitleIds.joinToString(",")


                            // 🔴 Open Parent Bottom Shee
                        )


                    }

                    // 🔴 Show Applied Filters Below Search Bar
                    if (appliedFilters.isNotEmpty() || tags.isNotBlank()) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            items(appliedFilters) { filter ->
                                FilterChip(text = filter.filterName, onRemove = {

                                    when(filter.filterType)
                                    {
                                        FilterType.GENRE->{
                                            Log.d("Filter2","removing ${filter.filterTagid}")
                                            filterViewModel.updateGenreCheckBoxState(filter.filterId,false)
                                            Log.d("Filter2","before removing ${selectedGenreIds.toString()}")

                                          selectedGenreIds=  selectedGenreIds.toMutableSet().apply {

                                                remove(filter.filterTagid)


                                            }
                                            Log.d("Filter2","after removing ${selectedGenreIds.toString()}")

                                        }
                                        FilterType.COUNTRY -> {
                                            Log.d("Filter2","removing ${filter.filterId}")
                                            Log.d("Filter2","before removing ${selectedCountryIds.toString()}")
                                            filterViewModel.updateCountryCheckBoxState(filter.filterName,false)


                                          selectedCountryIds=  selectedCountryIds.toMutableSet().apply {

                                                remove(filter.filterId)



                                            }
                                            Log.d("Filter2","after removing ${selectedCountryIds.toString()}")







                                        }
                                        FilterType.YEAR -> {

                                        }
                                        FilterType.ACTOR -> {


                                            filterViewModel.updateActorCheckBoxState(filter.filterId,false)
                                            selectedActorsIds=  selectedActorsIds.toMutableSet().apply {

                                                remove(filter.filterId)



                                            }



                                        }
                                        FilterType.SORT -> {}
                                        FilterType.AUDIO -> {
                                            filterViewModel.updateAudioCheckBoxState(filter.filterId,false)
                                            selectedAudioIds=  selectedAudioIds.toMutableSet().apply {

                                                remove(filter.filterId)



                                            }









                                        }
                                        FilterType.SUBTITLE ->{

                                            filterViewModel.updateSubtitleCheckBoxState(filter.filterId,false)
                                            selectedSubtitleIds=  selectedSubtitleIds.toMutableSet().apply {

                                                remove(filter.filterId)



                                            }







                                        }
                                    }

                                    appliedFilters = appliedFilters - filter



                                   // countriesForApi=""




                                    searchViewModel.updateSearchParams(
                                        "", "",
                                        category = emptyList() ,
                                        countries = "",
                                        persons = "",
                                        subtitle = "",
                                        sounds = ""
                                    )








                                }

                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    ShowSearchMovies(movies =movies, navController= navController,searchQuery=searchText,
                        onScrollCallbackReady = {
                            scrollToIndex?.let { callback ->

                                searchScope.launch {
                                    callback(15)
                                }


                            }


                        }, gridState = gridState


                        )








                }
            }
        )







        // 🔴 END OF SCAFFOLD 🔴

        // 🔴 Parent Bottom Sheet for Filter Selection
        MainFilterScreen  (
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(16.dp)),



            isVisible = isParentSheetVisible,
            onDismiss = { isParentSheetVisible = false },

           // selectedFilterType = selectedFilterType,

        )















        {
















            Column {
                Row( modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center



                )
                {
                    Text(
                        text = "فیلتر", // "Filters"
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)

                    )

                }


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
          val show = filterViewModel.isShowClearIconVisible.collectAsState()

          Log.d("Filter","FilterViewModel show value ${show.value}")
        // val checkBoxStates = remember { mutableStateMapOf<String,Boolean>()}
         val testname = remember { mutableStateOf("") }

        FilterBottomSheet (

            viewmodel = filterViewModel,
            modifier = Modifier,
            isVisible = isChildSheetVisible,
            onDismiss = { isChildSheetVisible = false },

            title = selectedFilterType?.label.toString(),
            onRemoveAllClick = {




                if(selectedFilterType==FilterType.GENRE)
                {

                    appliedFilters = appliedFilters.toMutableList().apply {
                        removeAll {
                            it.filterType==FilterType.GENRE


                          //  Log.d("OnRemoveAll", "remove $it")

                        }
                    }








                    filterViewModel.onRemoveAllClick(FilterType.GENRE)
                    filterViewModel.setCurrentFilter(FilterType.GENRE.name)


                   // filterViewModel.updateIconVisibility(false)






                    Log.d("OnRemoveAll","filters are ${appliedFilters.toString()}")
                }


                if(selectedFilterType==FilterType.COUNTRY)
                {

                    appliedFilters = appliedFilters.toMutableList().apply {
                        removeAll {
                            it.filterType==FilterType.COUNTRY


                            //  Log.d("OnRemoveAll", "remove $it")

                        }
                    }










                    filterViewModel.onRemoveAllClick(FilterType.COUNTRY)
                    filterViewModel.setCurrentFilter(FilterType.COUNTRY.name)
                   // filterViewModel.updateIconVisibility(false)



                    Log.d("OnRemoveAll","Genre Filter Clicked")
                }




                if(selectedFilterType==FilterType.ACTOR)
                {
                    filterViewModel.onRemoveAllClick(FilterType.ACTOR)
                    filterViewModel.setCurrentFilter(FilterType.ACTOR.name)

                    appliedFilters = appliedFilters.toMutableList().apply {
                        removeAll {
                            it.filterType==FilterType.ACTOR


                            //  Log.d("OnRemoveAll", "remove $it")

                        }
                    }






                    // filterViewModel.updateIconVisibility(false)



                    Log.d("OnRemoveAll","Genre Filter Clicked")
                }

                if(selectedFilterType==FilterType.AUDIO)
                {
                    filterViewModel.onRemoveAllClick(FilterType.AUDIO)
                    filterViewModel.setCurrentFilter(FilterType.AUDIO.name)


                    appliedFilters = appliedFilters.toMutableList().apply {
                        removeAll {
                            it.filterType==FilterType.AUDIO


                            //  Log.d("OnRemoveAll", "remove $it")

                        }
                    }




                    // filterViewModel.updateIconVisibility(false)



                    Log.d("OnRemoveAll","Genre Filter Clicked")
                }


                if(selectedFilterType==FilterType.SUBTITLE)
                {
                    filterViewModel.onRemoveAllClick(FilterType.SUBTITLE)
                    filterViewModel.setCurrentFilter(FilterType.SUBTITLE.name)

                    appliedFilters = appliedFilters.toMutableList().apply {
                        removeAll {
                            it.filterType==FilterType.SUBTITLE


                            //  Log.d("OnRemoveAll", "remove $it")

                        }
                    }








                    // filterViewModel.updateIconVisibility(false)



                    Log.d("OnRemoveAll","Genre Filter Clicked")
                }













                Log.d("OnRemoveAll","OnRemoveAllClcik")














            },
            showIcon = show.value,


            onCloseParanetSheetClick = { isChildSheetVisible=false },




            //isParentVisible = isParentSheetVisible

        )

        {

            Column {








                }





                when (selectedFilterType) {

                    FilterType.GENRE -> {

                        filterViewModel.setCurrentFilter(FilterType.GENRE.name)






















                            Log.d("Filter4","selected genres  ${selectedGenreIds.size}")










                            val selectedItemIds = remember { mutableSetOf<String>() }
                            Log.d("Filter","selected genres  ${selectedGenreIds.size}")
                            Log.d("Filter","selected genres is empty  ${selectedGenreIds.isEmpty()}")
                        if (genres != null) {
                            GenreSelectionSheet(
                                items = genres,

                                filterViewModel = filterViewModel, // ✅ Pass ViewModel
                                // items = genres,
                                selectedItemIds = selectedGenreIds.toMutableSet(),
                                onItemSelected = { genre, isSelected ->

                                    //  filterViewModel.updateCheckBoxSate(genre.translatedName,isSelected)


                                    //AddGenre
                                    selectedGenreIds = selectedGenreIds.toMutableSet().apply {

                                      //  filterViewModel.updateCheckBoxSate(genre.translatedName, isSelected)


                                        if (isSelected) {

                                            Log.d("Filter"," filter view model  selcted is true selected genres ${genre.name}   ${filterViewModel.getCheckState(genre.translatedName)}")


                                            Log.d("Filter3","isSelected genres  ${genre.translatedName}")
                                            testname.value="hi"
                                            add(genre.id.toString())
                                            Log.d("Filter1","isSelected genres  ${selectedGenreIds.size}")





                                            Log.d("Filter3","isSelected genres ${genre.translatedName}  ${filterViewModel.getCheckState(genre.translatedName)}")
                                            // Log.d("Filter2","isSelected genres ${genre.name}  ${checkBoxStates["تریلر"]}")


                                            //  if(selectedGenreIds.size>=0)

                                         //   filterViewModel.upc(true)




                                        } else {
                                           // filterViewModel.updateCheckBoxSate(genre.translatedName,false)
                                            Log.d("Filter"," filter view model selcted is false selected genres ${genre.name}   ${filterViewModel.getCheckState(genre.translatedName)}")



                                            Log.d("Filter3","isSelected genres ${genre.translatedName}  ${filterViewModel.getCheckState(genre.translatedName)}")



                                            remove(genre.id.toString())
                                            Log.d("Filter3","isSelected genres ${genre.translatedName}  ${filterViewModel.getCheckState(genre.translatedName)}")

                                            Log.d("Filter1","isSelected genres  ${selectedGenreIds.size}")
                                            //  Log.d("Filter1","isSelected genres ${genre.name}  ${checkBoxStates[genre.name]}")

                                           // filterViewModel.updateCheckBoxSate(genre.translatedName,false)
                                            if(selectedGenreIds.size==1) {
                                               // filterViewModel.updateIconVisibility(false)

                                            }


                                        }
                                        Log.d("Filter3","isSelected genres!!! ${genre.translatedName}  ${filterViewModel.getCheckState(genre.translatedName)}")


                                    }

                                    //AplyFilter

                                    appliedFilters = appliedFilters.toMutableList().apply {
                                        if (isSelected)
                                            add( Filter(
                                                filterName = genre.name,
                                                filterType = FilterType.GENRE,
                                                filterId = genre.translatedName,
                                                filterTagid = genre.id


                                            ) )
                                                else {
                                            remove(
                                                Filter(
                                                    filterName = genre.name,
                                                    filterType = FilterType.GENRE,
                                                    filterId = genre.translatedName

                                                )
                                            )




                                        }
                                    }
                                },


                                //   filterViewModel.updateIconVisibility(true)


                                // showRemoveAllCheckBoxesIcon=true


                                //filterViewModel.updateIconVisibility(false)


                                onClose = {
                                    isChildSheetVisible = false

                                },
                                isAllClear = isAllCheckBoxesclear,
                                onClearAll = isAllCheckBoxesclear,
                                onClear = {


                                    Log.d("Filter",  "on clear  trailer state is ${filterViewModel.getCheckState("Thriller")}" )

                                    Log.d("Filter","OnClear On Genre Clicked" )
                                    //  filterViewModel.updateIconVisibility(true)
                                    showRemoveAllCheckBoxesIcon=true

                                    //  selectedGenreIds.toMutableSet().clear()


                                },
                                // selectedCheckBoxes = filterViewModel.checkBoxStates.value

                            )
                        }



                        Log.d("Filter",  "trailer state is ${filterViewModel.getCheckState("Thriller")}" )
                        Log.d("Filter",  "trailer state is ${testname.value}" )


                    }


                    FilterType.SUBTITLE -> {
                        filterViewModel.setCurrentFilter(FilterType.SUBTITLE.name)
                        FilterSubtitleSelectionSheet(
                            filterViewModel = filterViewModel,
                            selectedSubtitleIds = selectedSubtitleIds,
                            onSubtitleSelected = { sub, isSelected ->
                                selectedSubtitleIds = selectedSubtitleIds.toMutableSet().apply {
                                    if (isSelected) add(sub.id) else remove(sub.id)
                                }

                                appliedFilters =

                                  appliedFilters.toMutableList().apply {

                                      if (isSelected)
                                          add( Filter(filterName = sub.name,
                                              filterType = FilterType.SUBTITLE,
                                              filterId = sub.id

                                          ) )
                                      else remove(
                                          Filter(filterName = sub.name,
                                              filterType = FilterType.SUBTITLE,
                                              filterId = sub.id



                                          )
                                      )


                                  }





                            },



                            // audioSubtitles = AUDIO_SUBTITLES,




                            onClose = { isChildSheetVisible = false }

                        )


                    }


                    FilterType.COUNTRY -> {
                        filterViewModel.setCurrentFilter(FilterType.COUNTRY.name)


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
                                        filterViewModel = filterViewModel,
                                        title = "کشور سازنده",
                                        items = it.countryInfo,
                                        selectedItemIds = selectedCountryIds,
                                        onItemSelected = { country, isSelected ->


                                            selectedCountryIds =
                                                selectedCountryIds.toMutableSet().apply {
                                                    if (isSelected) {

                                                       // filterViewModel.updateIconVisibility(true)
                                                        add(country.id.toString())
                                                       /* searchViewModel.updateSearchParams(
                                                            query = searchQuery,
                                                            tag =tagsForApi,
                                                            category = categoriesForApi,
                                                            countries = countriesForApi,
                                                            persons = actorsForApi



                                                        )*/




                                                    }




                                                    else
                                                    {

                                                        if(selectedCountryIds.size==1) {
                                                          //  filterViewModel.updateIconVisibility(false)

                                                        }
                                                        remove(
                                                            country.id.toString()
                                                        )
                                                    }

                                                }
                                            /*   countriesForApi = selectedCountryIds.joinToString(",")
                                               // 🔴 Update applied filters to show country names*/
                                            appliedFilters =
                                                appliedFilters.toMutableList().apply {
                                                    if (isSelected)
                                                        add( Filter(filterName = country.name,
                                                            filterType = FilterType.COUNTRY,
                                                            filterId = country.id.toString()

                                                            ) )
                                                    else remove(
                                                        Filter(filterName = country.name,
                                                            filterType = FilterType.COUNTRY,
                                                            filterId = country.id.toString()


                                                        )
                                                    )
                                                }

                                            // 🔴 Update API query parameter
                                          /*  countriesForApi =
                                                selectedCountryIds.joinToString(",")*/

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
                            filterViewModel.setCurrentFilter(FilterType.ACTOR.name)
                            FilterActorsSelectionSheet(
                                filterViewModel = filterViewModel,
                                title = "انتخاب بازیگر",
                                actors = actors,
                                selectedActorIds = selectedActorsIds,
                                onActorSelected = { actor, isSelected ->
                                    selectedActorsIds = selectedActorsIds.toMutableSet().apply {
                                        if (isSelected) add(actor.id.toString()) else remove(
                                            actor.id.toString()
                                        )
                                    }

                                    Log.d("act","actor ids $selectedActorsIds",)


                                    appliedFilters = appliedFilters.toMutableList().apply {
                                        if (isSelected)
                                            add( Filter(filterName = actor.name,
                                                filterType = FilterType.ACTOR,
                                                filterId = actor.id.toString()


                                            ) )
                                        else remove(
                                            Filter(filterName = actor.name,
                                                filterType = FilterType.ACTOR,
                                                filterId = actor.id.toString()


                                            )
                                        )
                                    }


                                    actorsForApi =
                                        selectedActorsIds.joinToString(",")


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


                       /* YearSelectionSheet(
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
                        )*/


                        YearSelectionSheet2(
                            isVisible = isYearSelectorVisible,
                            data =listOf(1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000) ,
                            onYearSelected = {selectedItem->

                              if(selectedYears[0]=="1")

                             _selectedYearFrom=selectedItem
                              else
                              if(selectedYears[1]=="2")
                             _selectedYearto=selectedItem





                            isYearSelectorVisible=false


                              //  selectedYears






                            },
                            selectedFromYear = _selectedYearFrom.toInt(),
                            selectedToYear = _selectedYearto.toInt(),


                            onClose = {},
                            onYearClick = {
                                Log.d("year","Clicked $it")
                                isYearSelectorVisible=true

                                 when(it)
                                 {
                                     1->{ selectedYears[0]="1" }
                                     2->{selectedYears[1]="2"}



                                 }




                               // isYearSelectorVisible=!isYearSelectorVisible




                            },
                            //selectedYears = selectedYears




                        )









                    }


                    FilterType.AUDIO -> {
                        filterViewModel.setCurrentFilter(FilterType.AUDIO.name)
                        FilterAudioSelectionSheet(
                            filterViewModel = filterViewModel,
                            selectedAudioIds = selectedAudioIds,
                            onAudioSelected = { audio, isSelected ->
                                selectedAudioIds = selectedAudioIds.toMutableSet().apply {
                                    if (isSelected) add(audio.id) else remove(audio.id)
                                }
                                appliedFilters =
                                    appliedFilters.toMutableList().apply {

                                        if (isSelected)
                                            add( Filter(filterName = audio.name,
                                                filterType = FilterType.AUDIO,
                                                filterId = audio.id

                                            ) )
                                        else remove(
                                            Filter(filterName = audio.name,
                                                filterType = FilterType.AUDIO,
                                                filterId = audio.id



                                            )
                                        )



                                    }


                            },

                            // audioSubtitles = AUDIO_SUBTITLES,

                            onClose = { isChildSheetVisible = false }

                        )


                    }


                    else -> {}
                }
            }
        }







@Composable
private fun ShowSearchMovies(

    movies: LazyPagingItems<MovieResult.DataMovie.Item>,
    navController: NavHostController,
    onScrollCallbackReady: (suspend (Int) -> Unit) -> Unit,
    searchQuery: String,
    gridState: LazyGridState,
  //  onMovieClick: (MovieResult.DataMovie.Item) -> Unit ,

)

{
   val refreshstate= movies.loadState.refresh
   val appendstate= movies.loadState.append
    val gridstate = rememberLazyGridState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    Log.d("Scroll", searchQuery)




    LaunchedEffect(searchQuery) {

        coroutineScope.launch {
            Log.d("Scroll", "launchh scrol")
            val index = (0 until movies.itemCount).firstOrNull { idx ->
                movies.peek(idx)?.name?.contains(searchQuery, ignoreCase = true) == true
            }

            if (index != null) {
                gridState.animateScrollToItem(index)
            } else {
                // Optional: handle not found
                Log.d("Scroll", "Item not found")
            }

        }



    }





/*
LaunchedEffect(Unit) {
    onScrollCallbackReady{ index->

        coroutineScope.launch {
            gridstate.animateScrollToItem(15)
        }


    }



}
*/








     when(refreshstate) {
         is LoadState.Loading -> {
             Log.d("loadingsate", "REFRESH")
             Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                 CircularProgressIndicator()
             }
         }

         is LoadState.Error -> {}

         else -> {
             Log.d("loadingsate", "REFRESH ELSE..")



             LazyVerticalGrid(
                 state = gridstate,
                 columns = GridCells.Fixed(2),
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(16.dp)
                 ,
                 contentPadding = PaddingValues(4.dp),
                 verticalArrangement = Arrangement.spacedBy(32.dp),
                 horizontalArrangement = Arrangement.spacedBy(8.dp)



             )

             {
                 items(movies.itemCount) { index ->

                     when(movies[index]?.category)
                     {

                         "MOVIES"-> {
                             movies[index]?.let {
                                 MovieCard(
                                     movie = it,
                                     onClick = { movies[index]?.let {  movie->   navController.navigate(Screen.ProductDetails.withArgs("${movie.id}")) }

                                         Log.d("category clicked..",movies.get(index)?.name.toString())



                                     }
                                 )
                                 Log.d("category","Movies")

                             }
                         }


                         "SERIES" -> {
                             movies[index]?.let {
                                 SeriesCard(
                                     info = it,
                                     onClick = { movies[index]?.let {
                                         series -> navController.navigate(Screen.SeriesDetailScreen.withArgs("${series.id}"))



                                     } })
                                 Log.d("category","Movies")


                             }















                     }
                     else ->
                     {



                         movies[index]?.let {
                             MovieCard(
                                 movie = it,
                                 onClick = { movies[index]?.let {
                                         movie->   navController.navigate(Screen.ProductDetails.withArgs("${movie.id}"))


                                 } }
                             )
                             Log.d("category","others")

                         }








                     }






                     }





                  /*   Log.d("loadingsate", "REFRESH ELSE..${movies.itemCount}")


                     val movie = movies[index]
                     if (movie != null) {
                         *//* MovieItem(
                                              movieTitle = movie.name.toString(),
                                              rating = movie.popularityRate.toString()
                                          )*//*

                         MovieCard(movie = movie) {

                             movies[index]?.let {

                                 navController.navigate(Screen.ProductDetails.withArgs("${movie.id}"))


                             }


                         }
                         Text("$index")*/

                        // item { Text("000000000000") }


                     }
                 }




/*
                 if (movies.loadState.append is LoadState.Loading) {
                     Log.d("loadingsate", "append  grid..")

                     item {
                         Box(
                             Modifier
                                 .fillMaxWidth()
                                 .padding(16.dp),
                             contentAlignment = Alignment.Center
                         ) {
                             CircularProgressIndicator()
                         }
                     }
                 }*/







             }



         }

     }













/////LazyGrid

   /* LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    )

    {
        items(movies.itemCount) { index ->


            val movie = movies[index]
            if (movie != null) {
                *//* MovieItem(
                                     movieTitle = movie.name.toString(),
                                     rating = movie.popularityRate.toString()
                                 )*//*

                MovieCard(movie = movie) {

                    movies[index]?.let {

                        navController.navigate(Screen.ProductDetails.withArgs("${movie.id}"))


                    }


                }


            }
        }

        // 🔴 Show Loading Indicator While Fetching More Data
        movies.apply {
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

        *//* products.apply {
                        when {
                            loadState.append is LoadState.Loading -> {

                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }




                            loadState.refresh is LoadState.Loading -> {
                                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                            }
                        }*//*


        // 🔴 Movie Grid with Pagination
        *//*   LazyVerticalGrid(
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
                    }*//*
    }*/



@Composable
    fun DynamicMoviesGrid(
        products: LazyPagingItems<MovieResult.DataMovie.Item>, // A list of mixed data types (movies and informational items)
        modifier: Modifier = Modifier,
        onMovieClick: (MovieResult.DataMovie.Item) -> Unit = {}, // Handles movie card clicks
        onInfoClick: (MovieResult.DataMovie.Item) -> Unit = {} // Handles informational card clicks
    )
    {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
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
//badge1
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBarWithBadge6(
        searchQuery: String,
        //onSearchChange: (String) -> Unit,
        filterCount: Int,
        onBadgeClick: () -> Unit,
       // suggestions: LazyPagingItems<MovieResult.DataMovie.Item>,
        onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit,
        viewModel: SearchViewModel
    ) {
        var expanded by remember { mutableStateOf(false) }
        var previousQuery by remember { mutableStateOf("") }
        val isSearching by viewModel.isSearching.collectAsState()

        val suggestions = viewModel.moviesFlow1.collectAsLazyPagingItems() // ✅ Live updates


        LaunchedEffect(suggestions.itemCount) {
            expanded = searchQuery.isNotEmpty() && suggestions.itemCount > 0
        }



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
                        ///** search text field
                        /*

                        TextField(
                            value = searchQuery,
                            onValueChange = {


                                //onSearchChange(it)
                                viewModel.onSearchTextChange(it)
                                expanded = it.isNotEmpty() && suggestions.itemCount > 0
                            } ,
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


                     ///////
                     */


                        TextField(
                            value = searchQuery,
                            onValueChange = { newQuery ->
                                viewModel.onSearchTextChange(newQuery)

                                // ✅ Only expand if a new search is being performed
                                if (newQuery.length > previousQuery.length) { // If user is typing more
                                  //  expanded = newQuery.isNotEmpty() && suggestions.itemCount > 0
                                    expanded=newQuery.isNotEmpty()
                                } else if (newQuery.isBlank()) { // If input is cleared, close suggestions
                                    expanded = false
                                }

                                previousQuery = newQuery // ✅ Update previous query
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



                        if (isSearching) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        }
































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
                    AnimatedVisibility(visible = expanded && !!isSearching) {
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






/////Search box 1
@Composable
fun SearchBarWithBadge7(
    searchQuery: String,
    filterCount: Int,
    onBadgeClick: () -> Unit,
    onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit,
    viewModel: SearchViewModel,
   //Genres is tags
    tags:String,
    countries:String,
   // genres:String,
    actors:String,
    audio:String,
    subtitle: String,





)
{
    var expanded by remember { mutableStateOf(false) }
    val searchResults = viewModel.moviesFlow1.collectAsLazyPagingItems()
    val isLoading by viewModel.isSearching.collectAsState()



    Log.d("SearchQuery", "selected tags are $tags" )
    Log.d("SearchQuery", "selected countries are $countries" )
    Log.d("SearchQuery", "selected Actors are $actors" )
    Log.d("SearchQuery", "selected Audios are $audio" )
    Log.d("SearchQuery", "selected Subtitles are $subtitle" )







    // ✅ Expand when new search results arrive
    LaunchedEffect(searchResults.itemCount) {
        expanded = searchQuery.isNotEmpty() && searchResults.itemCount > 0
    }
    Log.d("loading","is laoding first $isLoading")

    Row(modifier = Modifier,
    verticalAlignment = Alignment.CenterVertically

    )
    {

        Row(modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.Green)
            ,
            // horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically



        )


        {


            Column(modifier = Modifier) {





                Row(
                    Modifier
                        .background(Color.Yellow)
                        .padding(vertical = 10.dp)
                    ,

                    verticalAlignment = Alignment.CenterVertically,
                    // horizontalArrangement = Arrangement.SpaceBetween



                )


                {
                    Card() {
                        Column {
                            Row(
                                modifier = Modifier

                                ,

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            )

                            {
                                TextField(
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    shape = MaterialTheme.shapes.large,


                                    value = searchQuery,
                                    onValueChange = {
                                        viewModel.onSearchTextChange(it)
                                        expanded = it.isNotEmpty()
                                    },

                                    placeholder = { Text("جستجو...") },
                                    leadingIcon = {Icon(painterResource(R.drawable.search),"",
                                        modifier = Modifier.size(24.dp)


                                    )}


                                )















                            }



                            // ✅ Show Loading Indicator While Searching
                            if (isLoading) {

                                Log.d("loading","is laoding $isLoading")
                                Row(
                                    //modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator(color = Color.Red)
                                }
                            }

                            // ✅ Show Search Suggestions
                            AnimatedVisibility(visible = expanded && !isLoading) {
                                Card(modifier = Modifier) {
                                    LazyColumn {
                                        items(searchResults.itemCount) { index ->
                                            searchResults[index]?.let { movie->
                                                SuggestionTextItem(
                                                    movie.name.toString(),
                                                    onClick = {
                                                        onSuggestionClick(movie)
                                                        expanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    /* IconButton(onClick = onBadgeClick,
                         modifier = Modifier.background(Color.Black)


                     ) {

                     }*/












                }


















            }






        }


        Box( modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth(),

            contentAlignment = Alignment.Center

        )




        {

            BadgedBox(
                badge = {
                    if (filterCount > 0) {
                        Badge(
                            modifier = Modifier,
                            containerColor = Color.Red



                        ) {

                            Text("$filterCount")


                        }
                    }
                },
                modifier = Modifier.clickable (
                    onClick = onBadgeClick


                )



            ) {
                Icon(
                    painter = painterResource(R.drawable.filter),
                    contentDescription = "Filter",
                    tint = if(filterCount>0) Color.Red else Color.White,
                    modifier = Modifier.size(48.dp)



                )
            }




        }





    }















}




@Composable
fun SearchBarWithBadge1(
    searchQuery: String,
    filterCount: Int,
    onBadgeClick: () -> Unit,
    onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit,
    viewModel: SearchViewModel,
    tags: String,
    countries: String,
    actors: String,
    audio: String,
    subtitle: String,
                   // 🔹 Keep badge Y position fixed



) {

    Log.d("search","search tag is........$tags")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp  // 🔹 Get dynamic screen width
    val badgeXPosition = screenWidth * 0.80f          // 🔹 Adjust badge position (85% of screen width)
    val badgeYPosition = 20.dp




    var expanded by remember { mutableStateOf(false) }
    val searchResults = viewModel.moviesFlow.collectAsLazyPagingItems()


    val isLoading by viewModel.isSearching.collectAsState()

    LaunchedEffect(searchResults.itemCount, tags) {
        expanded = searchQuery.isNotEmpty() && searchResults.itemCount > 0

      /*  if(tags.isNotBlank())
        {
            viewModel.getMovieFlow(tags)

        }*/


    }

    Box(modifier = Modifier.fillMaxWidth()



    ) {
        // 🔹 Search Bar Section
        Row(
            modifier = Modifier
                .fillMaxWidth(),
              //  .background(Color.Green),
              // .padding(end = 30.dp), // Ensures space for the fixed badge
                verticalAlignment = Alignment.CenterVertically
        )

        {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        //.background(Color.Yellow)
                        .padding(vertical = 10.dp)
                       // .padding(horizontal = 5.dp)
                    ,

                    verticalAlignment = Alignment.CenterVertically
                )

                {
                    SearchBox(
                        searchQuery,
                        viewModel,

                        isLoading,
                        searchResults,
                        onSuggestionClick ={it.name}
                    )
                }
            }
        }


        // 🔥 FIXED BADGE POSITION
        Box(
            modifier = Modifier
                .absoluteOffset(
                    x = -badgeXPosition,
                    y = badgeYPosition
                )// 🔹 Keep it at a fixed position
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            BadgedBox(
                badge = {
                    if (filterCount > 0) {
                        Badge(containerColor = Color.Red) {
                            Text("$filterCount")
                        }
                    }
                },
                modifier = Modifier.clickable(onClick = onBadgeClick)

                 //   .offset(x = (-18).dp, y = (-4).dp) // move right & up

            )

            {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.Start

                )
                {

                }
                Icon(
                    painter = painterResource(R.drawable.filter),
                    contentDescription = "Filter",
                    tint = if (filterCount > 0) Color.White else Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


@Composable
private fun SearchBox(
    searchQuery: String,
    viewModel: SearchViewModel,
    //expanded: Boolean,
    isLoading: Boolean,
    searchResults: LazyPagingItems<MovieResult.DataMovie.Item>,
    onSuggestionClick: (MovieResult.DataMovie.Item) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(searchQuery) }






   // var expanded1 = expanded
    Card() {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 5.dp)

                ,
                verticalAlignment = Alignment.CenterVertically,

                //horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    shape = MaterialTheme.shapes.medium,
                    //value = if(searchQuery=="نمو") "" else searchQuery,
                    value = textFieldValue,
                    onValueChange = {
                        viewModel.updateSearchParams(
                            query = it,
                            tag = "",
                            category = listOf("MOVIE,SERIES,COURSE,CERTIFICATED_COURSE"),
                            countries = "",
                            persons = "",
                            sounds = "",
                            subtitle = ""
                        )
                        viewModel.onSearchTextChange(it)


                        expanded = it.isNotEmpty()
                        textFieldValue=it


                    },
                    placeholder = { Text("جستجو...") },
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.search),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(

                        focusedIndicatorColor = MaterialTheme.colorScheme.searchIndicatorLine,      // active line
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.searchIndicatorLine,    // default line
                        disabledIndicatorColor = MaterialTheme.colorScheme.searchIndicatorLine






                    )
                )
            }

            // 🔹 Show Loading Indicator While Searching
            if (isLoading) {
                Row(horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }

            // 🔹 Show Search Suggestions
            AnimatedVisibility(visible = expanded && !isLoading) {
                Log.d("search", "search result ${searchResults.itemCount}")

                Card(modifier = Modifier.height(100.dp)) {
                    LazyColumn {
                        items(searchResults.itemCount) { index ->
                            searchResults[index]?.let { movie ->
                                SuggestionTextItem(
                                    movie.name.toString(),
                                    onClick = {
                                        onSuggestionClick(
                                         movie


                                        )
                                        expanded = false
                                       textFieldValue=movie.name.toString()

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


@Composable
    fun SuggestionTextItem(text: String, onClick: () -> Unit) {
        Text(
            text = text,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp),
              //  .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White


        )
    }

@Composable
fun ShowBadgeBox(
    onClick:()->Unit,
    filterCounter: Int


)

{
    BadgedBox(
        badge = {
            if (filterCounter > 0) {
                Badge(
                    modifier = Modifier,
                    containerColor = Color.Red



                ) {

                    Text("$filterCounter")


                }
            }
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.filter),
            contentDescription = "Filter",
            tint = if(filterCounter>0) Color.Red else Color.White,
            modifier = Modifier.size(24.dp)


        )
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














