package com.smcdeveloper.nobinoapp.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Scale
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.repository.SeriesRepository
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge

import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.ui.theme.nobino_fonts
import com.smcdeveloper.nobinoapp.ui.theme.roundedShape
import com.smcdeveloper.nobinoapp.ui.theme.spacing
import okhttp3.internal.wait


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobinoText() {

    var text by remember { mutableStateOf("") }
    val myColor7 = Color(0xFF00838F)
    TextField(
        value = text,
        placeholder = {
            Text("Enter Your Phone")

        },
        onValueChange = { text = it },
        modifier = Modifier.padding(top = 20.dp),
        // label = { Text(text = "Middle name") },
        leadingIcon = {

            Icon(
                painterResource(R.drawable.phone_regular),
                contentDescription = "Email icon",
                //  tint = Color.Green
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Blue,

            unfocusedContainerColor = Color.Black,
            focusedContainerColor = Color.Black


        ),
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        )


}

@Composable
fun NobinoButton(text: String, modifier: Modifier) {
    Button(
        onClick = {},
        modifier = modifier
            .padding(20.dp)
            .height(50.dp)
        //  .background(Color.Green)
        ,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.onBackground


        )


    ) {

        Text(
            text,
            style = MaterialTheme.typography.nobinoMedium


        )

        //  modifier = Modifier.background(Color.Yellow)


    }

}

@Composable
fun NobinoOutlineButton() {
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        //  .background(Color.Green)
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,


            ),
        shape = ButtonDefaults.outlinedShape,
        // border = ButtonDefaults.outlinedButtonBorder(enabled)


        content = {},
    )


}


@Composable
fun FilmBox(movieTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
        contentAlignment = Alignment.TopStart

    )
    {
        Column() {
            Card()
            {
                Text(movieTitle)


            }


        }


    }
}

@Composable
fun FilmBox2(movieUrl: String) {


    Box()
    {

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = movieUrl)
                .apply(
                    block = fun ImageRequest.Builder.() {
                        scale(Scale.FILL)
                    }
                )
                .build()
        )
        Image(
            painter = painter, contentDescription = "", modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd


        )
        {
            Text("Movie Name")


        }


    }

}


@Composable
@Preview
fun showCardTempalte() {
    // MovieCardtest()
    // MovieCard1(sliderInfo = Slider.Sliderinfo)
    // NobinoGradientCard("resr")


}


/*

    Card(
        modifier = Modifier.fillMaxSize()


    )
    {

        AsyncImage(
            model = "https://vod.nobino.ir/vod/IMAGES/2024-8/9975/9975_1722932998427_IMAGES_POSTER.jpg",
            contentDescription = null,
        )






      */
/*  ImageRequest.Builder(LocalContext.current)
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
        )*//*




*/


@Composable
fun MovieCard(sliderInfo: Slider.Sliderinfo?) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            //   .wrapContentSize()

            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .border(2.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp))
    )

    {
        // Background image
        val imagePath = "https://vod.nobino.ir/vod/" + sliderInfo?.imageHorizontalPath.orEmpty()
        AsyncImage(
            model = imagePath,
            contentDescription = "Movie Poster",
            modifier = Modifier
                // .fillMaxSize(),
                .height(400.dp)
                .width(300.dp),

            contentScale = ContentScale.Inside,
            //   clipToBounds = true

        )


        /*  Image(
            painter = painter, // Replace with your image resource
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )*/

        // Gradient overlay

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 100f
                    )
                ),
            contentAlignment = Alignment.TopCenter

        )

        {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )

            {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    horizontalArrangement = Arrangement.SpaceBetween
                )


                {
                    sliderInfo?.product?.name?.let { Text(it) }
                    Text("hiihihihihih")

                }
            }


        }


        // Movie details


    }


}


