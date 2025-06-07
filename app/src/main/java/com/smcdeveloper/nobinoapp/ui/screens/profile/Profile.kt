package com.smcdeveloper.nobinoapp.ui.screens.profile


import android.annotation.SuppressLint
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Canvas

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import coil3.util.DebugLogger
import coil3.util.Logger
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.profile.ActiveUserProfile
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.divider
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMediumLight
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.LoginViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel= hiltViewModel(),
    dataStore: DataStoreViewModel= hiltViewModel(),
    loginViewModel: LoginViewModel




)










{

    //profileViewModel.updateState(ProfileScreenState.LOGIN_STATE)
    ProfilePage(navController = navController, dataStore = dataStore, viewModel = profileViewModel, loginViewModel =loginViewModel )



    fun test(): Boolean
    {
        return false

    }









    Box(
        contentAlignment = Alignment.CenterStart


    )
    {





    }



}

@Composable
fun Profile1(){

    @Composable
    fun ProfilePage2() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.profile_image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.nobinoLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Bio/Description
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Android Developer | Tech Enthusiast | Open Source Contributor",
                style = MaterialTheme.typography.nobinoLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = { /* Follow Action */ }) {
                    Text("Follow")
                }
                OutlinedButton(onClick = { /* Message Action */ }) {
                    Text("Message")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Details List
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileDetailItem(icon = Icons.Default.LocationOn, detail = "San Francisco, CA")
                ProfileDetailItem(icon = Icons.Default.Face, detail = "Works at Google")
                ProfileDetailItem(icon = Icons.Default.Email, detail = "johndoe@example.com")
            }
        }
    }
}



@Composable
fun ProfileDetailItem(icon: ImageVector, detail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
        Text(
            text = detail,
            style = MaterialTheme.typography.nobinoMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
   // ProfilePage()
}

@Composable
fun ProfilePage(navController: NavHostController,
         viewModel: ProfileViewModel,
          dataStore:DataStoreViewModel,
          loginViewModel: LoginViewModel




) {











    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1818))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    )




    {
        ProfileSection(navController, viewModel =viewModel, loginViewModel = loginViewModel)
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        ProfileScreen1(navController = navController,dataStore=dataStore, loginViewModel = loginViewModel)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileSection(navController: NavHostController,viewModel: ProfileViewModel,loginViewModel: LoginViewModel) {

    var enableAddNewProfile = mutableStateOf(false)


    LaunchedEffect(Unit) {

        viewModel.getUserProfile()


    }

     val profile by viewModel.activeUserProfile.collectAsState()
      var profileData: List<ActiveUserProfile> = emptyList()
    val loginstate = loginViewModel.isUserLogin.collectAsState()


    when(profile)
          {
              is NetworkResult.Success ->
              {
                  profileData= profile.data!!
                  Log.d("active", profileData.toString())
                 Log.d("active", profileData[0].name.toString())


              }

              is NetworkResult.Loading ->
              {


              }

              is NetworkResult.Error ->
              {


              }







          }











    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Existing Profiles
        Row(verticalAlignment = Alignment.CenterVertically) {

            if (loginstate.value)
            {

                if (profileData.size == 3) {


                    ProfileAvatar(name = "mainAccount", image = "")
                    {


                    }



                    ProfileAvatar(name = "18+ years", image = profileData[1].image)
                    {


                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    ProfileAvatar(name = "3-10 years", image = profileData[2].image)
                    {}


                } else if (profileData.size == 2) {
                    enableAddNewProfile.value = true

                    ProfileAvatar(name = "mainAccount", image = profileData[0].image ?: "test")
                    {


                    }



                    ProfileAvatar(name = "18+ years", image = profileData[1].image)
                    {


                    }


                } else {
                    enableAddNewProfile.value = true
                    ProfileAvatar(name = "mainAccount", image = "")
                    {


                    }
                }

        }


        }

        // Add New Profile

        if(enableAddNewProfile.value)
        {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { /* Handle click */ }
        ) {


            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            )


            {
                Image(painterResource(R.drawable.add_member), "",
                    Modifier.clip(CircleShape)
                        .size(56.dp)
                        .clickable {

                            navController.navigate(Screen.NewMember.route)


                        }


                )

                Text("+", style = TextStyle(fontSize = 24.sp, color = Color.White))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("New Account", fontSize = 12.sp)
        }
        }
    }
}

@Composable
fun ProfileAvatar(name: String,image:String?,onProfileClick:()->Unit) {










    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp),
              //  .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {


            Canvas (modifier =Modifier.size(150.dp)) {


                val colors = listOf(Color.Red, Color.Red, Color.Red, Color.Blue, Color.Red)
                val colors1 = listOf(Color.Red, Color.Black,Color.Red)


                val brush = Brush.linearGradient(colors1)
                drawRect(
                    brush = brush,
                    topLeft = Offset(0f, 0f),
                    //  size= Size(400f, 400f),
                    style = Stroke(width = 10f, cap = StrokeCap.Round)
                )
            }











            // Placeholder for Avatar Image
            if(image!!.isBlank() || image.isEmpty())


            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            else{

                SvgImage(image,false)
                {








                }








            }






        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontSize = 12.sp)
    }
}




