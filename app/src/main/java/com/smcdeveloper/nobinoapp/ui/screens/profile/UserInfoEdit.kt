package com.smcdeveloper.nobinoapp.ui.screens.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.USER_ID
import com.smcdeveloper.nobinoapp.util.Constants.USER_PHONE
import com.smcdeveloper.nobinoapp.util.Constants.USER_PROFILE_ID
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditUserInfoPage(navController: NavHostController,
                     viewModel: ProfileViewModel = hiltViewModel(),
                     dataStore:DataStoreViewModel= hiltViewModel()



)
{
    val userProfile by viewModel.userProfile.collectAsState()






    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val message by viewModel.message.collectAsState()

    var userEmail by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userLastName by remember { mutableStateOf("") }
    var userBirthDay by remember { mutableStateOf(0L) }









    var loading by remember {
        mutableStateOf(false)
    }















    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile("Bearer "+dataStore.getUserToken().toString())
        Log.d("user","user info ...")
        Log.d("user","user info ..."+dataStore.getUserToken())

    }

    LaunchedEffect(message) {

        message?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage() // clear message after snackbar is shown
            }
        }




    }






















           when(userProfile)
           {
               is NetworkResult.Loading->{


                   Box(modifier = Modifier.fillMaxSize())
                   {

                       CircularProgressIndicator(color = Color.Red)

                   }



                   loading=true


               }

               is NetworkResult.Error->{
                 //  ShowUserData(userProfile.data!!.profileData,viewModel)

                   loading=false

                   LaunchedEffect(Unit) {
                       snackbarHostState.showSnackbar("error")
                   }






               }



               is NetworkResult.Success->{

                  // ShowUserData(userProfile.data!!.profileData,viewModel)
                   userEmail=userProfile.data!!.profileData.email
                   userPhone=userProfile.data!!.profileData.mobile
                   userName=userProfile.data!!.profileData.username
                   userLastName=userProfile.data!!.profileData.lastName
                   userBirthDay=userProfile.data!!.profileData.birthDate

                   loading=false












               }




           }


    if(!loading)
    {




        Scaffold(

            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }


        )


        {
            /*ShowSampleData(
                userEmail,
                onUpdateData = {data->userEmail=data},
                viewModel = viewModel


            )*/




            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )


            {
                Text(
                    text = "ویرایش اطلاعات کاربری",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))





                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                )
                {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        ReadOnlyField(label = "نام کاربری:", value =userPhone



                        )


                        EditableFieldWithButtons1(label = "شماره موبایل:", initialValue =userPhone.toString()) { newValue ->
                            // viewModel.updatePhone(newValue)
                            //  onProfileUpdte(userProfile)

                        }
                        EditableFieldWithButtons1(label = "تاریخ تولد:", initialValue = "") { newValue ->
                            // viewModel.updateBirthdate(newValue)
                        }
                        EditableFieldWithButtons1(label = "ایمیل:", initialValue = userEmail.toString()  ) { newValue ->


                            viewModel.updateEmail(newValue)
                            viewModel.updateUserInfo(
                                //userId = userId,


                            )

                        }







                    }

                }
            }


















































































        /*    ShowUserData2(
                  userProfile = userInfo.data!!.profileData,
                  viewModel = viewModel


              ) {



              }*/













           /* ShowSampleData(userEmail, viewModel =) {
                    data->userEmail=data


            }*/

           // if(!updateError)
            //ShowUserData(userProfile.data!!.profileData,viewModel)





        }





    }






























    Log.d("user","user profile page   "+userProfile.data?.profileData?.mobile.toString())
    Log.d("user","user profile page    "+userProfile.data?.profileData?.id.toString())
    Log.d("user","user profile page    "+userProfile.data?.profileData?.firstName.toString())
    Log.d("user","user profile page    "+userProfile.data?.profileData?.email.toString())



    Log.d("user","user profile page     "+userProfile.data?.profileData?.username.toString())
    Log.d("user","user profile page       "+userProfile.data?.toString())








    // val errorMessage by viewModel.errorMessage.collectAsState()


}











