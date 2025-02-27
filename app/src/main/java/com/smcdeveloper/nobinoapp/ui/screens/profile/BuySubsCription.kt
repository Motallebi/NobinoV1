package com.smcdeveloper.nobinoapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentRequest
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SubsCriptionViewModel


@Composable
fun SubscriptionConfirmationPage(
    navController: NavHostController,
    viewModel: SubsCriptionViewModel = hiltViewModel(),
    planid: String,
    profileViewModel: ProfileViewModel= hiltViewModel()


)

{


    val currentPlan by viewModel.currentPlan.collectAsState()

    LaunchedEffect(Unit) {

        Log.d("plan","by plan screen..")
        viewModel.fetchCurrentSubsCriptionPlan(planid)



    }


    when (currentPlan) {
        is NetworkResult.Error -> {}

        is NetworkResult.Loading -> {}
        is NetworkResult.Success -> {

            Log.d("plan", "current plan is..:" + currentPlan.data?.planData?.name.toString())
           // currentPlan.data.planData.name.toString()
            Text("SUB>>>>>>")

            SubscriptionDetailsCard(
                imagePath = IMAGE_BASE_URL + currentPlan.data?.planData?.logo.toString(),
                subscriptionType = currentPlan.data?.planData?.name.toString(),
                price = "1000 هزار تومان",
                vat = "۹۰ هزار تومان",
                viewModel = profileViewModel,
                navController=navController

            )


         /*   SubscriptionDetailsCard(
                imagePath = IMAGE_BASE_URL + "POSTER/2024-9/1726055836659_IMAGES_POSTER.png",
                subscriptionType = "طلایی",
                price = "۹۰۰ هزار تومان",
                vat = "۹۰ هزار تومان"
            )

*/









        }
    }


}











@Composable
fun SubscriptionConfirmationPage1(navController: NavHostController) {
    val discountCode = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "خرید اشتراک",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon and Subscription Type
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_agenda),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = Color(0xFFFFC107)
                )
                Text(
                    text = "طلایی",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Price Details
                Text(
                    text = "قیمت: ۹۰,۰۰۰ تومان",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = "مالیات بر ارزش افزوده: ۹,۰۰۰ تومان",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )

                // Discount Code Field
                Column {
                    Text(
                        text = "ثبت کد تخفیف:",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                    TextField(
                        value = discountCode.value,
                        onValueChange = { discountCode.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(4.dp),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle Confirm */ },
                colors = ButtonDefaults.buttonColors(Color.Green),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "تایید خرید",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Button(
                onClick = { /* Handle Cancel */ },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "انصراف",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}


@Composable
fun SubscriptionDetailsCard(
    imagePath: String,
    subscriptionType: String,
    price: String,
    vat: String,
    viewModel: ProfileViewModel,
    navController: NavHostController
)

{
    var discountCode by remember { mutableStateOf(TextFieldValue("")) }
    val paymentState by viewModel.paymentResponse.collectAsState()

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Subscription Icon
            AsyncImage(
                model = imagePath,
                contentDescription = "Subscription Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp)
            )

            // Subscription Details
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                SubscriptionDetailItem(label = "نوع اشتراک:", value = subscriptionType)
                SubscriptionDetailItem(label = "قیمت:", value = price)
                SubscriptionDetailItem(label = "مالیات بر ارزش افزوده:", value = vat)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Discount Code Input with Button Inside
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = discountCode,
                    onValueChange = { discountCode = it },
                    placeholder = {
                        Text(
                            text = "ثبت کد تخفیف:",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                )

                Button(
                    onClick = { /* Apply Discount */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (discountCode.text.isNotEmpty()) Color.Green else Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(4.dp)
                        .height(50.dp)
                ) {
                    Text(text = "اعمال", color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons (Purchase & Cancel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Cancel Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "انصراف", color = Color.White, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))



                Button(
                    onClick = {

                        navController.navigate(Screen.PaymentResult.withArgs(
                            31,""



                        ))



                        /* Purchase Action */





                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "خرید اشتراک", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

// Composable for Subscription Details
@Composable
fun SubscriptionDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
