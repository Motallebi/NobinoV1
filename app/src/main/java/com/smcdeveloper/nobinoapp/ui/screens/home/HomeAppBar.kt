package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobinoTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("My App") },
        actions = {
            AppBarItem("Movies") { navController.navigate(Screen.Movies.route) }
            AppBarItem("Series") { navController.navigate(Screen.Series.route) }
            AppBarItem("Kids") { navController.navigate(Screen.Kids.route) }
            AppBarItem("Learn") { navController.navigate(Screen.Profile.route) }
            AppBarItem("More") { navController.navigate(Screen.Search.route) }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Green




        )
    )

}


@Composable
fun AppBarItem(label: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(label, color = MaterialTheme.colorScheme.onBackground)
    }
}


// Placeholder screens
@Composable
fun MoviesScreen() {
    Text("Movies Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun SeriesScreen() {
    Text("Series Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun KidsScreen() {
    Text("Kids Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun LearnScreen() {
    Text("Learn Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun MoreScreen() {
    Text("More Screen", modifier = Modifier.fillMaxSize())
}