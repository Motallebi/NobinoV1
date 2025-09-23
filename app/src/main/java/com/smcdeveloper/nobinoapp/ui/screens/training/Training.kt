package com.smcdeveloper.nobinoapp.ui.screens.training

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRowBySection2
import com.smcdeveloper.nobinoapp.ui.screens.home.CustomSlider
import com.smcdeveloper.nobinoapp.ui.screens.home.NobinoSectionSlider3
import com.smcdeveloper.nobinoapp.ui.screens.series.SliderItemByTags


import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun TrainingScreen(navController: NavHostController) {



    // SectionTest()

    SectionListScreen(navController = navController,
        onMovieClick = {}
    )


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
    val sliderResult by viewModel.slider.collectAsState()






    // Log the state of sectionsResult and moviesByTagsResult
    Log.d("SectionListScreen", "sectionsResult: $sectionsResult")
    Log.d("SectionListScreen", "moviesByTagsResult: $moviesByTagsResult")





    // Fetch sections when the screen loads
    LaunchedEffect(Unit) {
        Log.d("SectionListScreen", "Fetching sections...")
        viewModel.fetchSections(id = 41) // Replace with your actual section ID
        viewModel.getSlider("41")
    }

    // Fetch all movies once sections are loaded
    if (sectionsResult is NetworkResult.Success) {
        val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()
        Log.d("SectionListScreen", "Sections fetched successfully: ${sections.size} sections")
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
                && sliderResult is NetworkResult.Success


                         -> {
            val moviesBySection = (moviesByTagsResult as NetworkResult.Success).data.orEmpty()
            Log.d("SectionListScreen", "Movies fetched successfully for all sections")

            LazyColumn {


                //item{  AnimatedImageSlider(sliderResult.data!!.data) }
                item{  CustomSlider(modifier = Modifier,sliderResult.data?.sliderData, navController = navController) }






                itemsIndexed (moviesBySection.entries.toList()) { index,entry ->
                    val sectionTitle = entry.key // The section title (key of the map)
                    val movies = entry.value.orEmpty().filterNotNull()
                    Log.d("SectionListScreen", "Displaying section: $sectionTitle with ${movies.size} movies")

                    val taglist = ArrayList<String>()

                    movies.get(index).tags?.forEach { tag->

                        taglist.add(tag?.id.toString())



                    }





                    NobinoSpecialRowBySection2(
                       title =  sectionTitle,
                        navController = navController,
                        tags = taglist,
                        category = "",
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 10.dp)
                        ,


                        )


                    SectionItemWithMovies(
                        sectionTitle = sectionTitle,
                        movies = movies,
                        onMovieClick = onMovieClick,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun SectionItemWithMovies(
    sectionTitle: String,
    movies: List<MovieResult.DataMovie.Item?>?,
    onMovieClick: (Int) -> Unit,
    navController: NavHostController
) {
    Column(modifier = Modifier.padding(16.dp)) {

        NobinoSectionSlider3(movies!!, navController = navController)

        // Section Title
     //   Text(text = sectionTitle, style = MaterialTheme.typography.titleSmall)

      //  Spacer(modifier = Modifier.height(8.dp))

        // Movies LazyRow
      /*  LazyRow(

            modifier = Modifier.fillMaxWidth(),
               // .padding(16.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)





        ) {
            items(movies.orEmpty().filterNotNull()) { movie ->
                // MovieItem(movie = movie, onClick = { onMovieClick(movie.id ?: 0) })
                SliderItemByTags(movie, navController = navController)
               // NobinoSectionSlider3()
            }



        }*/
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






















