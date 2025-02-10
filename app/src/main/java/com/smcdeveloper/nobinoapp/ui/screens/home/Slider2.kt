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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize

// Shapes, Graphics, and Drawing
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip


// Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

// UI utilities
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel

// Coil for Image Loading (Coil 2.x)
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

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
    val pagerState = rememberPagerState(initialPage = 0, pageCount =  {15})

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
