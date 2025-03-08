package com.smcdeveloper.nobinoapp.ui.screens.Actors


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.ActorsViewModel


@Composable
fun ActorScreen(
    navController: NavHostController, viewModel: ActorsViewModel = hiltViewModel(),
    actorId: Int

) {


    //  ActorProfileCard(viewModel)
    ShowActorDetails(viewModel, actorId)
    // ShowContent(actorId:int)


}

@Composable

fun ShowContent(actorId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 150.dp)


    )

    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
                .height(300.dp),
            //  contentAlignment = Alignment.Center


        )


        {


            Box(
                modifier = Modifier
                    .offset(y = (-100).dp)
                    .size(200.dp)
                    .background(color = Color.Cyan, shape = CircleShape)


            )


            {
                Text(
                    "HELLO.....",
                    modifier = Modifier
                        .background(Color.Green),
                )

            }


        }


    }


}


@Composable
fun ShowActorDetails(viewModel: ActorsViewModel, actorId: Int) {

    val actor by viewModel.actor.collectAsState()

    LaunchedEffect(Unit) {


        viewModel.getActorDetail(actorId)


    }




    when (actor) {
        is NetworkResult.Loading -> {


        }

        is NetworkResult.Error -> {


        }


        is NetworkResult.Success -> {

            val currentActor = actor.data?.personInfo
            if (currentActor != null) {


                Log.d("actor", currentActor[0].toString())


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(top = 50.dp, start = 10.dp, end = 10.dp)


                )


                {
                    ShowHeader(stringResource(R.string.Actors))

                    ActorProfileCard(currentActor[0])


                }


            }


        }


    }


}


@Composable
fun ActorProfileCard1(
    actor: PersonInfo

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.DarkGray, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {


                // Instagram Icon


                Spacer(modifier = Modifier.width(8.dp))

                // Actor Name
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = actor.name,
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = actor.translatedName,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Profile Image
                AsyncImage(
                    model = IMAGE_BASE_URL + actor.imagePath,
                    contentDescription = "Actor Image",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Blue, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Biography Text
            Text(
                text = actor.description,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}


@Composable
fun ActorProfileCard(actor: PersonInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.DarkGray, RoundedCornerShape(16.dp))

            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
                top = 10.dp
            ) // Extra top padding for floating image
    )

    {
        Column {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )


            {


                // ✅ Instagram Icon (Left)


                ShowActorImage(
                    imagePath = IMAGE_BASE_URL + actor.imagePath


                )






                Spacer(modifier = Modifier.width(8.dp))

                // ✅ Actor Name (Center)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = actor.name,
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = actor.translatedName,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


           actor.instagramLink?.let {



               Image(
                   painter = painterResource(R.drawable.insta_logo),
                   contentDescription = "Instagram",
                   modifier = Modifier
                       // .size(48.dp)


                       //.padding(8.dp)
                       .clickable {




                           /* Open Instagram Link */

                       }


               )






           }






            }




                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Biography Text
                Text(
                    text = actor.description,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify
                )
            }


        }


    }



    @Composable
    fun ShowActorImage(imagePath: String) {

        ///ShowOverlayBox
        Box(
            Modifier
                //.height(400.dp)
                //.height(200.dp)
                //.background(Color.Yellow)
                .offset(y = (-20).dp)
                .padding(start = 20.dp),

            contentAlignment = Alignment.TopStart


            //.background(Color.Red)

        )


        {


            AsyncImage(
                model = imagePath,
                // model = IMAGE_BASE_URL + currentActor[0].imagePath,
                contentDescription = "Actor Image",
                contentScale = ContentScale.FillBounds,

                modifier = Modifier
                    .size(80.dp) // Adjust size as needed
                    .clip(CircleShape)
                    .border(2.dp, Color.Red, CircleShape)
                //.align(Alignment.TopEnd) // ✅ Position image on the right
                //.offset(y = (-100).dp) // ✅ Move it half out of the box

            )


        }


    }

    @Composable
    fun ShowHeader(title: String) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
               // .background(Color.Blue)
                .padding(horizontal = 10.dp),
               // .height(30.dp)
            horizontalArrangement = Arrangement.SpaceBetween


        )
        {
            Text(
                title,
                style = MaterialTheme.typography.nobinoLarge


            )


            Icon(
                painterResource(R.drawable.arrow_left), "",
                tint = Color.White


            )


        }


    }






