package com.smcdeveloper.nobinoapp.ui.screens.home

// Animation APIs
import android.util.Log
import androidx.compose.animation.animateColorAsState

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize

// Shapes, Graphics, and Drawing
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip


// Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope

// UI utilities
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage

// Coil for Image Loading (Coil 2.x)
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.ui.theme.sliderdots
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
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
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    unselectedSize: Dp = 15.dp,
    selectedSize: Dp = 25.dp,
    animationDuration: Int = 300,
    selectedColor: Color = Color.White,
    unselectedColor: Color = MaterialTheme.colorScheme.sliderdots,
)

{

    val pagerState = rememberPagerState(initialPage = 0, pageCount = {sliderList!!.size})
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        // viewModel.getSlider()

        while (true) {
            delay(3000) // Wait for 3 seconds before moving to the next page
            val nextPage = (pagerState.currentPage + 1) % 15
            pagerState.animateScrollToPage(nextPage)
        }

    }






    Column(
      //  Modifier.background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )


    {
        Row(
            modifier = modifier.fillMaxWidth()
               // .background(Color.Yellow)
            ,
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
                    val data = sliderList?.get(page)?.imageHorizontalPath.toString()
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(IMAGE_BASE_URL + data).build(),


                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
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


                Row(horizontalArrangement =Arrangement.spacedBy(8.dp))
                {

                    repeat(sliderList!!.size) { index ->
                        val selectedPage =pagerState.currentPage
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
                        )
                    }

                }













            }











        }
    }