@Composable
fun MovieCard1(sliderInfo: Slider.Sliderinfo?) {
    Box(

        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            // .padding(50.dp)
            //  .wrapContentSize()

            .clip(RoundedCornerShape(12.dp))
            .background(Color.Green)
            .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

        contentAlignment = Alignment.Center

    )

    {
        // Background image
        val imagePath = "https://vod.nobino.ir/vod/" + sliderInfo?.imageHorizontalPath.orEmpty()
        AsyncImage(
            model = imagePath,
            contentDescription = "Movie Poster",
            modifier = Modifier
                // .fillMaxSize(),
                .width(300.dp)
                .height(400.dp),


            contentScale = ContentScale.Crop,
            //   clipToBounds = true

        )


        /*  Image(
            painter = painter, // Replace with your image resource
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )*/

        // Gradient overlay

        Box(
            modifier = Modifier
                .fillMaxSize(0.8f)

                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 100f
                    )
                ),
            contentAlignment = Alignment.BottomStart


        )

        {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                //  verticalArrangement = Arrangement.Bottom


            )

            {
                Row(
                    //modifier = Modifier.size(60.dp),


                )
                {
                    //  Text("MivieName")
                    //  Text("IMDB")
                    NobinoGradientCard("IMDB 4.6")


                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan),
                    horizontalArrangement = Arrangement.SpaceAround


                    //horizontalArrangement = Arrangement.SpaceAround


                )
                {


                    //   NobinoGradientCard("movieName") { }
                    Text(
                        "icon1",
                        color = Color.Red,
                        fontSize = 13.sp


                    )
                    Text(
                        "icon2",
                        fontSize = 13.sp


                    )


                }


                // Movie details


            }


        }


    }


}


@Composable
fun NobinoGradientCard(
    text: String,

    ) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFCA9507),
            Color(0xFFF8B600)
        )
    )

    Card(

        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
            .padding(8.dp),

        shape = MaterialTheme.roundedShape.large,


        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Transparent to show gradient
        ),
        //contentPadding = ButtonDefaults.ContentPadding
    )
    {
        Box(
            modifier = Modifier
                .background(brush = gradientBrush)
                .wrapContentSize()
                .fillMaxSize()

                .height(50.dp),

            contentAlignment = Alignment.Center,

            )
        {
            Text(
                style = MaterialTheme.typography.nobinoLarge,
                //  textAlign = TextAlign.Center,

                text = text,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis


            )
        }


    }

    @Composable
    fun MovieCard1(sliderInfo: Slider.Sliderinfo?) {
        Box(

            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                // .padding(50.dp)
                //  .wrapContentSize()

                .clip(RoundedCornerShape(12.dp))
                .background(Color.Green)
                .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

            contentAlignment = Alignment.Center

        )

        {
            // Background image
            val imagePath = "https://vod.nobino.ir/vod/" + sliderInfo?.imageHorizontalPath.orEmpty()
            AsyncImage(
                model = imagePath,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    // .fillMaxSize(),
                    .width(300.dp)
                    .height(400.dp),


                contentScale = ContentScale.Crop,
                //   clipToBounds = true

            )


            /*  Image(
                painter = painter, // Replace with your image resource
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )*/

            // Gradient overlay

            Box(
                modifier = Modifier
                    .fillMaxSize(0.8f)

                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100f
                        )
                    ),
                contentAlignment = Alignment.BottomStart


            )

            {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    //  verticalArrangement = Arrangement.Bottom


                )

                {
                    Row(
                        //modifier = Modifier.size(60.dp),


                    )
                    {
                        //  Text("MivieName")
                        //  Text("IMDB")
                        NobinoGradientCard("IMDB 4.6")


                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan),
                        horizontalArrangement = Arrangement.SpaceAround


                        //horizontalArrangement = Arrangement.SpaceAround


                    )
                    {


                        //   NobinoGradientCard("movieName") { }
                        Text(
                            "icon1",
                            color = Color.Red,
                            fontSize = 13.sp


                        )
                        Text(
                            "icon2",
                            fontSize = 13.sp


                        )


                    }


                    // Movie details


                }


            }


        }


    }


}

