package com.smcdeveloper.nobinoapp.ui.screens.series

import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.HtmlText
import com.smcdeveloper.nobinoapp.ui.theme.ageSelectedButton
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.productDescription
import com.smcdeveloper.nobinoapp.ui.theme.roundedShape
import com.smcdeveloper.nobinoapp.ui.theme.sliderdots
import com.smcdeveloper.nobinoapp.util.Constants.DEFAULT_IMAGE_POSETR
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Composable
fun SeriesDetailPage(
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



    // Fetch product details when the page loads
    LaunchedEffect(productId) {
        Log.d("ProductDetailPage", "Fetching product details for ID: $productId")
        productDetailsViewModel.getProductDetails(productId)
    }

    // Fetch related movies when "Related Movies" tab is selected

    // Observe states
    val products by productDetailsViewModel.product.collectAsState()
    val relatedMovies by productDetailsViewModel.relatedMovies.collectAsState()






    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 0) {
            val tag = products.data?.data?.tags?.get(1)?.id



            Log.d("ProductDetailPage", "Fetching related movies for ID: $productId")
            Log.d("ProductDetailPage", "Fetching related movies for tag : $tag")

            productDetailsViewModel.getSeriesEpisodes(productId)



        }
        else if(selectedTabIndex==2)
        {
            Log.d("ProductDetailPage", "selected tab index : $selectedTabIndex ")

            val tagList= ArrayList<String>()

            products.data?.data?.tags?.forEachIndexed { index, tag ->



                tagList.add(tag.id)

            }

            Log.d("related","related tages are ${tagList.toString()}")





         //  productDetailsViewModel.getRelatedMovies(tagList.subList(1,2)
            productDetailsViewModel.getRelatedMovies(tagList[1] )









            relatedMovies.data?.movieInfo?.items?.forEach {

                Log.d("related","related series name is ${it?.name.toString()} cat is: ${it?.category.toString()}")


            }








        }





    }





    val episodes by productDetailsViewModel.episodes.collectAsState()


   // val episodes by productDetailsViewModel.




    // val episodes=relatedMovies


    Scaffold(
        modifier = Modifier.background(Color.DarkGray),
        backgroundColor = Color.Black


    )

    { paddingValues ->
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



                    val sessions= loadEpisodes(episodes)
                    products.data?.data?.let { productData ->



                        ShowSeriesProductDetailWithTabs(
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
                            episodes = episodes,
                            relatedMovies=relatedMovies,




                            productDetailsViewModel=productDetailsViewModel,
                            productId=productId,
                            sessions = sessions,
                            tags =productData.tags

                        )



                    }
                }
            }
        }
    }
}

@Composable
fun ShowSeriesProductDetailWithTabs(
    productTitle: String,
    productEnglishTitle: String,
    productImage: String,
    productDescription: String,
    productApproval: Int,
    videoUrl: String?,
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    episodes: NetworkResult<MovieResult>,
    relatedMovies: NetworkResult<MovieResult>,

    productDetailsViewModel: ProductDetailsViewModel,
    productId:Int,
    sessions: List<MovieResult.DataMovie.Item>,
    tags: List<ProductModel.MovieInfo.Tag>



)

{

   // val episodeList = loadEpisodes(relatedMovies)
   // val currentEpisode= sessions[0]



    LazyColumn(

        modifier = Modifier.background(Color.Black)
            .fillMaxSize()


    )

    {

        item {
            SeriesProductBanner(
                productImage = productImage,
                productTitle = productTitle,
                productEnglishTitle = productEnglishTitle,
                productApproval = productApproval,
                videoUrl = videoUrl,
                navController = navController,
              //  episode = currentEpisode,
                productId = productId,
                viewModel = productDetailsViewModel

            )

            Spacer(modifier = Modifier.height(16.dp))

        }





        // Tabs


        item {
            val tabTitles = listOf(stringResource(R.string.Description), stringResource(R.string.other_episodes),stringResource(R.string.RelatedMovies))

            TabRow(selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.Transparent,
                contentColor = Color.Red



            )
            {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.White,
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(text = title) }
                    )
                }
            }




            when (selectedTabIndex) {





                0 -> ProductDescription(description = productDescription)
                1 -> Episodes(
                    productDetailsViewModel = productDetailsViewModel,
                    productId = productId,


                    relatedMovies = episodes,
                    onSessionSelected = { sessionIndex  ->

                        productDetailsViewModel.getSeriesEpisodes4(productId,sessionIndex)


                    },
                    navController = navController,
                   sessions = sessions



                )
                2-> RelatedMovies(relatedMovies)

            }







        }



        // Tab Content

    }
}


