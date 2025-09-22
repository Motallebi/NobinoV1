package com.smcdeveloper.nobinoapp.ui.component


import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.View.TEXT_DIRECTION_RTL
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Scale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.Section
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.ageSelectedButton
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.ui.theme.roundedShape
import com.smcdeveloper.nobinoapp.ui.theme.spacing
import com.smcdeveloper.nobinoapp.util.Constants.DEFAULT_IMAGE_POSETR
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL


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
fun HtmlText(html: String, modifier: Modifier = Modifier, textColor: Color = Color.Black) {

   val Description= HtmlCompat.FROM_HTML_MODE_LEGACY.toString()


    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    //.subSequence(0,100)


                movementMethod = LinkMovementMethod.getInstance() // Enables clickable links
                setTextColor(textColor.toArgb())
                gravity=Gravity.START
                textDirection= TEXT_DIRECTION_RTL
                maxWidth=20







            }
        },
        update = { textView ->
          textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
              //.subSequence(0,50)



        },

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
    text: String="",

    ) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFCA9507),
            Color(0xFFF8B600)
        )
    )



    Box(

        modifier = Modifier


           .background(brush = gradientBrush, shape = MaterialTheme.roundedShape.medium)
            .wrapContentSize()
            .padding(5.dp),




      //  shape = MaterialTheme.roundedShape.large,



       /* colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,// Transparent to show gradien

        ),*/
        //contentPadding = ButtonDefaults.ContentPadding
        contentAlignment = Alignment.Center
    )


    {



             Box(
                 modifier = Modifier
                     .background(brush = gradientBrush)
                    .wrapContentSize(),
                     //.fillMaxSize()

                    // .height(50.dp),

                 contentAlignment = Alignment.Center

             )
             {
                 Text(
                     style = MaterialTheme.typography.nobinoMedium,
                     //  textAlign = TextAlign.Center,

                     text = if(text!="") "IMDB $text" else "",
                     color = Color.Black,
                     //  overflow = TextOverflow.Ellipsis


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
            model = if(sliderInfo?.imageHorizontalPath!!.isNotEmpty()) imagePath else DEFAULT_IMAGE_POSETR,
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
                    verticalAlignment = Alignment.CenterVertically


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
fun MovieCardtestByTag(

    movieInfo: MovieResult.DataMovie.Item,
    //onCardClick:()->Unit,
    navController: NavHostController



) {


    Box(

        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            // .padding(50.dp)
            //  .wrapContentSize()
            .clickable {

                if(movieInfo.category.toString() != "SERIES") {

                    navController.navigate(
                        Screen.ProductDetails.withArgs(
                            movieInfo.id.toString()


                        )
                    )
                }
                else if (movieInfo.category.toString() == "SERIES")
                {
                    navController.navigate(Screen.SeriesDetailScreen.withArgs(
                        movieInfo.id.toString())
                    )


                }






            }

            .clip(RoundedCornerShape(16.dp)),




          //  .background(Color.Green)
        // .border(8.dp, Color(0xFF6200EE), RoundedCornerShape(12.dp)),

         // contentAlignment = Alignment.BottomStart

    )

    {
        if (!movieInfo.images.isNullOrEmpty() && movieInfo.images[0]?.src != null) {

         val imageSrc=   movieInfo.images.filter {

                it?.imageType=="BANNER_MOBILE"

            }



            val imagePath = "https://vod.nobino.ir/vod/" + imageSrc[0]?.src.toString()


            AsyncImage(
                model = if (movieInfo.images[0]?.src!!.isNotEmpty()) imagePath else DEFAULT_IMAGE_POSETR,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    // .fillMaxSize(),
                    .width(200.dp)
                    .height(250.dp)


                ,


                contentScale = ContentScale.FillBounds,


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
                     .fillMaxWidth(),
                   //.padding(start = 15.dp)


                  // .background(Color.Red),
                //  horizontalAlignment = Alignment.End

                    verticalArrangement = Arrangement.Bottom


            )

            {

                Row(modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                  //  .background(Color.Yellow)
                )
                {

                    movieInfo?.name?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.nobinoMedium,
                            maxLines = 1



                        )


                    }



                }

                Row(modifier = Modifier.fillMaxWidth(),
                    //.background(Color.Green),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically


                )



                {
                    movieInfo.imdbRating?.let {
                        NobinoGradientCard("IMDB $it")
                    }

                    FeatureIconsRow(isKeyboardAvailable = true, isMicAvailable = true)







                }




            }
        }
    }
}




@Composable
fun FeatureIconsRow(isMicAvailable: Boolean, isKeyboardAvailable: Boolean) {
    Row(
        modifier = Modifier
           // .fillMaxWidth()
          //  .background(Color.Cyan)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        if (isMicAvailable) {
            Icon(
                painter = painterResource(id = R.drawable.mic), // Replace with actual mic icon
                contentDescription = "Microphone",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }

        if (isKeyboardAvailable) {
            Icon(
                painter = painterResource(id = R.drawable.keyboard), // Replace with actual keyboard icon
                contentDescription = "Keyboard",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
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


        if (!item.images.isNullOrEmpty() && item.images[0]?.src != null) {
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
                        Text(
                            it,
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
                    verticalAlignment = Alignment.CenterVertically


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
fun NobinoSpecialRow(
    title: String,
    navController: NavHostController,
    movieCat: MovieCat.MovieCatData,
    category: String,
    categoryName: String,
 //   modifier: Modifier
)
{

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(start =25.dp, end = 10.dp)
          .background(MaterialTheme.colorScheme.background),
         //   .background(Color.Green),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically



    )






    {

        Text(title,
            style = MaterialTheme.typography.nobinoSmall,
            modifier = Modifier.padding(start = 10.dp)




            )


        Row(
            modifier = Modifier
              // .background(Color.Red)
              //  .padding(start = 20.dp)

            ,

            verticalAlignment = Alignment.CenterVertically,

            //horizontalArrangement = Arrangement.SpaceBetween





        )

        {

            Text(
                "مشاهده همه",
                style = MaterialTheme.typography.nobinoSmall,


                modifier = Modifier.clickable {
                    Log.d("category", "Nobino Button Category is${category}")
                    //Log.d("test1","tag data is : "+movieCat.tags?.get(0).toString())

                    navController.navigate(
                        Screen.Product.withArgs(
                            movieCat.tags?.get(0).toString(),
                            category,
                            movieCat.title.toString(),



                            )


                    )


                    //  navController.navigate(Screen.DemoScreen.route)


                }
                    .padding(start = 10.dp)
                ,


                )

            Icon(painterResource(R.drawable.left), "",
                tint = Color.White,
                modifier=Modifier.size(32.dp)




            )















        }





    }


}

@Composable

fun NobinoMainTitleHeader(title:String,navController: NavHostController,modifier: Modifier)
{

    Row( modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        ,
      //  verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween



    )

    {
        Text(title,
            style = MaterialTheme.typography.nobinoLarge,
            modifier = Modifier.padding(top = 5.dp)
            // .background(Color.Red)



        )

        Icon(painterResource(R.drawable.left),"",
            tint = Color.White,
            modifier = Modifier.clickable {

                navController.navigateUp()





            }
                .size(32.dp)





        )








    }






















}




@Composable
fun NobinoExpandableCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    initialExpandedState: Boolean = false,
) {
    // Use rememberSaveable to survive configuration changes.
    var isExpanded by rememberSaveable { mutableStateOf(initialExpandedState) }
    val cardColor = MaterialTheme.colorScheme.ageSelectedButton // Use MaterialTheme.colorScheme
    val textColor = contentColorFor(cardColor)  //use contentColorFor


    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize( // Add animateContentSize for smooth expansion/collapse
                animationSpec = tween(
                    durationMillis = 300,  // Set the duration of the animation
                    easing = LinearEasing // Use a linear easing function
                )
            ),
        shape = MaterialTheme.shapes.medium, // Use MaterialTheme.shapes
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded } // Make the entire card clickable
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            )
            {



                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall, // Use MaterialTheme.typography
                    color = textColor,
                    modifier = Modifier.weight(1f) // Title takes up available space
                )

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = textColor, //match the text color

                        modifier = Modifier.graphicsLayer {
                            // Calculate the rotation angle based on isExpanded
                            rotationZ = if (isExpanded) 180f else 0f
                            transformOrigin = TransformOrigin.Center // Ensure correct rotation
                        }



                    )
                }




            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(

                    modifier.padding(bottom = 10.dp)



                )


                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium, // Use MaterialTheme.typography
                    color = textColor,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}


























@Composable
fun NobinoSpecialRowBySection(
    title: String,
    navController: NavHostController,
    Section: Section.Data,
    category: String,
    modifier: Modifier
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceAround


    )
    {

        Text(title)





        TextField("مشاهده همه", {},
            modifier = modifier.clickable {
                Log.d("category", "Nobino Button Category is${category}")
                //Log.d("test1","tag data is : "+movieCat.tags?.get(0).toString())

                navController.navigate(
                    Screen.Product.withArgs(
                        Section.tags[0].toString(),
                        category

                    )


                )


            },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.mobile_icon),
                    contentDescription = ""

                )
            }


        )


    }


}

