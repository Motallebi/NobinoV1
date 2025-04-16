package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.TextField


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.NobinoMainTitleHeader
import com.smcdeveloper.nobinoapp.ui.theme.ageSelectedButton


@Composable
fun NewMemberPage(navController: NavHostController) {
    val ageGroupOptions = listOf("۳ تا ۱۰ سال", "۱۰ تا ۱۵ سال", "18 تا 15 سال", "۱۸ سال به بالا")
    val selectedAgeGroup = remember { mutableStateOf(ageGroupOptions[1]) }
    val expanded = remember { mutableStateOf(false) }
    var activeButton by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black),
        contentColor = Color.Black,
        color = Color.Black


    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                // .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )


        {


            NobinoMainTitleHeader("newmember", navController)











            Spacer(modifier = Modifier.height(8.dp))

            // Instruction Text
            Text(
                text = "لطفا بازه سنی خود را جهت تماشای فیلم انتخاب کنید",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Dropdown Menu
            Box(modifier = Modifier.fillMaxWidth()



            )





            {

                Column() {


                    CustomComboBoxRTL2(ageGroupOptions) {

                        activeButton = true


                        selectedAgeGroup.value=it


                    }

                  /*  SearchCombo()
                    {
                        activeButton = true


                        selectedAgeGroup.value=it


                    }*/












                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {

                            navController.navigate(Screen.NewMemberSelection.route)


                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (activeButton) Color.Red else Color.Red.copy(alpha = 0.50f)


                        ),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "ثبت و ادامه",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }









                }








                /* OutlinedButton(

                    onClick = { expanded.value = !expanded.value },
                    shape = MaterialTheme.shapes.large,


                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Gray




                    )



                )

                {
                    Text(
                        text = selectedAgeGroup.value,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ageGroupOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            selectedAgeGroup.value = option
                            expanded.value = false
                            activeButton =true



                        },
                            modifier = Modifier.fillMaxWidth()



                        ) {
                            Text(
                                text = option,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }
            }*/




            }


        }


    }


}


@Composable
fun CustomComboBoxRTL(
    ages: List<String>, // Raw API data
    OnItemClicked: (String) -> Unit,
    //selectedSessionIndex: Int, // Currently selected session index


) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAge by remember { mutableStateOf("") }


    var boxWidth by remember { mutableStateOf(0) }

    // var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index


    // ✅ Filter only sessions from relatedMovies (first API call)
    //  Replace isSession with your session identifier

    // val  sessions=relatedMovies


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
              //  .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.ageSelectedButton, shape = MaterialTheme.shapes.medium)
                    .onGloballyPositioned { coordinates -> boxWidth = coordinates.size.width }

                   // .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.Red)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                )



                {



                        TextField(selectedAge,
                            onValueChange = {
                                selectedAge=it


                            },
                            placeholder = {Text("به عنوان مثال: ۱۸ سال به بالا")},
                            trailingIcon =
                            {
                                Icon(

                                    modifier = Modifier.clickable {
                                        expanded = !expanded

                                    }
                                        .size(32.dp)
                                    ,


                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown Icon",
                                    tint = Color.White,


                                    )
                            },
                            readOnly = true,


                            shape = MaterialTheme.shapes.medium,
                            //singleLine = true,
                          //  expanded =true












                        )



                        /*   Text(
                               text = if (selectedAge.isEmpty()) "انتخاب فصل" else selectedAge,
                               color = Color.White
                           )*/



                        /*Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            tint = Color.White
                        )
    */




















                }





            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { boxWidth.toDp() })
                    .background(MaterialTheme.colorScheme.ageSelectedButton
                        , shape = MaterialTheme.shapes.medium)
                    .clip(MaterialTheme.shapes.medium)
            )
            {
                ages.forEachIndexed { index, age ->
                    DropdownMenuItem(

                        content = {

                            androidx.compose.material3.Text(
                                //text = session .name.toString(),
                                text = age,
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                textAlign = TextAlign.End
                            )
                        },


                        onClick = {
                            selectedAge = age
                            //selectedSession = {}


                            expanded = false



                            OnItemClicked(age)

                            // session.id?.let { onSessionSelected(it) } // Send session ID to fetch episodes
                        },
                        modifier = Modifier.fillMaxWidth()
                          //  .background(Color.Red)
                    )
                }
            }
        }
    }
}