@Composable
fun SeriesProductBanner(
    productImage: String,
    productTitle: String,
    productEnglishTitle: String,
    productApproval: Int,
    videoUrl: String?,
    navController: NavHostController,
   // episode: MovieResult.DataMovie.Item?,
    viewModel: ProductDetailsViewModel,
   productId:Int


)

{
    LaunchedEffect(Unit) {
        viewModel.getSeriesFirstEpisode(productId,0)

    }
  //
    var isVideoAvailable = !videoUrl.isNullOrEmpty()
  // val isVideoAvailable = !episode?.videoLink.isNullOrEmpty()
   val isUserLogin =USER_LOGIN_STATUS
    val firstEpisode by viewModel.firstEpisode.collectAsState()

    isVideoAvailable=true





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





          //  Text("SAmple.. ${episode?.name}")
            //  episode?.id.toString()

       //   Log.d("episode","video Link ${episode.toString()}")


            Spacer(modifier = Modifier.height(8.dp))

            Button (
                onClick = {
                    if (isUserLogin) {
                       // viewModel.getSeriesFirstEpisode(12702,0)


                       /* if (episode != null) {
                            episode.id?.let {

                                Log.d("ProductBanner","episode.. $it")
                                viewModel.getSeriesFirstEpisode(12702,0)

                            }
                        }*/






                            when(firstEpisode)
                            {
                                is NetworkResult.Success->

                                {

                                    val episodeDetails = (firstEpisode as NetworkResult.Success).data


                                    Log.d("ProductBanner", "Success"+firstEpisode.data.toString())
                                  val link  = episodeDetails?.data?.videoLink
                                    Log.d("ProductBanner", "Success"+link)


                                    if(link!=null)
                                    {
                                        val encodedUrl = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
                                        Log.d("ProductBanner", "Navigating to video player with URL: $encodedUrl")
                                        navController.navigate(Screen.VideoPlayerScreen.withArgs(encodedUrl))

                                    }











                                }
                                else->
                                {

                                    Log.d("ProductBanner", "Failed")


                                }



                            }







                        }


                    else
                    {




                    navController.navigate(Screen.SignUp.route)



                }


















                },
               // enabled = isVideoAvailable && isUserLogin,
                colors = ButtonDefaults.buttonColors(
                    contentColor = if ((isVideoAvailable && isUserLogin) ) Color.Red else Color.Gray,
                    containerColor = Color.Red


                ),
                shape = MaterialTheme.roundedShape.exlarge
            )



            {
               Icon(
                    painterResource(R.drawable.play1), "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White


                )


                Text(
                    text = if (isVideoAvailable && isUserLogin) stringResource(R.string.PlayFirstEpisode) else stringResource(
                        R.string.LoginFirst
                    ),
                    style = MaterialTheme.typography.nobinoLarge
                )
            }
        }
    }
}