@Composable
fun MovieCardtest(sliderInfo: Slider.Sliderinfo?) {


    Box(

        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            // .padding(50.dp)
            //  .wrapContentSize()

            .clip(RoundedCornerShape(12.dp))
            .background(Color.Green)
            .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

        //  contentAlignment = Alignment.Center

    )

    {
        // Background image
        val imagePath = "https://vod.nobino.ir/vod/" + sliderInfo?.imageHorizontalPath.orEmpty()
        AsyncImage(
            model = imagePath,
            contentDescription = "Movie Poster",
            modifier = Modifier
                // .fillMaxSize(),
                .width(300.dp)
                .height(400.dp),


            contentScale = ContentScale.Crop,
            //   clipToBounds = true

        )


        /* Image(
            painter = painterResource(R.drawable.m1),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )*/

        // Gradient overlay

        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 100f
                    )
                ),
            contentAlignment = Alignment.BottomStart


        )

        {

            Column(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(start = 15.dp)
                 //   .background(Color.LightGray),
                //  horizontalAlignment = Alignment.End

                //  verticalArrangement = Arrangement.Bottom


            )

            {

                Row()
                {


                }


                Row()
                {
                    sliderInfo?.product?.screeningState?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.nobinoMedium


                        )


                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                      //  .background(Color.Cyan),
                    //  horizontalArrangement = Arrangement.SpaceAround


                    //horizontalArrangement = Arrangement.SpaceAround


                )

                {
                    sliderInfo?.product?.name?.let {
                        Text(
                            it,


                            style = MaterialTheme.typography.nobinoMedium,
                            // textAlign = TextAlign.Start
                            //  fontSize = 16.sp,
                            //   color = Color.White,
                            //  fontWeight = FontWeight.Bold
                        )
                    }
                }


                //   NobinoGradientCard("movieName") { }


                Row(
                    modifier = Modifier.fillMaxWidth(),


                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment =Alignment.CenterVertically




                )


                {

                    sliderInfo?.product?.imdbRating?.let {
                        NobinoGradientCard("IMDB $it")













                        Text(
                            "icon1",
                            color = Color.Red,
                            fontSize = 13.sp


                        )


                        //  Text("MivieName")
                        //  Text("IMDB")


                        Text(
                            "icon1",
                            color = Color.Red,
                            fontSize = 13.sp


                        )


                    }


                    // Movie details


                }


            }
        }
    }
}



@Composable
fun MovieCardtestByTag(movieInfo: MovieResult.DataMovie.Item) {


    Box(

        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            // .padding(50.dp)
            //  .wrapContentSize()

            .clip(RoundedCornerShape(12.dp))
            .background(Color.Green)
            .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

        //  contentAlignment = Alignment.Center

    )

    {
        if (!movieInfo.images.isNullOrEmpty() && movieInfo.images[0]?.src != null) {
            val imagePath = "https://vod.nobino.ir/vod/" + movieInfo.images[0]?.src

            AsyncImage(
                model = imagePath,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    // .fillMaxSize(),
                    .width(300.dp)
                    .height(400.dp),


                contentScale = ContentScale.Crop,
                //   clipToBounds = true

            )


        }































            /* Image(
            painter = painterResource(R.drawable.m1),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )*/

            // Gradient overlay

            Box(
                modifier = Modifier
                    .fillMaxSize()

                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100f
                        )
                    ),
                contentAlignment = Alignment.BottomStart


            )

            {

                Column(
                    modifier = Modifier
                        // .fillMaxWidth()
                        .padding(start = 15.dp)
                    //   .background(Color.LightGray),
                    //  horizontalAlignment = Alignment.End

                    //  verticalArrangement = Arrangement.Bottom


                )

                {

                    Row()
                    {


                    }


                    Row()
                    {
                        movieInfo?.name?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.nobinoMedium


                            )


                        }
                    }



                    Row()
                    {
                        movieInfo?.name?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.nobinoMedium


                            )


                        }
                    }














                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        //  .background(Color.Cyan),
                        //  horizontalArrangement = Arrangement.SpaceAround


                        //horizontalArrangement = Arrangement.SpaceAround


                    )

                    {
                        movieInfo?.name?.let {
                            Text(
                                it,


                                style = MaterialTheme.typography.nobinoMedium,
                                // textAlign = TextAlign.Start
                                //  fontSize = 16.sp,
                                //   color = Color.White,
                                //  fontWeight = FontWeight.Bold
                            )
                        }
                    }


                    //   NobinoGradientCard("movieName") { }


                    Row(
                        modifier = Modifier.fillMaxWidth(),


                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically


                    )


                    {

                        movieInfo?.imdbCode?.let {
                            NobinoGradientCard("IMDB $it")













                            Text(
                                "icon1",
                                color = Color.Red,
                                fontSize = 13.sp


                            )


                            //  Text("MivieName")
                            //  Text("IMDB")


                            Text(
                                "icon1",
                                color = Color.Red,
                                fontSize = 13.sp


                            )


                        }


                        // Movie details


                    }


                }
            }
        }
    }











