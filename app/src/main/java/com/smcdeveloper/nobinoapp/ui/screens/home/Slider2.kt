package com.smcdeveloper.nobinoapp.ui.screens.home

// Animation APIs
import android.util.Log
import androidx.compose.animation.animateColorAsState

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween

// Foundation and Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


// Official Pager (Experimental)
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PageSize

// Shapes, Graphics, and Drawing
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.ui.draw.clip


// Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

// UI utilities
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

// Coil for Image Loading (Coil 2.x)
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.FeatureIconsRow
import com.smcdeveloper.nobinoapp.ui.component.NobinoGradientCard
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.ui.theme.sliderdots
import com.smcdeveloper.nobinoapp.util.Constants.DEFAULT_IMAGE_POSETR
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_HIGHT
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_WITDTH
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Kotlin Standard Library
import kotlin.math.absoluteValue


// --- Data model for your image items ---
data class YourImageData(
    val imageUrl: String
)

// --- Main Composable with Official Pager ---
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedImageSlider(
    slider: List<Slider.Sliderinfo?>?,
    // viewModel:HomeViewModel= hiltViewModel()

) {


    //  val sliderState by viewModel.slider.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })

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
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
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

                val data = slider?.get(page)?.imageHorizontalPath.toString()
                val imagePath = "https://vod.nobino.ir/vod/" + data

                Log.d("slider", "slider data is $data")

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
)
{


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


@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    // sliderList: MutableList<String>,
    sliderList: List<Slider.Sliderinfo?>?,
    // selectedPage: Int,

    backwardIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    forwardIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 90.dp),
    imageCornerRadius: Dp = 20.dp,
    imageHeight: Dp = 260.dp,
    unselectedSize: Dp = 10.dp,
    selectedSize: Dp = 30.dp,
    animationDuration: Int = 300,
    selectedColor: Color = Color.White,
    unselectedColor: Color = MaterialTheme.colorScheme.sliderdots,
    navController: NavHostController,
    isClickble:Boolean=true
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { sliderList!!.size-4})
    val scope = rememberCoroutineScope()





    LaunchedEffect(Unit) {
        // viewModel.getSlider()

        while (true) {
            delay(3000) // Wait for 3 seconds before moving to the next page
            val nextPage = (pagerState.currentPage + 1) % 15
            pagerState.animateScrollToPage(
                nextPage


            )
        }

    }






    Column(
       //   Modifier.background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )


    {
        Row(
            modifier = modifier.fillMaxWidth(),
          //  .background(Color.Yellow),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        )

        {


            HorizontalPager(
                pageSize = PageSize.Fill,
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier.weight(1f)
                //  .background(Color.Green)
            )

            { page ->

                Log.d("Slider",sliderList.toString())

                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
                // val scaleFactor = 0.75f + (1f - 0.2f) * (1f - pageOffset.absoluteValue)


                Box(modifier = modifier
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }

                    .alpha(
                        scaleFactor.coerceIn(0f, 1f)
                    )
                    .width(217.dp)
                    .height(260.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(imageCornerRadius))) {
                    val data = sliderList?.get(page)?.imageVerticalPath.toString()
                    AsyncImage(
                        model =
                        ImageRequest.Builder(LocalContext.current)
                            .scale(Scale.FIT)
                            .crossfade(true)
                            .data(IMAGE_BASE_URL + data)
                            .build(),


                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        // placeholder = painterResource(id = R.drawable.img),
                        modifier = modifier.height(imageHeight),


//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
                    )

                    Box(Modifier.fillMaxSize()
                        .clickable {
                            if (isClickble) {


                                if (sliderList?.get(page)?.product?.category.toString() != "SERIES") {

                                    navController.navigate(
                                        Screen.ProductDetails.withArgs(
                                            sliderList?.get(page)?.product?.id.toString()


                                        )
                                    )
                                } else if (sliderList?.get(page)?.product?.category.toString() == "SERIES") {
                                    navController.navigate(
                                        Screen.SeriesDetailScreen.withArgs(
                                            sliderList?.get(page)?.product?.id.toString()
                                        )

                                    )

                                }


                            }

                        }







                        ,
                        contentAlignment = Alignment.BottomCenter


                    )
                    {
                        Column (

                           modifier.height(80.dp)
                               .fillMaxWidth()
                              //.background(Color.Yellow)


                        )
                        {
                         Row(
                             modifier.padding(start = 10.dp)


                         )
                         {


                             Text(
                                text =  sliderList?.get(page)?.product?.name ?:"" ,
                                 style = MaterialTheme.typography.nobinoSmall




                             )


                            // Text(sliderList?.get(page)?.product?.id.toString())


                         }

                         Row(
                             Modifier.fillMaxWidth(),
                             verticalAlignment = Alignment.CenterVertically,
                             horizontalArrangement = Arrangement.End


                         )
                         {
                            if(sliderList?.get(page)?.product?.imdbRating!=null)
                             NobinoGradientCard(sliderList[page]?.product?.imdbRating.toString())
                            // Text(sliderList?.get(page)?.product?.name.toString())
                             FeatureIconsRow(isKeyboardAvailable = true, isMicAvailable = true)

                         }




                        }






                    }






                }
            }

        }



        Row(
            modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        )

        {

/*
                repeat(sliderList!!.size) {
                    val color = if (pagerState.currentPage == it) dotsActiveColor else dotsInActiveColor
                    Box(modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(dotsSize)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        })
                }*/


            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {

                repeat(sliderList!!.size-5) { index ->
                    val selectedPage = pagerState.currentPage
                    val isSelected = index == selectedPage

                    val dotWidth by animateDpAsState(
                        targetValue = if (isSelected) selectedSize else unselectedSize,
                        animationSpec = tween(durationMillis = animationDuration), label = ""
                    )
                    val dotColor by animateColorAsState(
                        targetValue = if (isSelected) selectedColor else unselectedColor,
                        animationSpec = tween(durationMillis = animationDuration), label = ""
                    )
                    Box(
                        modifier = Modifier
                            .height(unselectedSize)
                            .width(dotWidth)
                            .clip(CircleShape)
                            .border(width = 1.dp, color = dotColor, shape = CircleShape)
                            .background(dotColor)
                            .clickable {

                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }






                            }
                    )
                }

            }


        }


    }
}


