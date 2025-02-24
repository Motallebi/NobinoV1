package com.smcdeveloper.nobinoapp.ui.screens.Actors








import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.ActorsViewModel


@Composable
fun ActorScreen(
    navController: NavHostController,viewModel: ActorsViewModel= hiltViewModel(),
    actorId:Int

)
{


  //  ActorProfileCard(viewModel)
      ShowActorDetails(viewModel,actorId)


}

@Composable
fun ShowActorDetails(viewModel: ActorsViewModel,actorId: Int)
{

    val actor by viewModel.actor.collectAsState()

    LaunchedEffect(Unit) {


        viewModel.getActorDetail(actorId)





    }




     when(actor)
     {
         is NetworkResult.Loading -> {




         }

         is NetworkResult.Error ->
         {



         }


         is NetworkResult.Success ->
         {

             val currentActor = actor.data?.personInfo
             if (currentActor != null) {

                 Log.d("actor", currentActor[0].toString())



                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 80.dp)

                ) {



                    Box(
                       Modifier.fillMaxWidth()
                           .height(200.dp)
                           .background(Color.Yellow)
                           .clip(RoundedCornerShape(90.dp)),
                        contentAlignment = Alignment.Center




                           //.background(Color.Red)

                    )
                    {


                        AsyncImage(
                            model = IMAGE_BASE_URL + currentActor[0].imagePath,
                            contentDescription = "Actor Image",
                            modifier = Modifier
                                .size(200.dp) // Adjust size as needed
                                .clip(CircleShape)
                                .border(2.dp, Color.Blue, CircleShape)
                                .align(Alignment.TopEnd) // ✅ Position image on the right
                                //.offset(y = (-50).dp) // ✅ Move it half out of the box
                        )







                    }

                    ActorProfileCard(currentActor[0])



                }



             }













         }






     }











}



@Composable
fun ActorProfileCard1(
    actor:PersonInfo

)

{
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
            ) {
                // Instagram Icon
                Image(
                    painter = painterResource(R.drawable.insta_logo),
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .padding(8.dp) // Inner padding to match the design
                        .clickable {  }
                )

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
                    model = IMAGE_BASE_URL+actor.imagePath,
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
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 50.dp) // Extra top padding for floating image
    ) {
        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )


            {
                // ✅ Instagram Icon (Left)
                Image(
                    painter = painterResource(R.drawable.insta_logo),
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                        .padding(8.dp)
                        .clickable { /* Open Instagram Link */ }
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

        // ✅ Profile Image (Right & Floating Over Box)
        AsyncImage(
            model = IMAGE_BASE_URL + actor.imagePath,
            contentDescription = "Actor Image",
            modifier = Modifier
                .size(90.dp) // Adjust size as needed
                .clip(CircleShape)
                .border(2.dp, Color.Blue, CircleShape)
                .align(Alignment.TopEnd) // ✅ Position image on the right
                .offset(y = (-50).dp) // ✅ Move it half out of the box
        )
    }
}