@Composable
fun SvgImage(base64Svg: String
             ,isSelected:Boolean =false,

             onClick:()->Unit




) {
    val context = LocalContext.current






// Use Coil to load the byte array into the ImageView




    // Create an ImageLoader with SVG support using Coil 3
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }.logger(DebugLogger(Logger.Level.Debug))
        .build()


    // Construct a valid Data URI for the SVG


    val imageByteArray = Base64.decode(base64Svg, Base64.DEFAULT)





    // Build the ImageRequest
    val imageRequest = ImageRequest.Builder(context)

        .data(imageByteArray)
        .crossfade(true)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = "SVG Image",
        modifier = Modifier.clip(
            RectangleShape
        )
            //.border(width = 5.dp, shape = RectangleShape, color = if(isSelected) Color.Red else Color.Gray)
            .size(64.dp)
            .clickable(onClick = onClick)

        ,

        clipToBounds = true,
        contentScale = ContentScale.FillBounds,




        imageLoader = imageLoader
    )
}



























@Composable
fun MenuOptions() {
    val options = listOf(
        "Edit Profile" to android.R.drawable.ic_menu_edit,
        "Subscription" to android.R.drawable.ic_menu_manage,
        "Payment History" to android.R.drawable.ic_menu_view,
        "FAQs" to android.R.drawable.ic_menu_help,
        "Terms & Conditions" to android.R.drawable.ic_menu_info_details,
        "Contact Us" to android.R.drawable.ic_menu_call,
        "Logout" to android.R.drawable.ic_menu_close_clear_cancel
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        options.forEach { (label, icon) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp)

                )
                Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}







@Composable
fun ProfileItem1(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}










@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen1(navController: NavController,dataStore: DataStoreViewModel,loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val profileItems = mapOf(
        stringResource(id = R.string.edit_profile) to Screen.EditProfile,
        stringResource(id = R.string.buy_subscription) to Screen.BuySubscription,
        stringResource(id = R.string.payment_history) to Screen.PaymentHistory,
        stringResource(id = R.string.faq) to Screen.FAQ,
        stringResource(id = R.string.terms_conditions) to Screen.TermsAndConditions,
        stringResource(id = R.string.contact_us) to Screen.ContactUs,

        )



   Scaffold(
       snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
       backgroundColor = Color.Black


   ) {


       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(16.dp)
       )


       {


           item {


               ProfileItem2 (
                   title = stringResource(R.string.edit_profile),
                   icon = painterResource(R.drawable.edit_profile)


               )
               {
                   if(USER_LOGIN_STATUS) {
                       navController.navigate(Screen.EditProfile.route)
                       {
                           launchSingleTop = true
                       }
                   }

                   else
                   {
                       coroutineScope.launch {
                           snackbarHostState.showSnackbar("ابتدا وارد شوید")

                       }


                   }


               }

           }



           item {


               ProfileItem2 (
                   title = stringResource(R.string.buy_subscription),
                   icon = painterResource(R.drawable.buy_subscription)


               )
               {
                   navController.navigate(Screen.BuySubscription.route)
                   {
                       launchSingleTop = true
                   }


               }

           }
           item {


               ProfileItem2 (
                   title = stringResource(R.string.payment_history),
                   icon = painterResource(R.drawable.payment_history)


               )
               {
                   navController.navigate(Screen.PaymentHistory.route)
                   {
                       launchSingleTop = true
                   }


               }

           }

           item {


               ProfileItem2 (
                   title = stringResource(R.string.faq),
                   icon = painterResource(R.drawable.faq)


               )
               {
                   navController.navigate(Screen.FAQ.route)
                   {
                       launchSingleTop = true
                   }


               }

           }

           item {


               ProfileItem2 (
                   title = stringResource(R.string.terms_conditions),
                   icon = painterResource(R.drawable.terms)


               )
               {
                   navController.navigate(Screen.TermsAndConditions.route)
                   {
                       launchSingleTop = true
                   }


               }

           }

           item {


               ProfileItem2 (
                   title = stringResource(R.string.terms_conditions),
                   icon = painterResource(R.drawable.terms)


               )
               {
                   navController.navigate(Screen.TermsAndConditions.route)
                   {
                       launchSingleTop = true
                   }


               }

           }


           item {


               ProfileItem2 (
                   title = stringResource(R.string.contact_us),
                   icon = painterResource(R.drawable.contact_us)


               )
               {
                   navController.navigate(Screen.ContactUs.route)
                   {
                       launchSingleTop = true
                   }


               }

           }


































           item {
               ProfileItem(stringResource(id = R.string.logout))
               {


                   dataStore.saveUserToken("")
                   dataStore.saveUserFirstName("")
                   dataStore.saveUserPhone("")
                   dataStore.saveUserLastName("")
                   dataStore.saveUserLoginStatus(false)
                   loginViewModel.updateLoging(false)



               }


           }
       }




   }






    }






















    @Composable
    fun ProfileItem(title: String, onClick: () -> Unit) {


        Column(

        ) {

            //   Icon(icon,"")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            )

            {
                Text(
                    text = title,
                    style = MaterialTheme.typography.nobinoMediumLight,


                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    //color = Color.White
                )

            }




        }

    }


    @Composable
    fun ProfileItem2(title: String, icon:Painter, onClick: () -> Unit) {


        Column(

        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            )



            {

                Row()
                {

                    Icon(icon,"",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)




                    )


                    Text(
                        text = title,
                        style = MaterialTheme.typography.nobinoMediumLight,


                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 18.sp,
                        //color = Color.White
                    )


                }




                Icon(
                    painterResource(R.drawable.left),


                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(32.dp),


                    contentDescription = ""
                )


            }

            Spacer(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.divider)

            )


        }

    }


    data class ProfileItemData(val titleResId: Int, val icon: ImageVector, val screen: Screen)
