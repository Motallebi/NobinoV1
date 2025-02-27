package com.smcdeveloper.nobinoapp.ui.screens.profile

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
    discountCode:String





)







{


    LaunchedEffect(Unit) {

        val discount =PaymentRequest.Discount("")
        val plan =PaymentRequest.Plan(31)
        val paymentRequest =PaymentRequest(plan = plan, discount = discount)

        viewModel.postPayment(paymentRequest = paymentRequest)






    }


    val paymentResponse by viewModel.paymentResponse.collectAsState()



          when(paymentResponse)
          {
              is NetworkResult.Loading->
          {
              Log.d("payment","payment is loading")




          }

              is NetworkResult.Error->
              {

                  Log.d("payment","payment is Error")



              }

              is NetworkResult.Success ->
              {
                 // paymentResponse.data.paymentResponse.payment

                  Log.d("payment","payment is Success")
                  Log.d("payment","payment response ${paymentResponse.data?.paymentResponse?.payment?.redirect}")
                  Log.d("payment","payment response ${paymentResponse.data?.paymentResponse?.payment.toString()}")





              }







          }











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
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Success Message
        Text(
            text = "پرداخت با موفقیت انجام شد",
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