@Composable
fun NobinoSpecialRowBySection1(
    title: String,
    navController: NavHostController,
    section: MovieResult.DataMovie.Item,
    category: String
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
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


                        //section.tags?.get(0).toString(),
                        category


                    )
                )


            }


        )


    }


}


@Composable
fun NobinoSpecialRowBySection2(
    title: String,
    navController: NavHostController,
    tags: List<String>,
    category: String,
    modifier: Modifier
) {

    Log.d("categoty", "category is $category")
    Log.d("categoty", "title is $title tags is $tags")

    // Log.d("categoty","tags are ${section.tags.toString()}")

    Row(

        modifier = modifier
            //.fillMaxWidth()
           // .padding(top = 10.dp)
            .padding(start = 30.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically



    )

    {

        Text(title,
            style = MaterialTheme.typography.nobinoSmall


            )

        Row(
            modifier = Modifier,

            verticalAlignment = Alignment.CenterVertically



        )
        {


            Text(
                stringResource(R.string.Show_More),
                style = MaterialTheme.typography.nobinoSmall,


                modifier = Modifier.clickable {
                    Log.d("category", "Nobino Button Category is${category}")
                    //Log.d("test1","tag data is : "+movieCat.tags?.get(0).toString())

                    //  Log.d("category", "Nobino Button tag is${ section.tags?.get(0)?.id.toString()}")
                    //  Log.d("category", "Nobino Button tag is${ section.tags.toString()}")

                    val tagsList = tags
                    val tags = tagsList.joinToString(",")
                    //  Log.d("category", "Nobino Button Category is${tagsJson}")
                    // Log.d("category", "Nobino Button Category is${Uri.encode(tagsJson)}")


                    // navController.navigate("section_screen?tags=${Uri.encode(tagsJson)}")

                    navController.navigate(

                        Screen.Product.withArgs(


                            tags,
                            category,
                            title



                        )

                    )


                }




            )

            Icon(painterResource(R.drawable.left),"",
                tint = Color.White,
                modifier = Modifier.size(32.dp)



            )




        }




    }


}




@Composable
fun NobinoSpecialRowBySectionForKids(
    title: String,
    navController: NavHostController,
    tags: List<String>,
    category: String
) {

    Log.d("categoty", "category is $category")
    Log.d("categoty", "title is $title tags is $tags")

    // Log.d("categoty","tags are ${section.tags.toString()}")

    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp),
           // .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically



    )

    {

        Text(title,
            color = Color.Black


        )

        Row(
            modifier = Modifier,

            verticalAlignment = Alignment.CenterVertically



        )
        {


            Text(
                stringResource(R.string.Show_More),
                color = Color.DarkGray.copy(alpha = 0.4f),

                modifier = Modifier.clickable {
                    Log.d("category", "Nobino Button Category is${category}")
                    //Log.d("test1","tag data is : "+movieCat.tags?.get(0).toString())

                    //  Log.d("category", "Nobino Button tag is${ section.tags?.get(0)?.id.toString()}")
                    //  Log.d("category", "Nobino Button tag is${ section.tags.toString()}")

                    val tagsList = tags
                    val tags = tagsList.joinToString(",")
                    //  Log.d("category", "Nobino Button Category is${tagsJson}")
                    // Log.d("category", "Nobino Button Category is${Uri.encode(tagsJson)}")


                    // navController.navigate("section_screen?tags=${Uri.encode(tagsJson)}")

                    navController.navigate(

                        Screen.Product.withArgs(


                            tags,
                            category,
                            title



                        )

                    )


                }




            )

            Icon(painterResource(R.drawable.arrow_left),"",
                tint = Color.White



            )




        }




    }


}














