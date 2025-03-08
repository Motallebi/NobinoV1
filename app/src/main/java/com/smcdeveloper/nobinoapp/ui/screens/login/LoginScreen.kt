package com.smcdeveloper.nobinoapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.component.NobinoButton
import com.smcdeveloper.nobinoapp.ui.component.NobinoOutlineButton
import com.smcdeveloper.nobinoapp.ui.component.NobinoText

import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel= hiltViewModel()





) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(top = 40.dp, start = 30.dp, end = 30.dp),
        contentAlignment = Alignment.Center


    )

    {



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)

            )


            {


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {

                    Image(
                        painterResource(R.drawable.nobino_logo_title_large),
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),

                    horizontalArrangement = Arrangement.End
                )


                {

                    Text(
                        "ورود",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.nobinoLarge,
                        fontWeight = FontWeight.Bold
                    )


                }

                Row()
                {

                    Text(
                        stringResource(R.string.input_phone_please),
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.nobinoMedium,
                        fontWeight = FontWeight.Bold,)





                }










                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    NobinoText()

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    NobinoButton("", modifier = Modifier)

                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    NobinoOutlineButton()

                }








            }





    }


}













































