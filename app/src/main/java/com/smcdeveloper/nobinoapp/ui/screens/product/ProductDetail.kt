import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailPage(
    navController: NavHostController,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    productId: Int
) {
    LaunchedEffect(productId) {
        productDetailsViewModel.getProductDetails(productId)
    }

    val products by productDetailsViewModel.product.collectAsState()

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
                    thumbnails = thumbnails.value
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
    thumbnails: List<String>
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

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
                            onClick = { /* Play movie functionality */ },
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
                            onClick = { selectedTabIndex = index },
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
                    1 -> RelatedTab()
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
fun RelatedTab() {
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
