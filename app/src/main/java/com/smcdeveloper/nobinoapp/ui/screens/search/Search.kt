package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.ui.screens.demo.FilterListItem


import kotlinx.coroutines.launch














@Composable
fun Search(navController: NavHostController,
    viewModel: FilterViewModel= hiltViewModel()

) {




























   // SearchPage(navController = navController)
    //ShowButtonSheet()

   // ShowContent()

    SearchScreen(viewModel)


}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShowButtonSheet() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isSheetOpen by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0) { 2 }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }


































    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){

        Scaffold() {

                it->
            Text(
                "Android",
                modifier = Modifier.padding(it)
            )



        }












        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isSheetOpen = true
                        sheetState.show()
                    }
                }
            ) {
                Text("Click ME")
            }

            if (isSheetOpen) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(550.dp),
                    containerColor = Color(0xffF0F3FF),
                    onDismissRequest = {
                        isSheetOpen = false
                    },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        //   Header("سفارشی سازی")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 5.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Tab 1: Default Order
                            CustomTab(
                                text = "سفارش عادی",
                                isSelected = pagerState.currentPage == 0,
                                onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Tab 2: Bulk Order
                            CustomTab(
                                text = "سفارش عمده",
                                isSelected = pagerState.currentPage == 1,
                                onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                            )
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            when (page) {
                                0 -> Text("---------")
                                1 -> Text("Bulk Order Content")
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
fun SearchScreen(viewModel: FilterViewModel)
{

    val coroutineScope = rememberCoroutineScope()

    // States for parent and child bottom sheets
    val parentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val childSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // State for determining which nested sheet to open
    var currentNestedSheet by remember { mutableStateOf<FilterType?>(null) }

    // 🔴 START OF SCAFFOLD 🔴
    Scaffold(

    )
    { paddingValues ->
        // Main content with Search Bar and Badge
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            // Search Bar and Badge
            SearchBarWithBadge(
                filterCount = viewModel.getFilterCount(),
                onBadgeClick = { coroutineScope.launch { parentSheetState.show() } }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main screen placeholder content
            Text(
                text = "Explore Filters and Results Here",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }


    ModalBottomSheet(
        sheetState = parentSheetState,
        onDismissRequest = { coroutineScope.launch { parentSheetState.hide() } },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        SearchCriteriaModal(
            onFilterSelected = { filterType ->
                currentNestedSheet = filterType
                coroutineScope.launch {
                    parentSheetState.hide() // Hide parent sheet
                    childSheetState.show()  // Show child sheet
                }
            }
        )
    }














}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentModalDemo() {
    val coroutineScope = rememberCoroutineScope()

    // State to control the visibility of the Parent Modal
    val parentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Parent ModalBottomSheet
    ModalBottomSheet(
        sheetState = parentSheetState,
        onDismissRequest = { coroutineScope.launch { parentSheetState.hide() } },
        containerColor = MaterialTheme.colorScheme.surface // Customize the modal background color
    ) {
        // Content inside the Parent Modal
      //  ParentModalContent()
    }

    // Button to open the modal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { coroutineScope.launch { parentSheetState.show() } } // Show the modal
        ) {
            Text("Open Parent Modal")
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
            Text("فیلتر", style = MaterialTheme.typography.titleLarge) // "Filter"
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 List of Filter Options
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val filterOptions = listOf(
                FilterType.GENRE to Icons.Default.Folder,
                FilterType.COUNTRY to Icons.Default.Public,
                FilterType.YEAR to Icons.Default.DateRange,
              //  FilterType.ACTOR to Icons.Default.Person,
              //  FilterType.SORT to Icons.Default.Sort,
              //  FilterType.AUDIO to Icons.Default.VolumeUp,
              //  FilterType.SUBTITLE to Icons.Default.Subtitles
            )

            items(filterOptions) { (filterType, icon) ->
                FilterListItem(filterType, icon, onOpenChild)
            }
        }
    }
}











@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowContent()
{

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { Icon(Icons.Filled.BrokenImage, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    )
    { contentPadding ->
        Text(
            "Android",
            modifier = Modifier.padding(contentPadding)
        )
        // Screen content

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }









}







@Composable
fun CustomTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(45.dp)
            .width(180.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xff24A751),
                    unselectedColor = Color.DarkGray
                ),
                selected = isSelected,
                onClick = onClick
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}









@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(viewModel: FilterViewModel= hiltViewModel(),
               navController: NavHostController



) {

    val coroutineScope = rememberCoroutineScope()

    // Bottom sheet states
    //val sheetState = rememberModalBottomSheetState()
    //val nestedSheetState = rememberModalBottomSheetState()

    val parentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val childSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        bottomBar = {

            BottomNavigationBar(

                onSearchClicked = { coroutineScope.launch { parentSheetState.show() } }

            )



        }
    )
    { paddingValues ->
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Main Content Here", color = MaterialTheme.colorScheme.onSurface)
        }
    }



















    // State for determining which nested sheet is open
    var currentNestedSheet by remember { mutableStateOf<FilterType?>(null) }

    // Main Search Page

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(0.7f),
        sheetState = parentSheetState,
        onDismissRequest = { coroutineScope.launch { parentSheetState.hide() } },
        containerColor = Color.Red
    ) {
        SearchCriteriaModal(
            onFilterSelected = { filterType ->
                currentNestedSheet = filterType // Set the selected filter type
                coroutineScope.launch {
                    parentSheetState.hide() // Hide parent sheet
                    childSheetState.show()  // Show child sheet
                }
            }
        )
    }

    // Nested Modal Bottom Sheet
    ModalBottomSheet(
        sheetState = childSheetState,
        onDismissRequest = { coroutineScope.launch { childSheetState.hide() } },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        when (currentNestedSheet) {
            FilterType.GENRE -> GenreModal(viewModel) { coroutineScope.launch { childSheetState.hide() } }
            FilterType.YEAR -> YearModal(viewModel) { coroutineScope.launch { childSheetState.hide() } }
            else -> {}
        }
    }

    // Main Search Content
    Column( // Main page content
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar with Badge Button
        SearchBarWithBadge(
            filterCount = viewModel.getFilterCount(),
            onBadgeClick = { coroutineScope.launch { parentSheetState.show() } }
        )
    }
}






