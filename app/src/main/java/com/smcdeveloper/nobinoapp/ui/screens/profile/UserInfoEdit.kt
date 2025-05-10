package com.smcdeveloper.nobinoapp.ui.screens.profile

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
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.USER_ID
import com.smcdeveloper.nobinoapp.util.Constants.USER_PHONE
import com.smcdeveloper.nobinoapp.util.Constants.USER_PROFILE_ID
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel


@Composable
fun EditUserInfoPage(navController: NavHostController,
                     viewModel: ProfileViewModel = hiltViewModel(),
                     dataStore:DataStoreViewModel= hiltViewModel()



) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val userProfileResult by viewModel.userProfile.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile("Bearer "+dataStore.getUserToken().toString())
        Log.d("user","user info ...")
        Log.d("user","user info ..."+dataStore.getUserToken())

    }

         when(userProfile)
         {
             is NetworkResult.Success->{


                 Column(
                     modifier = Modifier
                         .fillMaxSize()
                         .background(Color(0xFF121212))
                         .padding(16.dp),
                     verticalArrangement = Arrangement.spacedBy(16.dp)
                 ) {
                     Text(
                         text = "ویرایش اطلاعات کاربری",
                         style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
                         modifier = Modifier.align(Alignment.CenterHorizontally)
                     )

                     Spacer(modifier = Modifier.height(16.dp))

                     if (isLoading) {
                         CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.CenterHorizontally))
                         /* } else if (errorMessage != null) {
                              Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))*/
                     }

                     else {
                         userProfile?.let {
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
                                     it.data?.profileData?.mobile.toString()


                                     )
                                     EditableFieldWithButtons(label = "شماره موبایل:", initialValue = it.data?.profileData?.mobile.toString()) { newValue ->
                                         // viewModel.updatePhone(newValue)
                                     }
                                     EditableFieldWithButtons(label = "تاریخ تولد:", initialValue = "") { newValue ->
                                         // viewModel.updateBirthdate(newValue)
                                     }
                                     EditableFieldWithButtons(label = "ایمیل:", initialValue = it.data?.profileData?.email.toString()  ) { newValue ->




                                         // viewModel.updateEmail(newValue)
                                     }
                                 }
                             }
                         }
                     }
                 }





             }

             is NetworkResult.Loading->{
                 Box(modifier = Modifier.fillMaxSize(),
                     contentAlignment = Alignment.Center


                     )
                 {
                     CircularProgressIndicator(color = Color.Red)

                 }






             }

             else ->{




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


