@Composable
fun ShowSampleData(initialValue:String,onUpdateData:(String)->Unit,viewModel: ProfileViewModel)
{
    var text by remember { mutableStateOf(initialValue) }
    var isEditing by remember { mutableStateOf(false) }
    var tempText by remember { mutableStateOf(initialValue) } // Store temp value for cancel

    TextField(
        value = text,
        onValueChange =
        {
          text=it
          onUpdateData(it)
        }




    )

    Button(
        onClick = {

            viewModel.updateEmail(
               text
            )
            viewModel.updateUserInfo()


        }


    ) {

        Text("UpdateData")

    }





}



@Composable
fun ShowUserData(userProfile:UserProfile,viewModel: ProfileViewModel)
{








    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )

    {
        Text(
            text = "ویرایش اطلاعات کاربری",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))




        userProfile.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            )
            {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    it?.username?.let { it1 ->
                        ReadOnlyField(label = "نام کاربری:", value =
                        it1


                        )
                    }
                    EditableFieldWithButtons(label = "شماره موبایل:", initialValue = it!!.mobile.toString()) { newValue ->
                        // viewModel.updatePhone(newValue)
                    }
                    EditableFieldWithButtons(label = "تاریخ تولد:", initialValue = "") { newValue ->
                        // viewModel.updateBirthdate(newValue)
                    }
                    EditableFieldWithButtons(label = "ایمیل:", initialValue = it!!.email.toString()  ) { newValue ->


                        viewModel.updateEmail(newValue)
                        viewModel.updateUserInfo(
                            //userId = userId,


                        )

                    }
                }
            }
        }

    }































}



@Composable
fun ShowUserData2(viewModel: ProfileViewModel,

   userProfile: UserProfile






)
{







    var profile by remember { mutableStateOf(userProfile) }








    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )


    {
        Text(
            text = "ویرایش اطلاعات کاربری",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))





            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            )
            {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        ReadOnlyField(label = "نام کاربری:", value =
                        profile.username


                        )
                    }
                    EditableFieldWithButtons1(label = "شماره موبایل:", initialValue = profile.mobile.toString()) { newValue ->
                        // viewModel.updatePhone(newValue)
                      //  onProfileUpdte(userProfile)

                    }
                    EditableFieldWithButtons1(label = "تاریخ تولد:", initialValue = "") { newValue ->
                        // viewModel.updateBirthdate(newValue)
                    }
                    EditableFieldWithButtons1(label = "ایمیل:", initialValue = profile.email.toString()  ) { newValue ->


                        viewModel.updateEmail(newValue)
                        viewModel.updateUserInfo(
                            //userId = userId,


                        )

                    }
                }
            }


    }









































@Composable
fun EditUserInfoPage1(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // Dark background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Text(
            text = "ویرایش اطلاعات کاربری",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Group all fields inside a rectangular border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp)) // Dark gray background
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)) // Outer border
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Read-Only Username Field
                ReadOnlyField(label = "نام کاربری:", value = "mir12345678")

                // Editable Fields
                EditableField(label = "شماره موبایل:", initialValue = "09123456789")
                EditableField(label = "تاریخ تولد:", initialValue = "1376/04/28")
                EditableField(label = "ایمیل:", initialValue = "golnaz.moradi1997@gmail.com")
            }
        }
    }
}

// ✅ Read-Only Field (Same Size as Editable Fields)
@Composable
fun ReadOnlyField(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically



    ) {



        Text(
            modifier = Modifier
                .weight(0.3f),
            text = label,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)

        )

        Box(
            modifier = Modifier
                .weight(0.7f)
                .height(50.dp) // Ensure same height as text fields
                .background(Color(0xFF333333), shape = RoundedCornerShape(25.dp)) // Rounded shape
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd // Align text to the right
        ) {
            Text(
                text = value,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
            )
        }
    }
}

// ✅ Editable Field (With Icon & Editing Behavior)
@Composable
fun EditableField(label: String, initialValue: String) {
    var text by remember { mutableStateOf(initialValue) }  // State for text
    var isEditing by remember { mutableStateOf(false) }  // Track if in edit mode

    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically


    ) {
        // Label (Outside TextField)
        Text(
            modifier = Modifier.weight(0.3f),
            text = label,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
        )

        // Text Field
        TextField(
            value = text,
            onValueChange = { text = it },
            readOnly = !isEditing, // Only editable when in edit mode
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = if (isEditing) Color.White else Color.Gray // White when editing, gray otherwise
            ),
            singleLine = true,
            trailingIcon = {
                if (!isEditing) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_edit),
                        contentDescription = "Edit",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { isEditing = true }, // Start editing
                        tint = Color.Gray
                    )
                }
            },
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
                .height(50.dp) // Ensures all fields are the same size
                .border(
                    width = 2.dp,
                    color = if (isEditing) Color.Red else Color.Transparent, // Red border when editing
                    shape = RoundedCornerShape(25.dp)
                )
                .background(Color(0xFF333333), shape = RoundedCornerShape(25.dp)), // Dark background
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent, // Keep background consistent
                focusedIndicatorColor = Color.Transparent, // Remove default underline
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}





