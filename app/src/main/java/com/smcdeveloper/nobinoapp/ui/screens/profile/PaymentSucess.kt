package com.smcdeveloper.nobinoapp.ui.screens.profile

import android.content.Context
import android.content.Intent

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentRequest
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentResponse
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

@Composable
fun PaymentResultScreen(
    viewModel: ProfileViewModel= hiltViewModel(),
    planId:Int,
    discountCode:String,
    id:String,
    result:Boolean?= null







)







{

    val context = LocalContext.current


    LaunchedEffect(Unit) {


        Log.d("payment", "payment with $id $planId $discountCode"    )

        val discount =PaymentRequest.Discount("")
        val plan =PaymentRequest.Plan(planId)
        val paymentRequest =PaymentRequest(plan = plan, discount = discount)

        viewModel.postPayment(paymentRequest = paymentRequest)






    }


    val paymentResponse: NetworkResult<PaymentResponse> by viewModel.paymentResponse.collectAsState()



          when(paymentResponse)
          {
              is NetworkResult.Loading->
          {
              Log.d("payment","payment is loading")




          }

              is NetworkResult.Error->
              {

                  Log.d("payment","payment is Error")
                  Log.d("payment","error code is ${paymentResponse.message}")
                 // Log.d("payment","error code is ${paymentResponse.data.
                  ShowPaymentResultDialog("404")









              }

              is NetworkResult.Success ->
              {
                 // paymentResponse.data.paymentResponse.payment

                  Log.d("payment","payment is Success")
                  Log.d("payment","payment response ${paymentResponse.data?.paymentResponse?.payment?.redirect}")
                  Log.d("payment","payment response ${paymentResponse.data?.paymentResponse?.payment.toString()}")
                  val redirect=paymentResponse.data?.paymentResponse?.payment?.redirect
                //  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirect))
                  context.startActivity(intent)


                  if(result != null)
                  ShowPaymentResultDialog("1")







              }







          }











}

@Composable
fun ShowPaymentResultDialog(paymentId:String)
{



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Success Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Green, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.checkbox_on_background),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = if(paymentId=="1") Color.White else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Success Message
        Text(
            text =
            if(paymentId == "1")
                "پرداخت با موفقیت انجام شد"
            else if(paymentId == "400") "پرداخت با موفقیت انجام نشد"
               else   "کد اشتباه است",

            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "کاربر گرامی، اشتراک ۶ ماهه نوبیتو برای شما فعال شد.",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Button
        Button(
            onClick = { /* Handle navigation */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "بازگشت به خانه",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }












}