@Composable
fun NobinoDefultButton(
    text: String,
    onClick: () -> Unit
) {


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







@Composable
fun Loading3Dots(isDark: Boolean) {
    if (isDark) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading3dotsdark))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    } else {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading3dots))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }

}


@Composable
fun LayeredImageBackgroundCard(
    info: MovieResult.DataMovie.Item,
    modifier: Modifier = Modifier,
    overlayColor: Color = Color.Black.copy(alpha = 0.3f)
)

{
    Box(modifier = modifier
        .width(100.dp)
        .height(250.dp)
        .background(color = Color.Black, shape = MaterialTheme.roundedShape.medium)





    )
    {
        // First layer: full image (base layer)
        AsyncImage(
           model = IMAGE_BASE_URL+info.images?.get(0)?.src.toString(),

            contentDescription = "Layer 1",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .clip(MaterialTheme.roundedShape.medium)

        )

        // Second layer: same image with a transparency effect
        AsyncImage(
            model = IMAGE_BASE_URL+info.images?.get(0)?.src.toString(),
            contentDescription = "Layer 2",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .offset( y=(-4).dp)

                .alpha(0.2f)  // Adjust transparency as needed
                .clip(MaterialTheme.roundedShape.medium)
        )
        // Third layer: image with additional overlay (color + transparency)
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = IMAGE_BASE_URL+info.images?.get(0)?.src.toString(),
                contentDescription = "Layer 3",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y=(-8).dp)
                    .background(color = Color.Black.copy(alpha = 0.1f), shape = MaterialTheme.roundedShape.medium)
                    .clip(MaterialTheme.roundedShape.medium)

                    .alpha(0.1f) // Further transparency effect
            )
            // Overlay color applied on top of the third image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlayColor,shape = MaterialTheme.roundedShape.medium),
                contentAlignment = Alignment.BottomCenter


            )
            {
                Text("09099099")


            }



        }
    }
}


