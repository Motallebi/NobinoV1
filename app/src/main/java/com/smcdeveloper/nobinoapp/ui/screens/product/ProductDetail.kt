import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailPage(
    navController: NavHostController,
    productDetailsViewModel: ProductDetailsViewModel= hiltViewModel(),
    productId:Int
)







{

    LaunchedEffect(productId) {

        productDetailsViewModel.getProductDetails(productId)


    }
    val productState by productDetailsViewModel.product.collectAsState()

    when(productState){

        is NetworkResult.Loading -> {

            Log.d("productdatils","Loading")

        }

        is NetworkResult.Error ->
        {
            Log.d("productdatils","Error")

        }

        is NetworkResult.Success ->
        {
            Log.d("productdatils","Sucess")

        }






    }

















    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movie Details", color = Color.White) },
                backgroundColor = Color.Black
            )
        }
    )

    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Top Section with Background Poster and Movie Details
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Background Poster Image
                Image(
                    painter = painterResource(id = R.drawable.img1), // Replace with your image resource
                    contentDescription = "Background Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Foreground Movie Details
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "IMDB: 4.6",
                            color = Color.Yellow,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "امبولانس", // Persian Title
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "Ambulance",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tags Placeholder
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Chip(text = "اکشن") // Action in Persian
                        Spacer(modifier = Modifier.width(8.dp))
                        Chip(text = "جنایی") // Crime in Persian
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Play Movie Button
                    Button(
                        onClick = { /* TODO: Add play functionality */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "بخش فیلم", // Play Movie in Persian
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            val tabTitles = listOf("Description", "Cast", "Related")

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

            // Tab Content
            when (selectedTabIndex) {
                0 -> DescriptionTab()
                1 -> CastTab()
                2 -> RelatedTab()
            }
        }
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
fun DescriptionTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "This is a detailed description of the movie Ambulance. The movie revolves around intense action and drama.",
            textAlign = TextAlign.Start,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CastTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Main Cast:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "- Actor 1")
        Text(text = "- Actor 2")
        Text(text = "- Actor 3")
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
