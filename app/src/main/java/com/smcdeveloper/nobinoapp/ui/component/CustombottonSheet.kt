package com.smcdeveloper.nobinoapp.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.disabledButtonColor
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel

@Composable
fun ParentFilterBottomSheet(


    isVisible: Boolean,
    onDismiss: () -> Unit,
    sheetHeight: Dp = 400.dp, // Default height
    navigationBarHeight: Dp = 80.dp, // Adjust based on bottom navigation bar height
    modifier: Modifier,

    isClear:Boolean=false,
   // onCheckBox:()->Unit,




    content: @Composable ColumnScope.() -> Unit,



)
{
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)) // 🔴 Dims background when sheet is open
                .clickable(onClick = onDismiss), // 🔴 Click outside to dismiss
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .pointerInput(Unit) { // 🔴 Prevent clicks from passing through the bottom sheet
                        detectTapGestures { /* Consume Clicks Inside the Sheet */ }
                    }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                         .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {






                    content() // 🔴 Content inside the bottom sheet
                }
            }
        }
    }
}










@Composable
fun MainFilterScreen(


    isVisible: Boolean,
    onDismiss: () -> Unit,
    sheetHeight: Dp = 400.dp, // Default height
    navigationBarHeight: Dp = 80.dp, // Adjust based on bottom navigation bar height
    modifier: Modifier,




    content: @Composable ColumnScope.() -> Unit,



    )
{
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)) // 🔴 Dims background when sheet is open
                .clickable(onClick = onDismiss), // 🔴 Click outside to dismiss
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .pointerInput(Unit) { // 🔴 Prevent clicks from passing through the bottom sheet
                        detectTapGestures { /* Consume Clicks Inside the Sheet */ }
                    }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {






                    content() // 🔴 Content inside the bottom sheet
                }
            }
        }
    }
}



























@Composable
fun FilterBottomSheet(

    onCloseParanetSheetClick:()->Unit,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    sheetHeight: Dp = 400.dp, // Default height
    navigationBarHeight: Dp = 80.dp, // Adjust based on bottom navigation bar height
    modifier: Modifier,
    onRemoveAllClick:()->Unit,
    title:String,
    showIcon:Boolean=true,
    viewmodel:FilterViewModel= hiltViewModel(),

    //isParentVisible:Boolean,
    // selectedFilterType:FilterType?,



    content: @Composable ColumnScope.() -> Unit,




    )

{





    var isIconVisble by remember { mutableStateOf( showIcon)  }
    val isIconVisble2 =viewmodel.isShowClearIconVisible.collectAsState()
    Log.d("Filter","is icon visible $showIcon ---isIconVisble    $isIconVisble")

    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f)) // 🔴 Dims background when sheet is open
                .clickable(onClick = onDismiss), // 🔴 Click outside to dismiss
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .pointerInput(Unit) { // 🔴 Prevent clicks from passing through the bottom sheet
                        detectTapGestures { /* Consume Clicks Inside the Sheet */ }
                    }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Row(Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround





                    )


                    {
                        IconButton(onClick ={

                            onCloseParanetSheetClick()
                            Log.d("sheet","Close click....")




                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }


                        Text(
                            text = title ?: "",
                            style = MaterialTheme.typography.nobinoLarge,

                            )

                        if(isIconVisble2.value) {

                            IconButton(

                                onClick = {


                                    onRemoveAllClick()


                                },

                                )
                            {
                                Icon(
                                    painterResource(R.drawable.trash), contentDescription = "Close",
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.disabledButtonColor


                                )

                            }

                        }






                    }






                    content() // 🔴 Content inside the bottom sheet
                }
            }
        }
    }
}
