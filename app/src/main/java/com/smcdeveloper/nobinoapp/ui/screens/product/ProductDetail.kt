import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ProductDetailPage(
    navController: NavHostController,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    productId: Int
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(productId) {
        productDetailsViewModel.getProductDetails(productId)
    }




    val products by productDetailsViewModel.product.collectAsState()
    val relatedMovies by productDetailsViewModel.relatedMovies.collectAsState()




    val thumbnails = remember { mutableStateOf(listOf<String>()) }
    val actors = remember { mutableStateOf(listOf<String>()) }

    when (products) {
        is NetworkResult.Loading -> {
            Log.d("productdetails", "Loading")
        }
        is NetworkResult.Error -> {
            Log.d("productdetails", "Error")
        }
        is NetworkResult.Success -> {
            Log.d("productdetails", "Success")
            products.data?.data?.let { productData ->
                // Extract thumbnails and actor images
                val extractedThumbnails = ArrayList<String>()
                val extractedActors = ArrayList<String>()

                productData.actors.forEach { actor ->
                    extractedThumbnails.add(actor.imagePath) // Actor's thumbnail path
                    extractedActors.add(actor.imagePath)    // Actor's circular image path
                }

                thumbnails.value = extractedThumbnails
                actors.value = extractedActors

                // Pass to ShowProductDetail only when ready
                ShowProductDetail(
                    productTitle = productData.name,
                    productEnglishTitle = productData.translatedName,
                    productImage = productData.images[0].src,
                    productDescription = productData.longDescription,
                    productYear = 2024,
                    productCountry = productData.countries[0].name,
                    productApproval = 20000,
                    productDuration = "productData.duration",
                    actors = actors.value,
                    thumbnails = thumbnails.value,
                    videoUrl = productData.videoLink.toString(),
                    navController = navController,
                    productDetailsViewModel=productDetailsViewModel,
                    productId=productId,
                    onTabSelected = { index -> selectedTabIndex = index },
                    relatedMovies = relatedMovies,
                    selectedTabIndex=0



                )
            }
        }
    }
}

@Composable
fun ShowProductDetail(
    productTitle: String,
    productEnglishTitle: String,
    productImage: String,
    productDescription: String,
    productYear: Int,
    productCountry: String,
    productApproval: Int,
    productDuration: String,
    actors: List<String>,
    thumbnails: List<String>,
    videoUrl:String,
    navController: NavHostController,
    productDetailsViewModel:ProductDetailsViewModel,
    productId:Int,
    onTabSelected: (Int) -> Unit,
    relatedMovies: NetworkResult<MovieResult>,
    selectedTabIndex:Int



) {
    // var selectedTabIndex by remember { mutableStateOf(0) }
    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1) { // Check if the "Related" tab is selected
            productDetailsViewModel.getRelatedMovies(productId,"")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = productTitle, color = Color.White) },
                backgroundColor = Color.Black
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                // Top Section with Background Poster
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    // Background Image
                    AsyncImage(
                        model = "https://vod.nobino.ir/vod/$productImage",
                        contentDescription = productTitle,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Foreground Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        // IMDb Rating Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = "IMDb",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Color.Yellow, shape = RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = productApproval.toString(),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        // Movie Titles
                        Text(
                            text = productTitle,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = productEnglishTitle,
                            color = Color.White,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Genre Tags
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Chip(text = "اکشن")
                            Spacer(modifier = Modifier.width(8.dp))
                            Chip(text = "جنایی")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Play Button
                        Button(
                            onClick = {
                                Log.d("VideoUrl",videoUrl)
                                val encodedUrl = URLEncoder.encode(videoUrl, StandardCharsets.UTF_8.toString())
                                navController.navigate(Screen.VideoPlayerScreen.withArgs(encodedUrl))
                                ///   navController.navigate(Screen.VideoDemoScreen.withArgs(encodedUrl))




                                /* Play movie functionality */ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "بخش فیلم",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Tabs
                val tabTitles = listOf("Description", "Related")

                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                //selectedTabIndex = index
                                      },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> ProductDescription(
                        description = productDescription,
                        year = productYear,
                        country = productCountry,
                        approval = productApproval,
                        duration = productDuration,
                        actors = actors,
                        thumbnails = thumbnails
                    )
                    1 -> RelatedTab(relatedMovies = relatedMovies)
                }
            }
        }
    }
}

@Composable
fun ProductDescription(
    description: String,
    year: Int,
    country: String,
    approval: Int,
    duration: String,
    actors: List<String>,
    thumbnails: List<String>
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Detailed Description Text
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Additional Information Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$year", fontWeight = FontWeight.Bold)
            Text(text = country, fontWeight = FontWeight.Bold)
            Text(text = "$approval%", fontWeight = FontWeight.Bold)
            Text(text = duration, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Actor Thumbnails
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            thumbnails.forEach { thumbnail ->
                AsyncImage(
                    model = "https://vod.nobino.ir/vod/$thumbnail",
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Circular Actor Images with Names
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            actors.forEach { actor ->
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = "https://vod.nobino.ir/vod/$actor",
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = actor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun RelatedTab1() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Related Movies:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "- Related Movie 1")
        Text(text = "- Related Movie 2")
        Text(text = "- Related Movie 3")
    }
}

@Composable
fun Chip(text: String) {
    Text(
        text = text,
        color = Color.White,
        modifier = Modifier
            .background(Color.Gray.copy(alpha = 0.7f), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun RelatedTab(relatedMovies: NetworkResult<MovieResult>) {


    when (relatedMovies) {
        is NetworkResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${relatedMovies.message}")
            }
        }
        is NetworkResult.Success -> {
            val movies = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()  // Access the movie list inside `MovieResult`
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    RelatedMovieItem(movie)
                }
            }
        }
    }
}

@Composable
fun RelatedMovieItem(movie: MovieResult.DataMovie.Item) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { /* Handle click */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Movie Image
        AsyncImage(
            model = "https://vod.nobino.ir/vod"+movie.images?.firstOrNull()?.src, // Use the first image if available
            contentDescription = movie.name,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Movie Name
        Text(
            text = movie.name ?: "Unknown Movie",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