@Composable
fun NobinoSectionSlider(


    movieList: List<MovieResult.DataMovie.Item?>,
    navController: NavHostController,


    modifier: Modifier = Modifier,

    // selectedPage: Int,


    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 10.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    unselectedSize: Dp = 15.dp,
    selectedSize: Dp = 25.dp,
    animationDuration: Int = 300,
    //  selectedColor: Color = Color.White,
    // unselectedColor: Color = MaterialTheme.colorScheme.sliderdots,
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { movieList!!.size })
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        // viewModel.getSlider()
        val nextPage = (pagerState.currentPage + 1) % 15


    }






    Column(
        //  Modifier.background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )


    {
        Row(
            modifier = modifier.fillMaxWidth(),
            // .background(Color.Yellow)
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        )

        {


            HorizontalPager(
                pageSize = PageSize.Fill,
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier.weight(1f)
                //  .background(Color.Green)
            )

            { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
                // val scaleFactor = 0.75f + (1f - 0.2f) * (1f - pageOffset.absoluteValue)


                Box(modifier = modifier
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .alpha(
                        scaleFactor.coerceIn(0f, 1f)
                    )
                    .padding(10.dp)
                    .clip(RoundedCornerShape(imageCornerRadius))) {
                    val data = movieList?.get(page)?.images?.get(0)?.src.toString()
                    //  movieInfo


                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(IMAGE_BASE_URL + data).build(),


                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        // placeholder = painterResource(id = R.drawable.img),
                        modifier = modifier.height(imageHeight)
//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
                    )
                }
            }

        }



        Row(
            modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        )

        {


/*
                repeat(sliderList!!.size) {
                    val color = if (pagerState.currentPage == it) dotsActiveColor else dotsInActiveColor
                    Box(modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(dotsSize)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        })
                }*/


            Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
            {

                repeat(movieList!!.size) { index ->
                    val selectedPage = pagerState.currentPage
                    val isSelected = index == selectedPage


                }

            }


        }


    }
}


@Composable