@Composable
fun BottomNavigationBar(onSearchClicked: () -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { /* Handle Home */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = true,
            onClick = { onSearchClicked() } // Open the modal
        )
    }
}






















@Composable
fun SearchCriteriaModal(onFilterSelected: (FilterType) -> Unit) {
    val filterTypes = listOf(
        FilterType.GENRE to "Genre",
        FilterType.YEAR to "Year"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        items(filterTypes) { (filterType, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFilterSelected(filterType) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



@Composable
fun GenreModal(viewModel: FilterViewModel, onClose: () -> Unit) {
    val selectedGenres = remember { mutableStateOf(viewModel.filterCriteria.value.selectedGenres) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Select Genres",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            val genres = listOf("Action", "Drama", "Comedy", "Horror", "Fantasy")
            items(genres) { genre ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (genre in selectedGenres.value) {
                                selectedGenres.value = selectedGenres.value - genre
                            } else {
                                selectedGenres.value = selectedGenres.value + genre
                            }
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = genre in selectedGenres.value,
                        onCheckedChange = {
                            if (it) {
                                selectedGenres.value = selectedGenres.value + genre
                            } else {
                                selectedGenres.value = selectedGenres.value - genre
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = genre, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }

        Button(
            onClick = {
                viewModel.updateGenres(selectedGenres.value)
                onClose()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Save Genres")
        }
    }
}



@Composable
fun YearModal(viewModel: FilterViewModel, onClose: () -> Unit) {
    var startYear by remember { mutableStateOf<String?>(null) }
    var endYear by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Select Years",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Start Year:", color = MaterialTheme.colorScheme.onSurface)
            Text(text = startYear ?: "Select", color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "End Year:", color = MaterialTheme.colorScheme.onSurface)
            Text(text = endYear ?: "Select", color = MaterialTheme.colorScheme.primary)
        }

        LazyColumn {
            val years = (2000..2025).map { it.toString() }
            items(years) { year ->
                Text(
                    text = year,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (startYear == null) startYear = year
                            else endYear = year
                        }
                        .padding(8.dp),
                    color = if (year == startYear || year == endYear) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Button(
            onClick = {
                viewModel.updateYears(listOfNotNull(startYear, endYear).toSet())
                onClose()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Save Years")
        }
    }
}























@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithBadge(filterCount: Int, onBadgeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
               // containerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        BadgedBox(
            badge = {
                Badge { Text("$filterCount") }
            }
        ) {
            IconButton(onClick = onBadgeClick) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filters",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
























@Composable
fun SearchPage1(onRowClick: (String) -> Unit, filterCount: Int) {
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
            colors = TextFieldDefaults.colors(
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
        colors = TextFieldDefaults.colors(
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
            .clickable {
                onRowClick(label)





            }
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



























