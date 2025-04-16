package com.smcdeveloper.nobinoapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentHistory
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

@Composable
fun PaymentHistoryPage(navController: NavHostController,
                  profileViewModel: ProfileViewModel = hiltViewModel(),
                  dataStore: DataStoreViewModel = hiltViewModel()



)
{
    LaunchedEffect(Unit) {
        Log.d("paymentData" , "payments Launch:")
        profileViewModel.getPaymentHistory()
    }

   // profileViewModel.getPaymentHistory()


    val paymentData = profileViewModel.paymentHistoryData.collectAsLazyPagingItems()

     val state = paymentData.loadState

    when(state.refresh)
    {
         is LoadState.Loading ->{

             Log.d("paymentData" , "payments loading:")
         }
         is LoadState.Error -> {
             Log.d("paymentData" , "payments Error ")
         }
        else ->
        {
          Log.d("paymentData" , "payments are:" + paymentData.toString())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            )

            {
                Text(
                    text = "سوابق پرداخت",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn() {

                    items(
                        count = paymentData.itemCount,
                        key =paymentData.itemKey{ payment-> payment.id!! }     ,


                        )
                    { index->

                        PaymentHistory(paymentData[index]!!)







                    }







                }













                //  PaymentHistoryTable()
            }























        }
    }



















}



@Composable
fun PaymentHistory(profileViewModel: ProfileViewModel,dataStore: DataStoreViewModel)
{










}

@Composable
fun PaymentHistory(payment:PaymentHistory.PaymentHistoryData.Payment)
{
    Log.d("payment",payment.id.toString())
    Log.d("payment",payment.price.toString())
    Log.d("payment",payment.description.toString())



    Row(modifier = Modifier.fillMaxWidth()
        .background(Color.Gray)
        .height(80.dp)
        .padding(horizontal = 8.dp)

        ,
       // horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically





    )
    {


        Box(
            modifier = Modifier.background(Color.Blue)
                .border(width = 1.dp, color = Color.Black)
                // .fillMaxWidth()
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center


        )


        {
            Text(""+payment.description.let {
              it

            },
                //modifier = Modifier.border(width = 3.dp, color = Color.Gray)


                )


        }

        Box(
            modifier = Modifier.background(Color.Blue)
                .border(width = 1.dp, color = Color.Black)
                // .fillMaxWidth()
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center


        )


        {
            Text(""+ payment.price.let {
                it.toString()

            }
                //modifier = Modifier.border(width = 3.dp, color = Color.Gray)


            )


        }




                 /* .clip(
                      RoundedCornerShape(10.dp)

                  */





          /*Text(""+ payment.price.let {
              it.toString()

          },
                  modifier = Modifier.background(Color.Blue)
                      .fillMaxSize()
                      .weight(1f)

              *//*.clip(
                  RoundedCornerShape(10.dp)
          )*//*
                      .align(Alignment.CenterVertically)
                      .border(width = 2.dp, color = Color.Green)

              ,
              textAlign = TextAlign.Center



          )*/
          Text("1234",
              modifier = Modifier.background(Color.Blue)
                  .fillMaxSize()
                  .weight(1f)
                  .clip(
                      RoundedCornerShape(10.dp)



          ))
          Text("1234",
              modifier = Modifier.background(Color.Blue)
                  .fillMaxSize()
                  .weight(1f)
                  .clip(
                      RoundedCornerShape(10.dp)



              ))




      }

    HorizontalDivider(
        thickness = 5.dp,
        color = Color.Red


    )





}



@Composable
fun PaymentHistoryTable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Table Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("نوع اشتراک", style = headerStyle())
            Text("تاریخ شروع", style = headerStyle())
            Text("مبلغ", style = headerStyle())
            Text("وضعیت", style = headerStyle())
        }

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        // Table Row - Example Data
        PaymentHistoryRow("طلایی", "1401/01/01", "۳۹,۰۰۰ تومان", "فعال")
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        PaymentHistoryRow("نقره‌ای", "1400/12/01", "۲۹,۰۰۰ تومان", "منقضی شده")
    }
}

@Composable
fun PaymentHistoryRow(type: String, date: String, amount: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(type, style = rowStyle())
        Text(date, style = rowStyle())
        Text(amount, style = rowStyle())
        Text(
            status,
            style = rowStyle(),
            color = if (status == "فعال") Color.Green else Color.Red
        )
    }
}

@Composable
fun headerStyle(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun rowStyle(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
}
