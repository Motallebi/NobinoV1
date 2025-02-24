package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

@Composable
fun PaymentHistoryPage(navController: NavHostController,
                  profileViewModel: ProfileViewModel = hiltViewModel(),
                  dataStore: DataStoreViewModel = hiltViewModel()



) {











    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
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

        PaymentHistoryTable()
    }
}



@Composable
fun PaymentHistory(profileViewModel: ProfileViewModel,dataStore: DataStoreViewModel)
{










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
