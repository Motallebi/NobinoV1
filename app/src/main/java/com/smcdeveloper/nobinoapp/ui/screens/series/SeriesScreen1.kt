package com.smcdeveloper.nobinoapp.ui.screens.series

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.Section
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.MovieCardtestByTag
import com.smcdeveloper.nobinoapp.ui.component.NobinoGradientCard
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRowBySection
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRowBySection2

import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.Constants
import com.smcdeveloper.nobinoapp.util.LocalelUtils
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Composable
fun SeriesScreen(navController: NavHostController)

{
    //Series(navController=navContro( onSectionClick = {})
    Text("SERIES.......")
   // SectionTest()

    SectionListScreen(navController = navController,
        onMovieClick = {}
        )


}

@Composable
fun SectionListScreen3(
    viewModel: SeriesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit, // Callback for navigating to movie details
    navController: NavHostController
)
{
    val sectionsResult by viewModel.sections.collectAsState() // Observe sections
    val moviesByTagsResult by viewModel.moviesByTags.collectAsState() // Observe grouped movies





    // Fetch sections when the screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchSections(id = 36) // Replace with your actual section ID
    }

    // Fetch movies once sections are loaded
    if (sectionsResult is NetworkResult.Success) {
        val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()
        val tagsList = sections.map { it.tags } // Extract all tags
        LaunchedEffect(tagsList) {
          //  viewModel.fetchAllMovies(tagsList) // Fetch movies for all tags
        }
    }
    LaunchedEffect(Unit) {


        viewModel.getSlider("36")





    }





    // UI State Handling
    when {
        sectionsResult is NetworkResult.Loading || moviesByTagsResult is NetworkResult.Loading -> {
            // Show global loading indicator
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        sectionsResult is NetworkResult.Error -> {
            Text(
                text = "Error loading sections: ${(sectionsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        moviesByTagsResult is NetworkResult.Error -> {
            Text(
                text = "Error loading movies: ${(moviesByTagsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        sectionsResult is NetworkResult.Success && moviesByTagsResult is NetworkResult.Success -> {
            // Display sections with their movies
            val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()
            val moviesByTags = (moviesByTagsResult as NetworkResult.Success).data

            LazyColumn {
                items(sections) { section ->
                    val key = section.tags.joinToString(",") // Generate the same key format
                    val movies = moviesByTags?.get(key).orEmpty() // Retrieve movies for the section

                  /*  SectionItemWithMovies(
                        section = section,
                        movies = movies,
                        onSectionClick = onMovieClick,
                        navController =
                    )*/
                }
            }
        }
    }
}














































@Composable
fun SectionItemWithMovies(
    sectionTitle: String,
    movies: List<MovieResult.DataMovie.Item?>?,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Section Title
        Text(text = sectionTitle, style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(8.dp))

        // Movies LazyRow
        LazyRow {
            items(movies.orEmpty().filterNotNull()) { movie ->
               // MovieItem(movie = movie, onClick = { onMovieClick(movie.id ?: 0) })
                SliderItemByTags(movie)
            }



        }
    }
}
















@Composable
fun SectionItemWithMovies1(
    section: Section.Data,
    movies: List<MovieResult.DataMovie.Item>?,
    onSectionClick: () -> Unit,
    navController: NavHostController
) {

    Log.d("section","section title is"+section.title)


    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sample" , style = MaterialTheme.typography.nobinoLarge)
        Text(section.title , style = MaterialTheme.typography.nobinoLarge)
        NobinoSpecialRowBySection(section.title,navController,section,"")
        Row(
            modifier = Modifier
              //  .fillMaxWidth()
                .clickable(onClick = onSectionClick)
                .background(Color.Gray)
        )
        {
            AsyncImage(
                model = "https://vod.nobino.ir/vod/"+section.imagePath,
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.width(16.dp)
                .background(Color.Cyan)
                .height(20.dp)
            )
            Column {
                Text(text = section.title, style = MaterialTheme.typography.nobinoLarge)
                Text(text = "Movies: ${section.count}", style = MaterialTheme.typography.nobinoMedium)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display loaded movies in a LazyRow
        LazyRow {
            movies?.let {
                items(it) { movie ->
                   // MovieItem(movie = movie, onClick = {})
                    SliderItemByTags(movie)

                }
            }
        }
    }
}



@Composable
fun SliderItemByTags(movieInfo: MovieResult.DataMovie.Item) {

    MovieCardtestByTag(movieInfo)


}
















@Composable
fun SectionItem(section: Section.Data, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = "https://vod.nobino.ir/vod/"+section.imagePath,
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "section.title")
            Text(text = "Movies: ${section.count}", style = MaterialTheme.typography.nobinoMedium)
        }
    }
}

