@Composable
fun SeriesCardtest(item: MovieResult.DataMovie.Item) {
   // val items = seriesInfo.items?: emptyList()









           //MovieResult.DataMovie

    Box(

        modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            // .padding(50.dp)
            //  .wrapContentSize()

            .clip(RoundedCornerShape(12.dp))
            .background(Color.Green)
            .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

        //  contentAlignment = Alignment.Center

    )

    {
        // Background image



        if (!item.images.isNullOrEmpty() && item.images[0]?.src != null)
            {
                val imagePath = "https://vod.nobino.ir/vod/" + item.images[0]?.src

                AsyncImage(
                    model = imagePath,
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        // .fillMaxSize(),
                        .width(300.dp)
                        .height(400.dp),


                    contentScale = ContentScale.Crop,
                    //   clipToBounds = true

                )



            }






        /* Image(
            painter = painterResource(R.drawable.m1),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )*/

        // Gradient overlay

        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 100f
                    )
                ),
            contentAlignment = Alignment.BottomStart


        )

        {

            Column(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(start = 15.dp)
                //   .background(Color.LightGray),
                //  horizontalAlignment = Alignment.End

                //  verticalArrangement = Arrangement.Bottom


            )

            {
                Row()
                {
                    item.category?.let {
                        Text(it,
                            style = MaterialTheme.typography.nobinoMedium


                            )

                    }

                }



                Row()
                {
                    item.name?.let {
                        Text(
                            "----${it}----",
                            style = MaterialTheme.typography.nobinoMedium


                        )


                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    //  .background(Color.Cyan),
                    //  horizontalArrangement = Arrangement.SpaceAround


                    //horizontalArrangement = Arrangement.SpaceAround


                )

                {
                    item.name?.let {
                        Text(
                            it,


                            style = MaterialTheme.typography.nobinoMedium,
                            // textAlign = TextAlign.Start
                            //  fontSize = 16.sp,
                            //   color = Color.White,
                            //  fontWeight = FontWeight.Bold
                        )
                    }
                }


                //   NobinoGradientCard("movieName") { }


                Row(
                    modifier = Modifier.fillMaxWidth(),


                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment =Alignment.CenterVertically




                )


                {

                    item.name.let {
                        NobinoGradientCard("IMDB $it")













                        Text(
                            "icon1",
                            color = Color.Red,
                            fontSize = 13.sp


                        )


                        //  Text("MivieName")
                        //  Text("IMDB")


                        Text(
                            "icon1",
                            color = Color.Red,
                            fontSize = 13.sp


                        )


                    }


                    // Movie details


                }


            }
        }
    }
}
//navController.navigate(Screen.Product.withArgs(tag.tags?.get(0).toString()))
@Composable
fun NobinoSpecialRow(title:String,navController: NavHostController,movieCat: MovieCat.MovieCatData,category:String) {


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 10.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceAround


    )
    {

        Text(title)
        Text("ShowMore",
            modifier = Modifier.clickable {
                Log.d("category", "Nobino Button Category is${category}")
                //Log.d("test1","tag data is : "+movieCat.tags?.get(0).toString())

                navController.navigate(
                    Screen.Product.withArgs(
                        movieCat.tags?.get(0).toString(),
                        category

                    )


                )


            }


        )


    }





}

@Composable
fun NobinoDefultButton(
    text: String,
    onClick: () -> Unit ) {


    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground


        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(
                start = androidx.compose.material.MaterialTheme.spacing.semiLarge,
                end = androidx.compose.material.MaterialTheme.spacing.semiLarge,
                bottom = androidx.compose.material.MaterialTheme.spacing.medium
            ),
        shape = MaterialTheme.roundedShape.small
    ) {
        androidx.compose.material.Text(
            text = text,
            color = Color.White,
            style = androidx.compose.material.MaterialTheme.typography.h5
        )


    }


}

