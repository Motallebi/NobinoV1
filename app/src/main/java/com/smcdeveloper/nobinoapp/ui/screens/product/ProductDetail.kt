package com.smcdeveloper.nobinoapp.ui.screens.product

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMarKRequest
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.roundedShape
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import com.smcdeveloper.nobinoapp.util.DigitHelper
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailPage(
    navController: NavHostController,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    productId: Int
)
{

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent

        )







    }




    var selectedTabIndex by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    // Fetch product details when the page loads
    LaunchedEffect(productId) {
        Log.d("ProductDetailPage", "Fetching product details for ID: $productId")
        productDetailsViewModel.getProductDetails(productId)
    }

    // Fetch related movies when "Related Movies" tab is selected
    val products by productDetailsViewModel.product.collectAsState()
    val relatedMovies by productDetailsViewModel.relatedMovies.collectAsState()
    val bookmarked by productDetailsViewModel.isBookmarked.collectAsState()

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1) {
            val tag = products.data?.data?.tags?.get(0)?.id
            Log.d("ProductDetailPage", "Fetching related movies for tag: $tag")
            productDetailsViewModel.getRelatedMovies(tags = tag.toString())
        }
    }








  /*  val context = LocalContext.current
    SideEffect {
        (context as? Activity)?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, false)
        }
    }*/


    Scaffold(







    ) {


        LazyColumn(





        ) {

            item {


                Box {

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
                                    product = product, // Pass the entire ProductModel object
                                    productId = productId,
                                    viewModel = productDetailsViewModel,
                                    bookmark = bookmarked


                                )
                            }
                        }
                    }
                }




            }






        }





    }























}










































@OptIn(ExperimentalMaterial3Api::class)
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
    product: ProductModel.MovieInfo,
    viewModel: ProductDetailsViewModel,
    productId:Int,
    bookmark: Boolean


)
{
    val scrollState = rememberScrollState() // Remember scroll state

    Column(

      //  modifier = Modifier.verticalScroll(scrollState)



    ) {



        ProductBanner(
            productImage = productImage,
            productTitle = productTitle,
            productEnglishTitle = productEnglishTitle,
            productApproval = productApproval,
            videoUrl = videoUrl,
            navController = navController,
            viewModel =viewModel,
            productId = productId,
            bookmark = bookmark





        )

        Spacer(modifier = Modifier.height(16.dp))



      /*  if (videoUrl.isNullOrEmpty()) {
            Text(
                text = "Video is not available for this product.",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }*/







        // Tabs
        val tabTitles = listOf(stringResource(R.string.Description), stringResource(R.string.RelatedMovies))
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Black,
            contentColor = Color.White,
           // divider = {},















        )





        {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = title) },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.White

                )
            }
        }

        // Tab Content
        when (selectedTabIndex) {
            2 -> ProductDescription(description = productDescription)
            1 -> RelatedTab(relatedMovies = relatedMovies)
            0 -> ProductDescriptionWithExtras( product = product,navController=navController)




        }
    }
}

@Composable
fun ProductDescription(description: String) {
    Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Description",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            Text(
                text = description,
                style = MaterialTheme.typography.nobinoMedium,
                textAlign = TextAlign.Start
            )
        }
    }




