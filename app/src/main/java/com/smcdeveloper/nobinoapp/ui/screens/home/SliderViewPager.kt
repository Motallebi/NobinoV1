package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Scale
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.theme.LocalShape
import com.smcdeveloper.nobinoapp.ui.theme.LocalSpacing
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun ImageSlider(viewModel: HomeViewModel = hiltViewModel() )
{

 //   viewModel.slider.collectAsState()

    var sliderList by remember {
        mutableStateOf<List<Slider.Sliderinfo>>(emptyList())

    }
    var loading by remember {
        mutableStateOf(false)
    }



    val sliderResult by  viewModel.slider.collectAsState()

    when (sliderResult) {

        is NetworkResult.Success-> {
            val sliderInfo = (sliderResult as NetworkResult.Success<Slider>).data?.data
          sliderList = sliderInfo as List<Slider.Sliderinfo>

            loading = false
        }
        is NetworkResult.Error -> {
            loading = false
            Log.e("3636", "Top Slider error : ${sliderResult.message}")
        }
        is NetworkResult.Loading -> {
            loading = true
        }


    }

    if (loading)
    {

    }
    else
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = LocalSpacing.current.extraSmall,
                        vertical = LocalSpacing.current.small
                    )
            ) {
                val pagerState = rememberPagerState()
                var imageUrl by remember {
                    mutableStateOf("")
                }

                Box {
                    HorizontalPager(
                        count = sliderList.size,
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = LocalSpacing.current.medium),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { index ->
                        imageUrl ="https://vod.nobino.ir/vod/"+sliderList[index].imageHorizontalPath.toString()
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = imageUrl)
                                    .apply(
                                        block = fun ImageRequest.Builder.() {
                                            scale(Scale.FILL)
                                        }
                                    )
                                    .build()
                            )
                            Image(
                                painter = painter, contentDescription = "", modifier = Modifier
                                    .padding(LocalSpacing.current.small)
                                    .clip(LocalShape.current.medium)
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )


                        }
                    }

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(LocalSpacing.current.semiLarge),
                        activeColor = Color.Black,
                        inactiveColor = Color.LightGray,
                        indicatorWidth = LocalSpacing.current.small,
                        indicatorHeight = LocalSpacing.current.small,
                        indicatorShape = CircleShape
                    )
                }


                LaunchedEffect(key1 = pagerState.currentPage) {
                    delay(6000)
                    var newPosition = pagerState.currentPage + 1
                    if (newPosition > sliderList.size - 1) newPosition = 0
                    pagerState.scrollToPage(newPosition)
                }


            }
        }


    }









}