@Composable
fun EditableFieldWithButtons(label: String, initialValue: String, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf(initialValue) }
    var isEditing by remember { mutableStateOf(false) }
    var tempText by remember { mutableStateOf(initialValue) } // Store temp value for cancel

  //  val userInfoResult by viewModel.userInfo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {






    }





    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Label (Outside TextField)
            Text(
                modifier = Modifier.weight(0.3f),
                text = label,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            )

            // Text Field
            TextField(
                value = text,
                onValueChange = { text = it },
                readOnly = !isEditing,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isEditing) Color.White else Color.Gray
                ),
                singleLine = true,
                trailingIcon = {
                    if (!isEditing) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_edit),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    tempText = text // Store current value before editing
                                    isEditing = true
                                },
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 2.dp,
                        color = if (isEditing) Color.Red else Color.Transparent,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color(0xFF333333), shape = RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Buttons (Only Show When Editing)
        if (isEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Cancel Button
                Button(
                    onClick = {
                        text = tempText // Reset text
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("انصراف", color = Color.Red)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Save Button
                Button(
                    onClick = {
                        onSave(text) // Save changes
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("اعمال تغییرات", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun EditableFieldWithButtons1(label: String, initialValue:String, onSave: (String) -> Unit)
{
    var text by remember { mutableStateOf(initialValue) }
    var isEditing by remember { mutableStateOf(false) }
    var tempText by remember { mutableStateOf(initialValue) } // Store temp value for cancel

    //  val userInfoResult by viewModel.userInfo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {






    }





    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Label (Outside TextField)
            Text(
                modifier = Modifier.weight(0.3f),
                text = label,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            )

            // Text Field
            TextField(
                value = text,
                onValueChange =
                {
                    text=it

                },


                readOnly = !isEditing,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isEditing) Color.White else Color.Gray
                ),
                singleLine = true,
                trailingIcon = {
                    if (!isEditing) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_edit),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    tempText = text // Store current value before editing
                                    isEditing = true
                                },
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 2.dp,
                        color = if (isEditing) Color.Red else Color.Transparent,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color(0xFF333333), shape = RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Buttons (Only Show When Editing)
        if (isEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Cancel Button
                Button(
                    onClick = {
                        text = tempText // Reset text
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("انصراف", color = Color.Red)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Save Button
                Button(
                    onClick = {
                        onSave(text) // Save changes
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("اعمال تغییرات", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun EditableFieldWithButtons2(label: String, initialValue:String, onSave: (String) -> Unit)
{
    var text by remember { mutableStateOf(initialValue) }
    var isEditing by remember { mutableStateOf(false) }
    var tempText by remember { mutableStateOf(initialValue) } // Store temp value for cancel

    //  val userInfoResult by viewModel.userInfo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {






    }





    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            // Label (Outside TextField)
            Text(
                modifier = Modifier.weight(0.3f),
                text = label,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            )

            // Text Field
            TextField(
                value = text,
                onValueChange =
                {
                    text=it

                },


                readOnly = !isEditing,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isEditing) Color.White else Color.Gray
                ),
                singleLine = true,
                trailingIcon = {
                    if (!isEditing) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_edit),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    tempText = text // Store current value before editing
                                    isEditing = true
                                },
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        width = 2.dp,
                        color = if (isEditing) Color.Red else Color.Transparent,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(Color(0xFF333333), shape = RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Buttons (Only Show When Editing)
        if (isEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Cancel Button
                Button(
                    onClick = {
                        text = tempText // Reset text
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("انصراف", color = Color.Red)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Save Button
                Button(
                    onClick = {
                        onSave(text) // Save changes
                        isEditing = false // Hide buttons
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("اعمال تغییرات", color = Color.White)
                }
            }
        }
    }
}















