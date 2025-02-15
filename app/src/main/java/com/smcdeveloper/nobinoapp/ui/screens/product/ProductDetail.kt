import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.smcdeveloper.nobinoapp.ui.screens.series.ProductBanner
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

    // Fetch product details when the page loads
    LaunchedEffect(productId) {
        Log.d("ProductDetailPage", "Fetching product details for ID: $productId")
        productDetailsViewModel.getProductDetails(productId)
    }

    // Fetch related movies when "Related Movies" tab is selected
    val products by productDetailsViewModel.product.collectAsState()
    val relatedMovies by productDetailsViewModel.relatedMovies.collectAsState()

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1) {
            val tag = products.data?.data?.tags?.get(0)?.id
            Log.d("ProductDetailPage", "Fetching related movies for tag: $tag")
            productDetailsViewModel.getRelatedMovies(tags = tag.toString())
        }
    }

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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is NetworkResult.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${(products as NetworkResult.Error).message}")
                    }
                }
                is NetworkResult.Success -> {
                    val productData = (products as NetworkResult.Success<ProductModel>).data?.data
                    productData?.let { product ->
                        ShowProductDetailWithTabs(
                            productTitle = product.name,
                            productEnglishTitle = product.translatedName,
                            productImage = product.images.firstOrNull()?.src.orEmpty(),
                            productDescription = product.longDescription,
                            productApproval = 200,
                            videoUrl = product.videoLink,
                            navController = navController,
                            selectedTabIndex = selectedTabIndex,
                            onTabSelected = { index -> selectedTabIndex = index },
                            relatedMovies = relatedMovies,
                            product = product // Pass the entire ProductModel object
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
    relatedMovies: NetworkResult<MovieResult>,
    product: ProductModel.MovieInfo
)
{
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
        val tabTitles = listOf("Description", "Related Movies")
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
            2 -> ProductDescription(description = productDescription)
            1 -> RelatedTab(relatedMovies = relatedMovies)
           0 -> ProductDescriptionWithExtras( product = product


            )




        }
    }
}

@Composable
fun ProductDescription(description: String) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = "Description",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            Text(
                text = description,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start
            )
        }
    }
}



@Composable
fun ProductDescriptionWithExtras(
    product:ProductModel.MovieInfo,
   // description: String,
   // List of (Label, IconResId)
    //screenshots: List<String>, // List of screenshot URLs
   // circularImages: List<Pair<String, String>> // List of (Actor Name, Image URL)
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Description Text
        item {
            Text(
                text = product.longDescription,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start
            )
        }

        // Spacer
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Row with icons and labels
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                product.actors.forEach { actor ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = actor.name,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Spacer
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Screenshots (LazyRow)
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(product.images) { screenshot ->
                    AsyncImage(
                        model ="https://vod.nobino.ir/vod/${screenshot.src}",
                        contentDescription = "Screenshot",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // Spacer
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Circular Images with Labels (LazyRow or LazyGrid)
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(product.actors) { actor ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://vod.nobino.ir/vod/${actor.imagePath}",
                            contentDescription = actor.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = actor.name,
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
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
            val movies = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()
            if (movies.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No related movies available.")
                }
            } else {
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
    }
}
