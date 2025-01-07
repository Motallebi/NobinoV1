import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SubscriptionSelectionPage() {
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
fun SubscriptionCard(planName: String, price: String, originalPrice: String?, duration: String, discount: String?, icon: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
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
