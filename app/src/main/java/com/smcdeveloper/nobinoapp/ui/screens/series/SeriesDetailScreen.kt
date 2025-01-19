package com.smcdeveloper.nobinoapp.ui.screens.series

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Composable
fun SeriesDetailPage(
    navController: NavHostController,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    productId: Int
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Fetch product details when the page loads
    LaunchedEffect(productId) {
        Log.d("ProductDetailPage", "Fetching product details for ID: $productId")
        productDetailsViewModel.getProductDetails(productId)
    }

    // Fetch related movies when "Related Movies" tab is selected

    // Observe states
    val products by productDetailsViewModel.product.collectAsState()



    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1) {
            val tag = products.data?.data?.tags?.get(0)?.id
            Log.d("ProductDetailPage", "Fetching related movies for ID: $productId")
            Log.d("ProductDetailPage", "Fetching related movies for tag : $tag")

            productDetailsViewModel.getSeriesEpisodes(productId)

        }
    }




    val relatedMovies by productDetailsViewModel.relatedSeries.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Detail", color = Color.White) },
                backgroundColor = Color.Black
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (products) {
                is NetworkResult.Loading -> {
                    Log.d("ProductDetailPage", "Loading product details...")
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is NetworkResult.Error -> {
                    Log.e(
                        "ProductDetailPage",
                        "Error loading product details: ${(products as NetworkResult.Error).message}"
                    )
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${(products as NetworkResult.Error).message}")
                    }
                }
                is NetworkResult.Success -> {
                    Log.d("ProductDetailPage", "Product details loaded successfully.")
                    Log.d("ProductDetailPage", products.data?.data.toString())
                    Log.d("ProductDetailPage","video link......"+ products.data?.data?.videoLink.toString())


                    products.data?.data?.let { productData ->



                        ShowProductDetailWithTabs(
                            productTitle = productData.name,
                            productEnglishTitle = productData.translatedName,
                            productImage = productData.images.firstOrNull()?.src.orEmpty(),
                            productDescription = productData.longDescription,
                            productApproval = 200,
                            videoUrl = productData.videoLink,
                            navController = navController,
                            selectedTabIndex = selectedTabIndex,
                            onTabSelected = { index ->
                                Log.d("ProductDetailPage", "Tab selected: $index")
                                selectedTabIndex = index
                            },
                            relatedMovies = relatedMovies
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowProductDetailWithTabs(
    productTitle: String,
    productEnglishTitle: String,
    productImage: String,
    productDescription: String,
    productApproval: Int,
    videoUrl: String?,
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    relatedMovies: NetworkResult<MovieResult>
) {
    Column {
        ProductBanner(
            productImage = productImage,
            productTitle = productTitle,
            productEnglishTitle = productEnglishTitle,
            productApproval = productApproval,
            videoUrl = videoUrl,
            navController = navController
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (videoUrl.isNullOrEmpty()) {
            Text(
                text = "Video is not available for this product.",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Tabs
        val tabTitles = listOf("Description", "Episodes","RelatedMovies")
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = title) }
                )
            }
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> ProductDescription(description = productDescription)
            1 -> Episodes(relatedMovies = relatedMovies)
            3-> RelatedMovies()
        }
    }
}


@Composable
fun ProductBanner(
    productImage: String,
    productTitle: String,
    productEnglishTitle: String,
    productApproval: Int,
    videoUrl: String?,
    navController: NavHostController
) {
    val isVideoAvailable = !videoUrl.isNullOrEmpty()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            model = "https://vod.nobino.ir/vod/$productImage",
            contentDescription = productTitle,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "$productTitle ($productEnglishTitle)",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (isVideoAvailable) {
                        val encodedUrl = URLEncoder.encode(videoUrl, StandardCharsets.UTF_8.toString())
                        Log.d("ProductBanner", "Navigating to video player with URL: $encodedUrl")
                        navController.navigate(Screen.VideoPlayerScreen.withArgs(encodedUrl))
                    }
                },
                enabled = isVideoAvailable,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isVideoAvailable) Color.Red else Color.Gray
                )
            ) {
                Text(
                    text = if (isVideoAvailable) "Play Movie" else "Video Not Available",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun ProductDescription(description: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Log.d("ProductDescription", "Displayed product description.")
    }
}

@Composable
fun Episodes(relatedMovies: NetworkResult<MovieResult>) {
    when (relatedMovies) {
        is NetworkResult.Loading -> {
            Log.d("RelatedTab", "Loading related movies...")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error -> {
            Log.e("RelatedTab", "Error loading related movies: ${relatedMovies.message}")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${relatedMovies.message}")
            }
        }
        is NetworkResult.Success -> {
            val movies = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()
            if (movies.isEmpty()) {
                Log.d("RelatedTab", "No related movies found.")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No related movies available.")
                }
            } else {
                Log.d("RelatedTab", "Related movies loaded successfully.")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movies) { movie ->
                        RelatedMovieItem(movie)
                    }
                }
            }
        }
    }
}

@Composable
fun RelatedMovieItem(movie: MovieResult.DataMovie.Item) {

    val imageUrl="https://vod.nobino.ir/vod/${movie.images?.firstOrNull()?.src}"
    Log.d("related","image url is"+imageUrl)

    movie.images?.firstOrNull()?.src.toString()


    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://vod.nobino.ir/vod/${movie.images?.firstOrNull()?.src}",
            contentDescription = movie.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = movie.name ?: "Untitled",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Log.d("RelatedMovieItem", "Displayed movie: ${movie.name}")
    }

}


@Composable
fun RelatedMovies()
{

}