@Composable
fun ProductDescription(description: String) {
    Column(modifier = Modifier.padding(16.dp)) {


        HtmlText(description, textColor = Color.White)


    }
}

    /*@Composable
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
}*/

    @Composable
    fun RelatedMovieItem(movie: MovieResult.DataMovie.Item) {

        val imageUrl = "https://vod.nobino.ir/vod/${movie.images?.firstOrNull()?.src}"
        Log.d("related", "image url is" + imageUrl)

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
    fun RelatedMovies(relatedMovies: NetworkResult<MovieResult>) {

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

                Log.d("related", "related tab success")

                val movies = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()
                if (movies.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No related movies available.")
                    }
                } else {

                    Column() {





                            LazyRow(modifier = Modifier.padding(vertical = 30.dp)) {

                                items(movies) { movie ->

                                    com.smcdeveloper.nobinoapp.ui.screens.product.RelatedMovieItem(
                                        movie
                                    )


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
fun NewTab()
{

}







    /*
@Composable
fun Episodes(
    relatedMovies: NetworkResult<MovieResult>,
    onSessionSelected: (Int) -> Unit // Callback for fetching new session data

) {
    when (relatedMovies) {
        is NetworkResult.Loading -> {
            Log.d("EpisodesTab", "Loading episodes...")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error -> {
            Log.e("EpisodesTab", "Error loading episodes: ${relatedMovies.message}")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${relatedMovies.message}")
            }
        }
        is NetworkResult.Success -> {
            val sessions = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()
            if (sessions.isEmpty()) {
                Log.d("EpisodesTab", "No sessions found.")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No sessions available.")
                }
            } else
            {
                EpisodesWithDropdown(sessions = sessions, onSessionSelected = onSessionSelected)
            }
        }
    }
}
*/



@Composable
fun loadEpisodes1(
    relatedMovies: NetworkResult<MovieResult>,
):List<MovieResult.DataMovie.Item>








{
    var sessions = emptyList<MovieResult.DataMovie.Item>()

    when(relatedMovies)
    {
        is NetworkResult.Loading -> {
            // Show a loading indicator while data is being fetched
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is NetworkResult.Error -> {
            // Display an error message if there is an error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${relatedMovies.message}")
            }
        }


        is NetworkResult.Success ->
        {

            sessions = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()






        }







    }




    return sessions




}


@Composable
fun loadEpisodes(
    relatedMovies: NetworkResult<MovieResult>
): List<MovieResult.DataMovie.Item>


{
    var sessions by remember { mutableStateOf(emptyList<MovieResult.DataMovie.Item>()) }

    when (relatedMovies) {
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
                Text(text = "Error: ${relatedMovies.message}")
            }
        }

        is NetworkResult.Success -> {
            Log.d("related","success....")
            sessions = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()
        }
    }

    return sessions // ✅ The UI will reactively update when sessions changes
}



















    @Composable
    fun Episodes1(
        relatedMovies: NetworkResult<MovieResult>, // Current state of episodes/movies
        onSessionSelected: (Int) -> Unit,// Callback when a session is selected
        productDetailsViewModel: ProductDetailsViewModel,
        productId: Int,
        navController: NavHostController

    ) {
        when (relatedMovies) {
            is NetworkResult.Loading -> {
                // Show a loading indicator while data is being fetched
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Error -> {
                // Display an error message if there is an error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${relatedMovies.message}")
                }
            }

            is NetworkResult.Success -> {
                // Process and display the list of sessions and episodes
                val sessions = relatedMovies.data?.movieInfo?.items.orEmpty().filterNotNull()



                if (sessions.isEmpty()) {
                    // Show a message if no sessions are found
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No sessions available.")
                    }
                } else {
                    // Display sessions with a dropdown and list of episodes

                    /*
                CustomComboBoxRTL1(
                 relatedMovies = sessions,
                    onSessionSelected=onSessionSelected




                )*/



                    EpisodesWithDropdown3(
                        productId = productId,
                        sessions = sessions,
                        viewModel = productDetailsViewModel,
                        navController = navController

                        // onSessionSelected = onSessionSelected
                    )


                }
            }
        }
    }






@Composable
fun Episodes(
    relatedMovies: NetworkResult<MovieResult>, // Current state of episodes/movies
    onSessionSelected: (Int) -> Unit,// Callback when a session is selected
    productDetailsViewModel: ProductDetailsViewModel,
    productId: Int,
    navController: NavHostController,
    sessions: List<MovieResult.DataMovie.Item>


) {


  //  val sessions = loadEpisodes(relatedMovies)







    if (sessions.isEmpty()) {
        // Show a message if no sessions are found
        Box(
            modifier = Modifier.fillMaxSize(),
            //    .background(Color.Green),

            contentAlignment = Alignment.Center

        )

        {
            Text(text = "No sessions available.")
        }
    } else {

       sessions.forEach  {
          Log.d("SessionData", "Session ${it.name.toString()}")
      }












        // Display sessions with a dropdown and list of episodes

        /*
            CustomComboBoxRTL1(
             relatedMovies = sessions,
                onSessionSelected=onSessionSelected




            )*/



        EpisodesWithDropdown3(
            productId = productId,
            sessions = sessions,
            viewModel = productDetailsViewModel,
            navController = navController

            // onSessionSelected = onSessionSelected
        )


    }


}








    @Composable
    fun EpisodesWithDropdown(
        sessions: List<MovieResult.DataMovie.Item>, // List of sessions
        onSessionSelected: (Int) -> Unit, // Callback when a session is selected
        navController: NavHostController
    ) {
        var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index

        Column(modifier = Modifier.fillMaxSize()) {
            // Dropdown for selecting sessions
            SessionDropdownMenu(
                sessions = sessions,
                selectedSession = selectedSessionIndex,
                onSessionSelected = { index ->
                    selectedSessionIndex = index // Update the selected session index
                    onSessionSelected(index) // Notify the parent with the selected index
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn for displaying episodes of the selected session
            Column(
                modifier = Modifier.fillMaxSize(),
                // contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                //
                // val selectedSession = sessions[selectedSessionIndex]
                sessions.forEach { episode ->

                    //EpisodeItem(episode) // Display each episode

                }
            }
        }
    }


    @Composable
    fun EpisodesWithDropdown2(
        productId: Int, // Product ID needed for API call
        sessions: List<MovieResult.DataMovie.Item>, // List of sessions
        viewModel: ProductDetailsViewModel // ViewModel reference
    ) {
        var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index

        // ✅ Observe episodes from ViewModel
        val episodes by viewModel.episodes1.collectAsState()

        episodes.data?.forEach {
            Log.d("episode", "episodes ${it.name}")

        }

        Log.d("episode", "episodes ")




        Column(modifier = Modifier.fillMaxSize()) {
            // Dropdown for selecting sessions
            SessionDropdownMenu(
                sessions = sessions,
                selectedSession = selectedSessionIndex,
                onSessionSelected = { index ->
                    selectedSessionIndex = index // Update selected session
                    viewModel.getSeriesEpisodes4(productId, index) // Fetch episodes
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Show episodes for the selected session
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {

                episodes.data?.forEach { episode ->


                    //  EpisodeItem(episode, navController = )


                }


            }
        }
    }


    @Composable
    fun EpisodesWithDropdown3(


        productId: Int, // Product ID needed for API call
        sessions: List<MovieResult.DataMovie.Item>, // List of sessions
        viewModel: ProductDetailsViewModel, // ViewModel reference
        navController: NavHostController
    )

    {
        var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index

        // ✅ Observe episodes from ViewModel
        val episodes by viewModel.episodes1.collectAsState()

        episodes.data?.forEach {
            Log.d("episode", "episodes ${it.name}")

        }

        Log.d("episode", "episodes ")




        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize(0.5f))
            {

                CustomComboBoxRTL1(
                    relatedMovies = sessions,
                    selectedSessionIndex = selectedSessionIndex,
                    onSessionSelected = { index ->
                        selectedSessionIndex = index // Update selected session
                        viewModel.getSeriesEpisodes4(productId, index) // Fetch episodes
                    }
                )

            }
            // Dropdown for selecting sessions


            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Show episodes for the selected session
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {

                episodes.data?.forEach { episode ->


                    EpisodeItem(episode, navController)


                }


            }
        }
    }


    @Composable
    fun EpisodesWithDropdown1(
        sessions: List<MovieResult.DataMovie.Item>, // List of sessions
        onSessionSelected: (Int) -> Unit // Callback when a session is selected


    ) {
        var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index

        Column(modifier = Modifier.fillMaxSize()) {
            // Dropdown for selecting sessions
            CustomComboBoxRTL1(
                relatedMovies = sessions,
                selectedSessionIndex = selectedSessionIndex,
                onSessionSelected = { index ->
                    selectedSessionIndex = index // Update the selected session index
                    onSessionSelected(index) // Notify the parent with the selected index
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn for displaying episodes of the selected session
            Column(
                modifier = Modifier.fillMaxSize(),
                // contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //
                // val selectedSession = sessions[selectedSessionIndex]
                sessions.forEach { episode ->

                    //  EpisodeItem(episode) // Display each episode

                }
            }
        }
    }


    @Composable
    fun SessionDropdownMenu(
        sessions: List<MovieResult.DataMovie.Item>, // List of all sessions
        selectedSession: Int, // Currently selected session index
        onSessionSelected: (Int) -> Unit // Callback to notify the selected session index


    ) {
        var expanded by remember { mutableStateOf(false) } // Tracks dropdown expanded state
        val sessions1 = remember {
            sessions.filter {
                it.category.toString().equals("SEASON")
            }
        } // Replace isSession

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        )


        {
            // Display the name of the selected session
            Text(
                text = sessions.getOrNull(selectedSession)?.name ?: "Select a Session",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            // Dropdown menu items
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )

            {
                sessions1.forEachIndexed { index, session ->
                    DropdownMenuItem(

                        onClick = {
                            expanded = false // Close the dropdown
                            onSessionSelected(index) // Pass the selected index
                        },
                        content = {
                            Text(session.name ?: "Session ${index + 1}")


                        }

                    )
                }
            }
        }
    }


    /*
@Composable
fun EpisodesWithDropdown(
    sessions: List<MovieResult.DataMovie.Item>, // List of all sessions
    selectedSession: Int, // Currently selected session index
    onSessionSelected: (Int) -> Unit
) {
    var selectedSession by remember { mutableStateOf(sessions.firstOrNull()) } // Selected session state

    Column(modifier = Modifier.fillMaxSize()) {
        // Dropdown Menu for selecting sessions
        SessionDropdownMenu(
            sessions = sessions,
            selectedSession = selectedSession,
            onSessionSelected = { session ->

                onSessionSelected(session?: return@SessionDropdownMenu)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn for displaying episodes of the selected session
        selectedSession?.let { session ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                sessions.forEach { episode ->
                    item {
                        EpisodeItem(episode)
                    }
                }
            }
        }
    }
}
*/


    /*
@Composable
fun SessionDropdownMenu(
    sessions: List<MovieResult.DataMovie.Item>,
    selectedSession: MovieResult.DataMovie.Item?,
   // onSessionSelected: (MovieResult.DataMovie.Item) -> Unit
    onSessionSelected: (Int) -> Unit // Pass the index of the selected session
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red, shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedSession?.name ?: "Select Session",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sessions.forEachIndexed { index,session ->
                DropdownMenuItem(

                    onClick = {
                        expanded = false
                        onSessionSelected(index)
                    },
                    content = {
                         Text(session.name ?: "Session ${session.id}")


                    },
                )
            }
        }
    }
}*/

    @Composable
    fun EpisodeItem(
        episode: MovieResult.DataMovie.Item,
        navController: NavHostController
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.sliderdots, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable {

                    navController.navigate(Screen.ProductDetails.withArgs(episode.id.toString()))


                },


            verticalAlignment = Alignment.CenterVertically
        )

        {








            AsyncImage(



                model = if(episode.images?.get(1)?.src!!.isNotEmpty()) "https://vod.nobino.ir/vod/${episode.images.get(1)?.src}" else DEFAULT_IMAGE_POSETR,
                contentDescription = episode.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(64.dp,100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier =
            Modifier.weight(1f)

            ) {





                Text(
                    text = episode.name ?: "Untitled Episode",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.productDescription

                )


                HtmlText(
                    html = episode.shortDescription ?: "No description available",
                    textColor = MaterialTheme.colorScheme.productDescription


                )






               /* Text(
                    text = episode.shortDescription ?: "No description available",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )*/
            }

            /*Text(
                text = "${episode.ages} mins",
                fontSize = 12.sp,
                color = Color.Gray
            )*/
        }
    }


    @Composable
    fun CustomComboBoxRTL(

        option: List<MovieResult.DataMovie.Item>,
        // sessions: List<MovieResult.DataMovie.Item>, // List of sessions
        onSessionSelected: (Int) -> Unit // Callback when a session is selecte


    ) {


        var expanded by remember { mutableStateOf(false) }
        // var selectedOption by remember { mutableStateOf("") }
        var selectedSession by remember { mutableStateOf("") }
        val sessions = remember {
            option.filter {

                it.category.equals("SEASON")

            }
        }




        sessions.forEach {
            Log.d("selected", "options.." + it.name)

        }








        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var boxWidth by remember { mutableStateOf(0) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, shape = RoundedCornerShape(16.dp))
                        .onGloballyPositioned { coordinates ->
                            boxWidth = coordinates.size.width
                        }
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            tint = Color.White
                        )
                        androidx.compose.material3.Text(
                            text = if (selectedSession.isEmpty()) "Select an option" else selectedSession,
                            color = Color.White
                        )
                    }
                }





























                androidx.compose.material3.DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { boxWidth.toDp() })
                        .background(Color.Red, shape = RoundedCornerShape(16.dp))
                        .align(Alignment.TopEnd),
                    offset = DpOffset(x = 0.dp, y = 0.dp)
                ) {
                    sessions.forEachIndexed { index, session ->
                        androidx.compose.material3.DropdownMenuItem(
                            {
                                androidx.compose.material3.Text(
                                    text = session.name.toString(),
                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    textAlign = TextAlign.End
                                )
                            },
                            onClick = {


                                selectedSession = session.name.toString()

                                Log.d("selected", "selected option is  $selectedSession")


                                expanded = false
                                onSessionSelected(index) // Pass the selected index


                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {

                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }


                    }
                }
            }

        }


    }


    @Composable
    fun CustomComboBoxRTL1(
        relatedMovies: List<MovieResult.DataMovie.Item>, // Raw API data
        onSessionSelected: (Int) -> Unit,
        selectedSessionIndex: Int, // Currently selected session index


    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedSession by remember { mutableStateOf("") }


        var boxWidth by remember { mutableStateOf(0) }

        // var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index


        // ✅ Filter only sessions from relatedMovies (first API call)
        val sessions = remember {
            relatedMovies.filter {
                it.category.toString().equals("SEASON")
            }
        } // Replace isSession with your session identifier

        // val  sessions=relatedMovies


        sessions.forEach {


        }






        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, shape = RoundedCornerShape(16.dp))
                        .onGloballyPositioned { coordinates -> boxWidth = coordinates.size.width }
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            tint = Color.White
                        )
                        Text(
                            text = if (selectedSession.isEmpty()) "انتخاب فصل" else selectedSession,
                            color = Color.White
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { boxWidth.toDp() })
                        .background(Color.Red, shape = RoundedCornerShape(4.dp))
                )
                {
                    sessions.forEachIndexed { index, session ->
                        DropdownMenuItem(
                            content = {

                               Text(
                                    //text = session .name.toString(),
                                    text = " فصل   ${index + 1} ",

                                    color = Color.White,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    textAlign = TextAlign.End
                                )
                            },


                            onClick = {
                                //selectedSession = session.name.toString()
                                selectedSession = " فصل  ${index + 1} "


                                expanded = false



                                onSessionSelected(index)

                                // session.id?.let { onSessionSelected(it) } // Send session ID to fetch episodes
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }





