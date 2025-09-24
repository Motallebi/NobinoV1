package com.smcdeveloper.nobinoapp.ui.screens.series

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMarKRequest
import com.smcdeveloper.nobinoapp.navigation.Screen



import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel


@Composable
fun FloatingActionButtons(navController: NavHostController, viewModel: ProductDetailsViewModel, productId: Int, bookmark:Boolean, isUserLogedIn: Boolean) {




    // val bookmarkState = remember {  mutableStateOf(false)}
    val bookmarkStat by viewModel.isBookmarked.collectAsState()

    Log.d("bookmark ","bookmark state $bookmarkStat")














    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )

    {
        Row {
            TransparentBookmarkIconButton(
                isBookmark =bookmarkStat,
                icon =
                if(bookmarkStat) {

                    painterResource(R.drawable.bookmark)





                }

                else
                    painterResource(R.drawable.bookmark)

                ,
                contentDescription = "Bookmark",
                onClick = {




                    Log.d("stat", "Bookmark:${bookmark}")

                    if (bookmarkStat) {


                        Log.d("stat", "removing bookmark")
                        viewModel.removeBookMark(
                            auth = "Bearer $USER_TOKEN",

                            bookmark = BookMarKRequest(


                                productId = productId,
                                type = "BOOKMARK"


                            )


                        )
                    }

                    else {
                        if (isUserLogedIn) {

                            viewModel.saveBookMark(
                                auth = "Bearer $USER_TOKEN",

                                bookmark = BookMarKRequest(


                                    productId = productId,
                                    type = "BOOKMARK"


                                )


                            )


                        }

                        else{

                            navController.navigate(Screen.SignUp.route)







                        }


                    }


                    /* Handle bookmark action */





                }
            )



            // Share button
            TransparentIconButton(
                icon = painterResource(R.drawable.share),
                contentDescription = "Share",
                onClick = { /* Handle share action */ }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Bookmark button

        }




        // Back button
        TransparentIconButton(
            icon = painterResource(R.drawable.arrow_left),
            contentDescription = "Back",
            onClick = { navController.navigateUp() },

            )


    }
}




@Composable
fun TransparentBookmarkIconButton(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    isBookmark:Boolean =false
)

{
    var isSelected by remember { mutableStateOf(false) }




    Log.d("stat", "Loading ... Bookmark:$isBookmark Selected $isSelected")

    IconButton(
        onClick = {
            isSelected = !isSelected // ✅ Toggle selection state


            onClick()
            Log.d("stat", "Bookmark:$isBookmark Selected $isSelected")
        },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.6f)) // ✅ Semi-transparent background
    )
    {
        Icon(




            painter = icon,
            contentDescription = contentDescription,
            tint = if (isBookmark) Color.Red else Color.White, // ✅ Change icon color when selected
            modifier = Modifier.size(24.dp)
        )
    }
}




@Composable
fun TransparentIconButton(
    icon:Painter ,
    contentDescription: String,
    onClick: () -> Unit,

    )
{
    var isSelected by remember { mutableStateOf(false) }



    IconButton(
        onClick = {
            isSelected = !isSelected // ✅ Toggle selection state
            onClick()

        },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.6f)) // ✅ Semi-transparent background
    )
    {
        Icon(




            painter = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) Color.Red else Color.White, // ✅ Change icon color when selected
            modifier = Modifier.size(24.dp)
        )
    }
}




@Composable
fun CategoryChipsRow(title1:String="",
                     title2:String="",
                     onTag1Click: () -> Unit,
                     onTag2Click: () -> Unit

) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryChip(title1)
        {
            onTag1Click()
        }// Crime
        CategoryChip(title2)
        {
            onTag2Click()

        }// Action
    }
}



@Composable
fun CategoryChip(text: String="",
                 onTagClick: () -> Unit


) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp)) // Rounded shape
            .background(Color.DarkGray) // Background color
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onTagClick() }
    )
    {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