@Composable
fun SeriesCardWithAnimation(

    info: MovieResult.DataMovie.Item,
    modifier: Modifier = Modifier,
    onClick: () -> Unit



)
{











            Box(
                modifier = Modifier.width(100.dp)
                    .height(200.dp)
                    //  .background(Color.Red)
                    .offset(y=(-10).dp),
                contentAlignment = Alignment.TopCenter


            )
            {
                Box(
                    modifier = modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp)
                        .background(color =Color.DarkGray.copy(alpha = 0.8f), shape = RoundedCornerShape(15.dp))
                        .offset(y=(-10).dp),
                    contentAlignment = Alignment.TopCenter





                )
                {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(200.dp)
                            .background(color =Color.DarkGray.copy(alpha = 0.4f), shape = RoundedCornerShape(15.dp))





                    )
                    {
                        // Text("test")




                    }




                }











            }



            Box(

                modifier = Modifier.width(100.dp)
                    .height(200.dp)
                    .clickable { onClick() }
                   // .background(Color.Red)
                //   .offset(y=(-10).dp),
                // contentAlignment = Alignment.TopCenter








            )
            {
                val imageData  = if(info.images!!.isNotEmpty()) info.images.get(0)?.src.toString() else DEFAULT_IMAGE_POSETR
                AsyncImage(


                    model = IMAGE_BASE_URL+imageData,

                    contentDescription = "Layer 1",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .clip(MaterialTheme.roundedShape.medium)

                )









            }











































}