@Composable
fun ProductDescriptionWithExtras(
    product:ProductModel.MovieInfo,

    navController: NavHostController
   // description: String,
   // List of (Label, IconResId)
    //screenshots: List<String>, // List of screenshot URLs
   // circularImages: List<Pair<String, String>> // List of (Actor Name, Image URL)
) {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Description Text
       /* item {
            Text(
                text = product.longDescription,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start
            )
        }*/



            HtmlText(product.longDescription,
                textColor = Color.White


            )







            MovieInfoRow(


                year = product.productionYear.toString(),
                time = "1:24:30",
                rating = product.imdbRating.toString(),
                country = product.countries[0].name


                )











        }









        // Spacer
       Spacer(modifier = Modifier.height(16.dp))

        // Row with icons and labels

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
                            style = MaterialTheme.typography.nobinoMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }


        // Spacer
       Spacer(modifier = Modifier.height(16.dp))

        // Screenshots (LazyRow)

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


        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Circular Images with Labels (LazyRow or LazyGrid)

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            )
            {
                items(product.actors) { actor ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = "https://vod.nobino.ir/vod/${actor.imagePath}",
                            contentDescription = actor.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .clickable {

                                  navController.navigate(Screen.Actors.withArgs(actor.id))

                                   // navController.navigate(Screen.Actors.route)


                                },
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = actor.name,
                            style = MaterialTheme.typography.nobinoMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White

                        )
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

        Column() {


            Row()
            {

                LazyRow {

                    items(movies) { movie ->
                        RelatedMovieItem(movie)
                    }

                }



            }

           /* Row()
            {

                LazyRow {

                    items(movies) { movie ->
                        RelatedMovieItem(movie)
                    }

                }



            }
*/






        }






               /* LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(movies) { movie ->
                        RelatedMovieItem(movie)
                    }
                }*/
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


@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                movementMethod = LinkMovementMethod.getInstance() // Enables clickable links
            }
        },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        },
        modifier = modifier
    )
}


@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, textColor: Color = Color.Black) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                movementMethod = LinkMovementMethod.getInstance() // Enables clickable links
                setTextColor(textColor.toArgb())


            }
        },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        },
        modifier = modifier
    )
}









@Composable
fun MovieInfoRow(
    year: String,
    country: String,
    rating: String,
    time: String
)

{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
           // .padding(horizontal = 8.dp)
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
             verticalAlignment = Alignment.CenterVertically,
       // horizontalArrangement = Arrangement.SpaceEvenly
    )
    {

        MovieInfoItem(null, DigitHelper.digitByLocate(time))  // Duration
        MovieInfoItem(Icons.Default.ThumbUp, DigitHelper.digitByLocate("$rating%"))  // Rating
        MovieInfoItem(Icons.Default.Public, country)  // Country
        MovieInfoItem(Icons.Default.DateRange, DigitHelper.digitByLocate(year),false)  // Year



    }



}





@Composable
fun MovieInfoItem(icon: ImageVector?, text: String,showSpacer:Boolean=true) {
    Row(
        modifier = Modifier.padding(start = 5.dp)
            //.background(Color.Blue)
            .fillMaxHeight()
            .width(70.dp),


              verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,

    )

    {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.nobinoMedium
        )
        if(showSpacer) {
            Spacer(
                modifier = Modifier.height(50.dp)
                    .background(Color.White)
                    .width(1.dp)
                    .padding(horizontal = 8.dp)
            )
        }


    }
}




@Composable
fun CategoryChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp)) // Rounded shape
            .background(Color.DarkGray) // Background color
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun CategoryChipsRow() {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryChip("جنایی") // Crime
        CategoryChip("اکشن") // Action
    }
}




@Composable
fun ProductBanner(
    productImage: String,
    productTitle: String,
    productEnglishTitle: String,
    productApproval: Int,
    videoUrl: String?,
    navController: NavHostController,
    viewModel: ProductDetailsViewModel,
  //  episode: MovieResult.DataMovie.Item
    productId:Int,
    bookmark: Boolean


)

