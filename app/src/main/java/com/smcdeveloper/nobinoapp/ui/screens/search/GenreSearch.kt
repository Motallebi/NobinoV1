package com.smcdeveloper.nobinoapp.ui.screens.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.data.model.search.GenreInfo
import com.smcdeveloper.nobinoapp.ui.component.ParentFilterBottomSheet

import com.smcdeveloper.nobinoapp.ui.screens.demo.SelectionCheckboxItem


@Composable
fun GenreSelectionSheet(

    items: List<GenreInfo>,
    selectedItemIds: MutableSet<String>,
    selectedCheckBoxes:MutableMap<String,Boolean>,

    onItemSelected: (GenreInfo, Boolean) -> Unit,
    onClose: () -> Unit,
    onClearAll:Boolean,

    isAllClear:Boolean,
    onClear:()->Unit,





)

{


    items.forEach {

        Log.d("Filter1", "check Box stattus  ${it.name} is ${selectedCheckBoxes[it.translatedName]}" )



    }













    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) }









    ParentFilterBottomSheet (

       // isClear = onClearAll(),
        isVisible = true,
        onDismiss = onClose,
        //onCheckBox = {},
        modifier = Modifier) { // 🔴 Use bottom sheet
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(Color.Blue)
               // .padding(16.dp)
        )

        {


            var isCheckBoxCleared by remember {mutableStateOf( isAllClear )  }



            // 🔴 Header with Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text(text = title, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = onClose) { // 🔴 Close bottom sheet
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔴 Search Bar with Debouncing
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("جستجو...") }, // "Search..."
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.background
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🔴 List of Selectable Items (Country Names, but track by ID)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredItems.chunked(2)) { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {




                        rowItems.forEach { item ->

                          /*  if(isAllClear)
                            {

                                SelectionCheckboxItem(
                                    text = item.name,
                                    isSelected = false,
                                    onSelected = {
                                            isSelected ->

                                        onClear()




                                        onItemSelected(
                                            item,
                                            isSelected
                                        )



                                    },
                                    modifier = Modifier.weight(1f),





                                    )



                            }*/












                            SelectionCheckboxItem(
                                text = item.name, // 🔴 Show country name
                                isSelected =
                              //  if((selectedCheckBoxes[item.id]==false)) false  else item.id.toString() in selectedItemIds, // 🔴 Check by ID
                              if(item.id.toString() in selectedItemIds)  true else selectedCheckBoxes[item.translatedName]!!,


                                onSelected = { isSelected ->


                                    onClear()




                                    onItemSelected(
                                        item,
                                        isSelected
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                //onClearAll = onClearAll

                            )
                           // isCheckBoxCleared=false















                        }
                        isCheckBoxCleared=false

                    }
                }
            }
        }
    }


}
