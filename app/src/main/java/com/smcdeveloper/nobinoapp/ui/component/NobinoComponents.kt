package com.smcdeveloper.nobinoapp.ui.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.Purple80
import com.smcdeveloper.nobinoapp.ui.theme.RedPrimary
import com.smcdeveloper.nobinoapp.ui.theme.mianAppTextInput
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
            focusedTextColor = MaterialTheme.colorScheme.mianAppTextInput,
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
            contentColor = Purple80,
            containerColor = RedPrimary






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
            contentColor = Purple80,



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