@Composable
fun Series(
    navController: NavHostController,
    viewModel: SeriesViewModel = hiltViewModel()
)
{
    LocalelUtils.setLocale(LocalContext.current, Constants.USER_LANGUAGE)

    LaunchedEffect(true) {

        refreshDataFromServer(viewModel)
    }






    Column(
     //   modifier = Modifier.padding(top = 30.dp)


    )
    {

        Text("NOBINO--------NONINO")
        NobinoGradientCard("Nobino")





        // getAllMovies()
           getSeriesSlider()


    }

}

@Composable
fun SectionTest(
    viewModel: SeriesViewModel = hiltViewModel(),
) {
    // Observe states from ViewModel
    val sectionsResult by viewModel.sections.collectAsState()
    val moviesByTagsResult by viewModel.moviesByTags.collectAsState()

    // Trigger section fetching on first composition
    LaunchedEffect(Unit) {
        Log.d("SectionListScreen", "Triggering fetchSections call...")
        viewModel.fetchSections(id = 31) // Replace with your actual section ID
    }

    // Handle section results
    when (sectionsResult) {
        is NetworkResult.Error -> {
            Log.e("SectionListScreen", "Error fetching sections: ${(sectionsResult as NetworkResult.Error).message}")
            Text(text = "Error: ${(sectionsResult as NetworkResult.Error).message}")
        }
        is NetworkResult.Loading -> {
            Log.d("SectionListScreen", "Loading sections...")
            CircularProgressIndicator()
        }
        is NetworkResult.Success -> {
            val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()
            Log.d("SectionListScreen", "Successfully fetched ${sections.size} sections")

            // Render sections
            LazyColumn {
                items(sections) { section ->
                    Text(
                        text = section.title,
                        style = MaterialTheme.typography.nobinoMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}











@Composable
fun SectionListScreen(
    viewModel: SeriesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit, // Callback for navigating to movie details
    navController: NavHostController
)
{
    val sectionsResult by viewModel.sections.collectAsState()
    val moviesByTagsResult by viewModel.moviesByTags.collectAsState()
    val sectionTagsMap = mutableMapOf<String, List<String>>() // Map to hold section id and its tags
    val sliderState by viewModel.slider.collectAsState()




    // Log the state of sectionsResult and moviesByTagsResult
    Log.d("SectionListScreen", "sectionsResult: $sectionsResult")
    Log.d("SectionListScreen", "moviesByTagsResult: $moviesByTagsResult")





    // Fetch sections when the screen loads
    LaunchedEffect(Unit) {
        Log.d("SectionListScreen", "Fetching sections...")
        viewModel.fetchSections(id = 36) // Replace with your actual section ID
    }

    LaunchedEffect(Unit) {

        viewModel.getSlider("36")


    }





    // Fetch all movies once sections are loaded
    if (sectionsResult is NetworkResult.Success) {
        val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()


        Log.d("SectionListScreen", "Sections fetched successfully: ${sections.size} sections")


        sections.forEach {section->

            Log.d("SectionListScreen", "Section data is : tags are: ${section.tags}  id is:  ${section.id} title is: ${section.title}")
            sectionTagsMap[section.title] = section.tags // Assuming `section.id` uniquely identifies a section




        }



        LaunchedEffect(sections) {
            Log.d("SectionListScreen", "Fetching movies for all sections...")
            viewModel.fetchAllMovies(sections) // Fetch movies for all sections
        }
    }

    when {
        sectionsResult is NetworkResult.Loading || moviesByTagsResult is NetworkResult.Loading -> {
            Log.d("SectionListScreen", "Loading state detected: Displaying global loading indicator")
            //CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Global loading indicator
        }
        sectionsResult is NetworkResult.Error -> {
            val errorMessage = (sectionsResult as NetworkResult.Error).message
            Log.e("SectionListScreen", "Error loading sections: $errorMessage")
            Text(
                text = "Error loading sections: ${(sectionsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        moviesByTagsResult is NetworkResult.Error -> {
            Text(
                text = "Error loading movies: ${(moviesByTagsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        sectionsResult is NetworkResult.Success && moviesByTagsResult is NetworkResult.Success
                && sliderState is NetworkResult.Success

                     -> {
            val moviesBySection = (moviesByTagsResult as NetworkResult.Success).data.orEmpty()

            Log.d("SectionListScreen", "Movies fetched successfully for all sections")

            LazyColumn {

                item{ Text("SLIDER") }
                item{  AnimatedImageSlider(sliderState.data!!.data)}






                itemsIndexed (moviesBySection.entries.toList()) { index,entry ->
                    val sectionTitle = entry.key // The section title (key of the map)
                    val movies = entry.value.orEmpty().filterNotNull()
                    val sectionTags = sectionTagsMap[sectionTitle].orEmpty() // Retrieve tags for this section
                    Log.d("SectionListScreen", "Displaying section: $sectionTitle with ${movies.size} movies")
                    Log.d("SectionListScreen", "Displaying section tags for title ${sectionTitle} : ${sectionTags} ")






                    SectionItemWithMovies(
                        sectionTitle = sectionTitle,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}




@Composable
fun  AnimatedImageSlider(
    slider: List<Slider.Sliderinfo?>?,
    // viewModel:HomeViewModel= hiltViewModel()

)






{




    //  val sliderState by viewModel.slider.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount =  {5})

    // slider?.get(pagerState.currentPage)?.imageHorizontalPath.toString()

    LaunchedEffect(Unit) {
        // viewModel.getSlider()

        while (true) {
            delay(3000) // Wait for 3 seconds before moving to the next page
            val nextPage = (pagerState.currentPage + 1) % 15
            pagerState.animateScrollToPage(nextPage)
        }

    }




    Column(modifier = Modifier.fillMaxSize()) {
        // HorizontalPager from Compose Foundation





        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState,
            modifier = Modifier
                .height(300.dp)
                //.weight(1f)
                .fillMaxWidth()
        ) { page ->
            // Calculate the offset from the center for scaling effect.
            // Note: currentPageOffsetFraction is an experimental extension property.
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                .absoluteValue
                .coerceIn(0f, 1f)
            // Interpolate between 0.85f (off-center) and 1f (center)
            val scale = lerp(0.7f, 1.2f, 1f - pageOffset)


            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .fillMaxWidth()
                    .aspectRatio(0.8f) // Adjust aspect ratio as needed.
            ) {

                val data= slider?.get(page)?.imageHorizontalPath.toString()
                val imagePath="https://vod.nobino.ir/vod/"+data

                Log.d("slider","slider data is $data")

                Image(
                    painter = rememberAsyncImagePainter(model = imagePath),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Dot indicators below the pager

        if (slider != null) {
            SliderWithIndicator(
                numberOfPages = slider.size,
                selectedPage = pagerState.currentPage,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }


















    // Create a PagerState using the official pager



}

// --- Dot Indicator Composable ---
@Composable
fun SliderWithIndicator(
    numberOfPages: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color.Blue,
    unselectedColor: Color = Color.LightGray,
    unselectedSize: Dp = 8.dp,
    selectedSize: Dp = 25.dp,
    space: Dp = 4.dp,
    animationDuration: Int = 300
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(numberOfPages) { index ->
            val isSelected = index == selectedPage

            val dotWidth by animateDpAsState(
                targetValue = if (isSelected) selectedSize else unselectedSize,
                animationSpec = tween(durationMillis = animationDuration)
            )
            val dotColor by animateColorAsState(
                targetValue = if (isSelected) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = animationDuration)
            )
            Box(
                modifier = Modifier
                    .height(unselectedSize)
                    .width(dotWidth)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
    }
}














private suspend fun refreshDataFromServer(viewModel: SeriesViewModel) {
  // viewModel.getProduct()
   // viewModel.getProductBySize()
   // viewModel.getSlider()
    viewModel.getSeriestBySize()


   //viewModel.productsListBySize()
}




  //  NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
   // NobinoOutlineButton()