fun NobinoSectionSlider1(


    movieList: List<MovieResult.DataMovie.Item?>,


    ) {


    val lazyListState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
        //.background(Color.DarkGray)
        // .padding(vertical = 16.dp)
    )
    {

        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(250.dp), // Adjust height as needed


            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between posters
        )
        {
            itemsIndexed(movieList, key = { _, movie -> movie?.id ?: 1 }) { index, movie ->
                // Determine the scale based on whether this item is the first visible
                val targetScale by remember {
                    derivedStateOf {


                        val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex + 1
                        val firstVisibleItemScrollOffset =
                            lazyListState.firstVisibleItemScrollOffset

                        // Check if this item is the first visible item AND
                        // if it's fully or almost fully at the start of the list.
                        // You can adjust the offset threshold for when it "snaps" to big size.
                        val isFirstAndAtStart =
                            (index == firstVisibleItemIndex
                                    //  && firstVisibleItemScrollOffset < 20
                                    ) // Adjust threshold as needed

                        if (isFirstAndAtStart) 1.1f else 0.9f // Bigger for first, slightly smaller for others
                    }
                }

                val animatedScale by animateFloatAsState(
                    targetValue = targetScale,
                    animationSpec = tween(durationMillis = 300), label = "moviePosterScale"
                )




                MoviePosterCard(
                    movie = movie!!,
                    scale = animatedScale
                )


            }


        }


    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NobinoSectionSlider2(
    movieList: List<MovieResult.DataMovie.Item?>
) {
    val state = rememberCarouselState { movieList.count() }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(vertical = 24.dp)
    )
    {


        HorizontalMultiBrowseCarousel(

            state = state,


            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),

            preferredItemWidth = 280.dp,
            // itemWidth = 186.dp,
            itemSpacing = 16.dp,
            // contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = movieList[i]


            HeroMoviePosterCardUniform(item!!)


            /*
                    val data =item?.images?.get(0)?.src.toString()
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(IMAGE_BASE_URL + data).build(),


                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        // placeholder = painterResource(id = R.drawable.img),
                        modifier = Modifier.height(205.dp)
                            .maskClip(MaterialTheme.shapes.extraLarge)
                           // .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            //                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
                    )*/


            /*
             Image(
                 modifier = Modifier
                     .height(205.dp)
                     .maskClip(MaterialTheme.shapes.extraLarge),
                 painter = painterResource(id = item.imageResId),
                 contentDescription = item.contentDescription,
                 contentScale = ContentScale.Crop
             )*/
        }


    }


}


@Stable
fun Dp.toPx(density: Density): Float {
    return with(density) { this@toPx.toPx() }
}


@Composable
fun NobinoSectionSlider3(
    movieList: List<MovieResult.DataMovie.Item?>,
    navController: NavHostController


) {
      val lazyListState = rememberLazyListState()
      val density = LocalDensity.current // <--- Capture LocalDensity here!

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(vertical = 24.dp)
    )*/

        LazyRow(

            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.Blue)
                //.padding(vertical = 10.dp)
                .height(IMAGE_HIGHT.dp), // Height for the row items
            contentPadding = PaddingValues(horizontal = 24.dp), // Padding around the content
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between items
        )

        {


            itemsIndexed(movieList, key = { _, movie -> movie?.id.toString() }) { index, movie ->
                // Calculate scale and darkness for each item based on its scroll position

///////

                val targetScaleAndDarkness by remember {

                    derivedStateOf {
                        val layoutInfo = lazyListState.layoutInfo
                        val visibleItemsInfo = layoutInfo.visibleItemsInfo
                        val itemInfo = visibleItemsInfo.find { it.index == index }

                        if (itemInfo == null) {
                            // Item is not visible, use default small/dark state
                            return@derivedStateOf Pair(0.8f, 0.5f) // scale, alpha (darkness)
                        } else {
                        }

// Determine the start position of the item relative to the LazyRow's viewport
                        val itemStartOffset = itemInfo.offset
                        val viewportStart = layoutInfo.viewportStartOffset
// Normalize the position based on the item's width for smoother transitions
                        // A threshold to control how much of the item needs to be "first"
                        val thresholdStart =
                            0.dp.toPx(density = density) // When the item starts appearing
                        val thresholdEnd =
                            150.dp.toPx(density = density) // When it's fully "first" (adjust this)

                        // Calculate progress from 0 (off-screen) to 1 (fully "first")
                        val progress =
                            ((thresholdEnd - itemStartOffset) / thresholdEnd).coerceIn(0f, 1f)

                        // Scale calculation: grows from 0.8 to 1.0 (or higher)
                        val scale = 0.8f + (0.2f * progress) // Scales from 0.8 to 1.0
                        // Or, to make it even bigger:
                        // val scale = 0.8f + (0.3f * progress) // Scales from 0.8 to 1.1

                        // Darkness calculation: alpha decreases as it becomes "first"
                        // 1.0f means no overlay, 0.0f means full overlay (fully dark)
                        // We want non-first items to be darker (e.g., 0.5 alpha overlay),
                        // and the first item to have no overlay (1.0 alpha).
                        val darknessAlpha =
                            0.5f + (0.5f * progress) // From 0.5 (darker) to 1.0 (normal)
                        // This means the overlay opacity will go from 0.5 to 0.0
                        // We'll apply the overlay inside the card.


                        Pair(scale, darknessAlpha)


                    }


//////

                }


                val animatedScale by animateFloatAsState(
                    targetValue = targetScaleAndDarkness.first,
                    animationSpec = tween(durationMillis = 300), label = "itemScaleAnimation"
                )

                val animatedDarknessAlpha by animateFloatAsState(
                    targetValue = targetScaleAndDarkness.second,
                    animationSpec = tween(durationMillis = 300), label = "itemDarknessAnimation"
                )


                MoviePosterCardWithDarknessAndScale(
                    movie =movie!!,
                    scale = animatedScale,
                    darknessAlpha = animatedDarknessAlpha,
                    navController = navController
                )






            }


        }
       // Spacer(modifier = Modifier.height(32.dp))




}