@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomComboBoxRTL2(
    ages: List<String>, // Raw API data
    OnItemClicked: (String) -> Unit,
    //selectedSessionIndex: Int, // Currently selected session index


)
{
    var expanded by remember { mutableStateOf(false) }
    var selectedAge by remember { mutableStateOf("") }




    // var selectedSessionIndex by remember { mutableStateOf(0) } // Tracks the selected session index


    // ✅ Filter only sessions from relatedMovies (first API call)
    //  Replace isSession with your session identifier

    // val  sessions=relatedMovies


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Column(
            modifier = Modifier.fillMaxWidth()
              //  .background(Color.Gray)
                .padding(horizontal = 8.dp)



        )
        {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded=!expanded},
                modifier =Modifier.fillMaxWidth()




            )
            {

                TextField(selectedAge,
                    onValueChange = {
                        selectedAge=it


                    },
                    placeholder = {Text("به عنوان مثال: ۱۸ سال به بالا")},
                    trailingIcon =
                    {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded=expanded)

                       /* Icon(

                            modifier = Modifier.clickable {
                                expanded = !expanded

                            }
                                .size(32.dp)
                            ,


                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            tint = Color.White,


                            )*/
                    },
                    readOnly = true,


                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()

                    //singleLine = true,
                    //  expanded =true












                )


                ExposedDropdownMenu(
                    expanded=expanded,
                    onDismissRequest = { expanded=false}



                ) {

                    ages.forEachIndexed { index, age ->
                        DropdownMenuItem(

                            content = {

                                androidx.compose.material3.Text(
                                    //text = session .name.toString(),
                                    text = age,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    textAlign = TextAlign.End
                                )
                            },


                            onClick = {
                                selectedAge = age
                                //selectedSession = {}


                                expanded = false



                                OnItemClicked(age)

                                // session.id?.let { onSessionSelected(it) } // Send session ID to fetch episodes
                            },
                            modifier = Modifier.fillMaxWidth()
                            //  .background(Color.Red)
                        )
                    }







                }








            }











        }


















    }
}































@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCombo(
    onItemClicked:(String)->Unit




) {

    var expanded by remember { mutableStateOf(true) }
    var slectedAge by remember { mutableStateOf("1234") }


    Column(modifier = Modifier.background(Color.Gray) ) {

        AnimatedVisibility(expanded) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                elevation = CardDefaults.cardElevation()


            )

            {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                        //.background(Color.Black, shape = RoundedCornerShape(50.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                )

                {
                    TextField(
                        value = slectedAge,
                        onValueChange = {
                            onItemClicked(it)

                            // onSearchChange(it)
                          //  expanded = it.isNotEmpty() // 🔴 Show suggestions when text is entered
                        },
                        placeholder = {
                           Text(
                                "جستجو...",
                                color = Color.Red
                            )
                        },


                        textStyle = TextStyle(


                            //  color = color


                        ),


                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .background(Color.DarkGray, shape = RoundedCornerShape(24.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Red
                            )
                        }
                    )

                    // 🔴 Filter Icon with Badge

                }

                Column(modifier = Modifier.fillMaxWidth()) {

                     SuggestionTextItem("t1") {
                       //  expanded=false
                         slectedAge=it


                     }
                     SuggestionTextItem("t2") {
                       //  expanded=false

                     }
                     SuggestionTextItem("t3") {

                        // expanded=false


                     }







                }









            }


        }

    }

}



@Composable
fun SuggestionTextItem(text: String, onClick: (String) -> Unit) {
    androidx.compose.material3.Text(
        text = text,
        modifier = Modifier
            .clickable(onClick = { onClick(text) })
            .padding(8.dp),
        //  .fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White


    )
}