{
    val isVideoAvailable = !videoUrl.isNullOrEmpty()
    val isUserLogin =USER_LOGIN_STATUS

    Log.d("banner","Bookmarked is $bookmark")



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    )

    {

        AsyncImage(
            model = "https://vod.nobino.ir/vod/$productImage",
            contentDescription = productTitle,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 50.dp)
            ,
            verticalArrangement = Arrangement.Top
        )










        {

            FloatingActionButtons(navController,viewModel,productId,bookmark)

























            Text(
                text = productTitle,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top=20.dp)

            )

            Text(
                text = productEnglishTitle,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(top=10.dp)
            )


            CategoryChipsRow()



           // Text("SAmple.. ${episode.name}")


            Spacer(modifier = Modifier.height(8.dp))

            Button(
              //  modifier = Modifier.width(150.dp),
                onClick = {
                    if (isVideoAvailable && isUserLogin) {
                        val encodedUrl = URLEncoder.encode(videoUrl, StandardCharsets.UTF_8.toString())
                        Log.d("ProductBanner", "Navigating to video player with URL: $encodedUrl")
                        navController.navigate(Screen.VideoPlayerScreen.withArgs(encodedUrl))
                    }

                    else{




                        navController.navigate(Screen.SignUp.route)



                    }









                },
                // enabled = isVideoAvailable && isUserLogin,
                colors = ButtonDefaults.buttonColors(



                    contentColor = if (isVideoAvailable && isUserLogin) Color.Red else Color.Gray,
                    containerColor = Color.Red


                ),
                shape = MaterialTheme.roundedShape.exlarge


            )
            {
               Icon(painterResource(R.drawable.play1),"",
                  modifier = Modifier.size(32.dp),
                    tint = Color.White



                )

                Text(
                    text = if (isVideoAvailable && isUserLogin) stringResource(R.string.PlayMovie) else  stringResource(R.string.LoginFirst),
                   style = MaterialTheme.typography.nobinoLarge
                )
            }
        }









    }










}



@Composable
fun FloatingActionButtons(navController: NavHostController,viewModel: ProductDetailsViewModel,productId: Int,bookmark:Boolean) {




   // val bookmarkState = remember {  mutableStateOf(false)}
   val bookmarkStat by viewModel.isBookmarked.collectAsState()

    Log.d("bookmark ","bookmark state $bookmarkStat")














    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )

    {
        Row {
            TransparentBookmarkIconButton(
                isBookmark =bookmarkStat,
                icon =
                if(bookmarkStat) {

                    painterResource(R.drawable.bookmark)





                }

                else
                   painterResource(R.drawable.bookmark)

                ,
                contentDescription = "Bookmark",
                onClick = {




                    Log.d("stat", "Bookmark:${bookmark}")

                    if (bookmarkStat) {


                        Log.d("stat", "removing bookmark")
                        viewModel.removeBookMark(
                            auth = "Bearer $USER_TOKEN",

                            bookmark = BookMarKRequest(


                                productId = productId,
                                type = "BOOKMARK"


                            )


                        )
                    }

                    else
                    {

                        viewModel.saveBookMark(
                            auth = "Bearer $USER_TOKEN",

                            bookmark =   BookMarKRequest(


                                productId =productId ,
                                type="BOOKMARK"




                            )






                        )








                    }





                    /* Handle bookmark action */





                }
            )



            // Share button
            TransparentIconButton(
                icon = painterResource(R.drawable.share),
                contentDescription = "Share",
                onClick = { /* Handle share action */ }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Bookmark button

        }




        // Back button
        TransparentIconButton(
            icon = painterResource(R.drawable.arrow_left),
            contentDescription = "Back",
            onClick = { navController.navigateUp() },

        )


    }
}







@Composable
fun TransparentBookmarkIconButton(
    icon:Painter ,
    contentDescription: String,
    onClick: () -> Unit,
    isBookmark:Boolean =false
)

{
    var isSelected by remember { mutableStateOf(false) }




    Log.d("stat", "Loading ... Bookmark:$isBookmark Selected $isSelected")

    IconButton(
        onClick = {
            isSelected = !isSelected // ✅ Toggle selection state


            onClick()
            Log.d("stat", "Bookmark:$isBookmark Selected $isSelected")
        },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.6f)) // ✅ Semi-transparent background
    )
    {
        Icon(




            painter = icon,
            contentDescription = contentDescription,
            tint = if (isBookmark) Color.Red else Color.White, // ✅ Change icon color when selected
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun TransparentIconButton(
    icon:Painter ,
    contentDescription: String,
    onClick: () -> Unit,

)
{
    var isSelected by remember { mutableStateOf(false) }



    IconButton(
        onClick = {
            isSelected = !isSelected // ✅ Toggle selection state
            onClick()

        },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.6f)) // ✅ Semi-transparent background
    )
    {
        Icon(




            painter = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) Color.Red else Color.White, // ✅ Change icon color when selected
            modifier = Modifier.size(24.dp)
        )
    }
}
