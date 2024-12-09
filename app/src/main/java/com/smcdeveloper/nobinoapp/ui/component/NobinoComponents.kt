package com.smcdeveloper.nobinoapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Scale
import com.smcdeveloper.nobinoapp.R

import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobinoText()
{

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
fun NobinoButton(text:String)
{
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
            .height(50.dp)
          //  .background(Color.Green)
        ,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.onBackground






        )






    ) {

          Text(text,
              style = MaterialTheme.typography.nobinoMedium


          )

            //  modifier = Modifier.background(Color.Yellow)






    }

}

@Composable
fun NobinoOutlineButton()
{
    OutlinedButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
        //  .background(Color.Green)
        ,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,



              ),
        shape =  ButtonDefaults.outlinedShape,
       // border = ButtonDefaults.outlinedButtonBorder(enabled)





        content = {},
    )








}


@Composable
fun FilmBox(movieTitle:String) {
    Box(
        modifier = Modifier.fillMaxSize()
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
fun FilmBox2(movieUrl:String)
{


    Box()
    {

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data =movieUrl )
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















    }


















