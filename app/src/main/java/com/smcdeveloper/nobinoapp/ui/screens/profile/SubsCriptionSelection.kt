import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.Image
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.SubsCriptionViewModel

@Composable
fun SubscriptionSelectionPage(navController: NavHostController,
      viewModel: SubsCriptionViewModel= hiltViewModel()



)

{

   ShowPlans(viewModel,navController)
















}

@Composable
fun SubscriptionCard(planName: String, price: String, originalPrice: String?, duration: String, discount: String?, icon: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation =CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Discount Badge
            if (discount != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.Red, shape = RoundedCornerShape(bottomEnd = 8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = discount,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = planName,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )

                        Text(
                            text = duration,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    if (originalPrice != null) {
                        Text(
                            text = originalPrice,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray,
                                textAlign = TextAlign.End,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                            )
                        )
                    }

                    Text(
                        text = price,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ShowPlans(viewModel: SubsCriptionViewModel,
       navController: NavHostController

)
{

    val plans by viewModel.plans.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.fetchCurrentSubsCription()




    }


    when(plans)
    {
        is NetworkResult.Loading->
        {

        }

        is NetworkResult.Error -> {





        }
        is NetworkResult.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )


            {
                Text(
                    text = "خرید اشتراک",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally))

            }






























            LazyColumn() {


                items(plans.data!!.subData!!)
                { plan->



                    SubscriptionCard3(

                        title =plan?.name.toString(),
                        duration =  plan?.description.toString(),
                        originalPrice =  plan?.price?.totalAmount, // Original price (optional)
                        discountedPrice = plan?.price?.discountAmount, // Discounted price (mandatory)
                        imagePath = IMAGE_BASE_URL+plan?.logo,
                        discountPercentage = 80, // Show






                    )
                    {
                        navController.navigate(Screen.SubscriptionConfirmation.withArgs(plan?.id.toString()))


                    }











                }

               // item{ BronzeSubscriptionCard()}
















            }















        }
    }















}





@Composable
fun SubscriptionSelectionPage1(navController: NavHostController



) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        SubscriptionCard(
            planName = "اشتراک برنزی",
            price = "۱۵,۰۰۰ تومان",
            originalPrice = "۳۰,۰۰۰ تومان",
            duration = "یک ماه",
            discount = "۷۵%",
            icon = android.R.drawable.ic_menu_compass
        )
        SubscriptionCard(
            planName = "اشتراک نقره‌ای",
            price = "۳۰,۰۰۰ تومان",
            originalPrice = null,
            duration = "سه ماه",
            discount = null,
            icon = android.R.drawable.ic_menu_directions
        )
        SubscriptionCard(
            planName = "اشتراک طلایی",
            price = "۹۰,۰۰۰ تومان",
            originalPrice = "۱۵۰,۰۰۰ تومان",
            duration = "شش ماه",
            discount = "۴۰%",
            icon = android.R.drawable.ic_menu_agenda
        )
    }
}




@Composable
fun BronzeSubscriptionCard() {
    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Discount Badge
        Box(
            modifier = Modifier
                .background(Color.Red, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "٪۵۰", // 50% Discount
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Main Card
        Card(
            shape = RoundedCornerShape(8.dp),
           colors = CardDefaults.cardColors(Color.Black),
            elevation =CardDefaults.cardElevation( 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "اشتراک برنزی",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "یک ماهه",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "۳۰,۰۰۰ تومان",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )

                    Text(
                        text = "۱۵,۰۰۰ تومان",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun SubscriptionCard2(
    title: String,
    duration: String,
    originalPrice: Int?,
    discountedPrice: Int,
    imagePath: String, // URL or local image path
    discountPercentage: Int? // Show badge only if not null
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth() // Ensures proper positioning
    ) {
        // Use Badge only if there's a discount
        if (discountPercentage != null) {
            BadgedBox(
                badge = {
                    Badge(
                        containerColor = Color.Red, // Red badge background
                        contentColor = Color.White // White text
                    )
                    {
                        Text(
                            text = "٪$discountPercentage",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(painterResource(R.drawable.mobile_icon),"")


                    }
                },
                modifier = Modifier.align(Alignment.TopEnd) // Correct badge positioning
            )
            {

                    Text("90909090909")

            }
        }

        // Main Card inside Box (so badge overlays correctly)
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color.Black),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon + Subscription details
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = "Subscription Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = duration,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Pricing details
                Column(horizontalAlignment = Alignment.End) {
                    originalPrice?.let {
                        Text(
                            text = "$it تومان",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    Text(
                        text = "$discountedPrice تومان",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun SubscriptionCard1(
    title: String,
    duration: String,
    originalPrice: Int?,
    discountedPrice: Int,
    imagePath: String,
    discountPercentage: Int? // Show badge only if not null
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Show Badge if Discount Exists
        if (discountPercentage != null) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 6.dp, top = 6.dp), // Positioning
                containerColor = Color.Red // Red background like your image
            ) {
                Text(
                    text = "٪$discountPercentage", // Show 50% like your image
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Main Card
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Image + Subscription Details
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = "Subscription Icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = duration,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Pricing Details
                Column(horizontalAlignment = Alignment.End) {
                    originalPrice?.let {
                        Text(
                            text = "$it تومان",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    Text(
                        text = "$discountedPrice تومان",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}




@Composable
fun SubscriptionCard3(
    title: String,
    duration: String,
    originalPrice: Int?,
    discountedPrice: Int?,
    imagePath: String,
    discountPercentage: Int? ,// Show badge only if not null
    onBadgeClick: ()->Unit






) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Wrap the card inside a BadgedBox to attach the badge correctly
        BadgedBox(
            badge = {
                if (discountPercentage != null) {
                    Badge(
                        modifier = Modifier
                            .background(Color.Red, shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = "٪$discountPercentage",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            // Main Card
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        onBadgeClick()

                    }

            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Image + Subscription Details
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = imagePath,
                            contentDescription = "Subscription Icon",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = duration,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Pricing Details
                    Column(horizontalAlignment = Alignment.End) {
                        originalPrice?.let {
                            Text(
                                text = "$it تومان",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }

                        Text(
                            text = "$discountedPrice تومان",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
