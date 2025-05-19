package com.smcdeveloper.nobinoapp.ui.screens.search

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.animatedlist.AnimatedInfiniteLazyColumn
import com.smarttoolfactory.animatedlist.model.AnimationProgress
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max


@Composable
fun YearSelectionSheet(
    selectedFromYear: Int?,
    selectedToYear: Int?,
    onYearSelected: (Int, Boolean) -> Unit,
   // onYearSelected: (String) -> Unit,
    onClose: () -> Unit
) {
    val years = (1990..2025).toList()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔴 Header with Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "انتخاب سال", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 "From" and "To" Year Fields INSIDE Year Selection Sheet
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            YearInputField(label = "از", selectedYear = selectedFromYear)
            Text(
                "تا",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            YearInputField(label = "تا", selectedYear = selectedToYear)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔴 Animated Year Selection List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp), // Controls visible area
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 32.dp)
            ) {
                itemsIndexed(years) { index, year ->
                    YearItem(
                        year = year,
                        isSelected = year == selectedFromYear || year == selectedToYear,
                        index = index,
                        listState = listState,
                        onYearSelected = { isFromYear -> onYearSelected(year, isFromYear) }
                    )
                }
            }
        }
    }

    // 🔴 Scroll to selected year when opened
    LaunchedEffect(Unit) {
        val defaultYear = selectedFromYear ?: 2023
        val index = years.indexOf(defaultYear)
        if (index != -1) {
            listState.animateScrollToItem(index) // 🔥 Smooth animation to selected year
        }
    }
}


@Composable
fun YearItem(
    year: Int,
    isSelected: Boolean,
    index: Int,
    listState: LazyListState,
    onYearSelected: (Boolean) -> Unit
)


{
    val centerIndex = listState.firstVisibleItemIndex + 2 // Center the middle item
    val distance = abs(index - centerIndex)

    // 🔴 Scaling Effect
    val scale by animateFloatAsState(
        targetValue = max(0.8f, 1.2f - (distance * 0.1f)),
        animationSpec = tween(durationMillis = 300),
        label = "Year Scaling"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onYearSelected(index < listState.layoutInfo.totalItemsCount / 2) }
            .background(
                if (isSelected) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = year.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (16.sp * scale)),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun YearInputField(label: String, selectedYear: Int?) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // ✅ Ensures full width inside LazyColumn
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedYear?.toString() ?: label, // ✅ Show label if no year is selected
            style = MaterialTheme.typography.bodyLarge
        )
    }
}




///////////////////////////////////////////////////////////////////////////////



@Composable
fun YearSelection(
    modifier: Modifier,
    onYearClick:(String)->Unit,
    firstYear:Int,
    secondYear:Int

   // isVisible: Boolean





)
{

    Text(firstYear.toString(),
        modifier.clickable {


            onYearClick("0")




        }

    )

    Text(secondYear.toString(),
        modifier.clickable {


            onYearClick("0")



        }

    )







}




@Composable
fun YearSelectionSheet2(
    selectedFromYear: Int?=1990,
    selectedToYear: Int?=2025,


   // onYearSelected: (Int, Boolean) -> Unit,
    onYearSelected: (String) -> Unit,
    onClose: () -> Unit,
    data:List<Int>,
    isVisible:Boolean,
    onYearClick:(String)->Unit,
    selectedYears :List<String>


)





{

    var isTimpeickerVisible by remember { mutableStateOf(false) }




    Row(modifier =
        Modifier.fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween


    )
    {


        YearSelection(
            //isVisible = isVisible,
            modifier = Modifier,





            onYearClick = onYearClick,
            firstYear =  selectedYears[0].toInt() ?: 0,
            secondYear = selectedYears[1].toInt()?: 0








        )




        Text(
            selectedYears[0].toString(),
            modifier = Modifier.clickable {


            }





        )
        Text(selectedToYear.toString(),

        modifier = Modifier.clickable {


        }

        )




    }


    Log.d("Year","is visible $isVisible" )

  if(isVisible) {

      Box()
      {
          val scope = rememberCoroutineScope()
          val anp1 = AnimationProgress(
              scale = 5f,
              color = Color.Blue,
              itemOffset = 2,
              itemFraction = 3f,
              globalItemIndex = 3,
              itemIndex = 0,
              distanceToSelector = 5f
          )



          AnimatedInfiniteLazyColumn(

              items = data,
              visibleItemCount = 7,
              inactiveColor = Color.Black,
              activeColor = Color.Red,
              selectorIndex = 3,
              inactiveItemPercent = 40,
              itemScaleRange = 1,
              itemContent = { anp, index, item, height, lazyListState ->


                  val color = anp.color
                  val scale = anp.scale
                  // val color = animationProgress.color
                  //
                  // val scale = animationProgress.scale

                  Box(
                      modifier = Modifier
                          .scale(scale)
                          .background(color, RoundedCornerShape(20.dp))
                          .size(width = 400.dp, height = 80.dp)
                          .clickable(
                              interactionSource = remember {
                                  MutableInteractionSource()

                              },
                              indication = null
                          ) {
                              onYearSelected(

                                  data[index].toString()


                              )

                              Log.d("anim", "animated: $item")
                              scope.launch {
                                  lazyListState.animateScrollBy(anp.distanceToSelector)

                              }
                          },


                      contentAlignment = Alignment.Center
                  ) {

                      Text(
                          data[index].toString(),
                          color = Color.White,
                          fontSize = 50.sp,
                          fontWeight = FontWeight.Bold
                      )


                  }
              }
          )


      }

  }























}