@Composable
fun HeroMoviePosterCardUniform(movie: MovieResult.DataMovie.Item) {
    Card(
        modifier = Modifier
            .fillMaxHeight() // Let the carousel determine the width, but fill its allocated height
           .aspectRatio(0.7f), // Maintain a consistent aspect ratio (e.g., 7:10 for posters)
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    )

    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        )

        {


            val data = movie.images?.get(0)?.src.toString()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .scale(Scale.FIT)
                    .crossfade(true).data(IMAGE_BASE_URL + data).build(),


                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                // placeholder = painterResource(id = R.drawable.img),
                modifier = Modifier
                    .height(IMAGE_HIGHT.dp)
                    // .maskClip(MaterialTheme.shapes.extraLarge)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
            )


            /*Image(
            painter = painterResource(id = movie.imageResId),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )*/

        }
    }
}


@Composable
fun MoviePosterCard(movie: MovieResult.DataMovie.Item, scale: Float) {


    Card(
        modifier = Modifier
            .width(160.dp) // Fixed width for posters
            .height(220.dp) // Fixed height for posters
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    )

    {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            val data = movie.images?.get(0)?.src.toString()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                    .crossfade(true).data(IMAGE_BASE_URL + data).build(),


                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                // placeholder = painterResource(id = R.drawable.img),
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
            )


        }
    }
}




@Composable
fun MoviePosterCardWithDarknessAndScale(
    movie: MovieResult.DataMovie.Item,
    scale: Float, darknessAlpha: Float,
    navController: NavHostController,

) {
   var imageSrc = emptyList<MovieResult. DataMovie. Item. Image?>()
   var imagePath =""

    if(movie.images!!.isNotEmpty()) {

         imageSrc = movie.images.filter {

            it?.imageType == "POSTER"

        }
        imagePath= imageSrc[0]?.src.toString()

    }
    else
    {
        imagePath= DEFAULT_IMAGE_POSETR


    }






       Log.d("filterImage","images source is ${imageSrc}")













       // val data = movie.images?.get(0)?.src.toString()
     //  val imageData  = if(movie.images.isNotEmpty()) imagePath else DEFAULT_IMAGE_POSETR




    Card(
        modifier = Modifier
            .width(IMAGE_WITDTH.dp) // Fixed width for each card, adjusted to fit content and leave space
            .height(IMAGE_HIGHT.dp) // Fixed height for each card
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    )

    {
        Box(
            modifier = Modifier.fillMaxSize()
        )
        {





            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(IMAGE_BASE_URL + imagePath)
                    .build(),


                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                // placeholder = painterResource(id = R.drawable.img),
                modifier = Modifier
                   .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )

           // Text("89898")











            val overlayAlpha = 1.0f - darknessAlpha

            Box(
                modifier =
                Modifier
                    .clickable {

                        if(movie.category.toString() != "SERIES") {

                            navController.navigate(
                                Screen.ProductDetails.withArgs(
                                    movie.id.toString()


                                )
                            )
                        }
                        else if (movie.category.toString() == "SERIES")
                        {
                            navController.navigate(
                                Screen.SeriesDetailScreen.withArgs(
                                    movie.id.toString())

                            )

                        }











                    }


                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = overlayAlpha)













                    ),
                contentAlignment = Alignment.BottomCenter



            )
            {


                Column(modifier = Modifier.fillMaxWidth()


                )


                {
                    if (movie.imdbRating == "") {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.3f)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End

                        )
                        {
                            Text(
                                movie.name.toString(),
                                style = MaterialTheme.typography.nobinoSmall,
                                color = Color.White,
                                modifier = Modifier.padding(start = 10.dp)

                            )
                            FeatureIconsRow(isKeyboardAvailable = true, isMicAvailable = true)


                        }


                    } else {
                        Text(
                            movie.name.toString(),
                            style = MaterialTheme.typography.nobinoSmall,
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp)

                        )

                        // Text("89898")
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.3f)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End

                        )
                        {
                            NobinoGradientCard(movie.imdbRating.toString())
                            FeatureIconsRow(isKeyboardAvailable = true, isMicAvailable = true)


                        }


                    }
                }




















                }



            }


        }
    }








