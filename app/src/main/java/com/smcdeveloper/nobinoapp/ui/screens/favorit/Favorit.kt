package com.smcdeveloper.nobinoapp.ui.screens.favorit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.Image
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.FavoritesViewModel

@Composable
fun FavoriteScreen(navController: NavHostController,
viewModel: FavoritesViewModel= hiltViewModel()
)
{

    FavoriteListScreen(viewModel)



}

@Composable
fun ShowData()
{












}






@Composable
fun FavoriteListScreen(viewModel: FavoritesViewModel) {
  //  val movies by viewModel.movies.collectAsState()

    val movies = viewModel.getUserFavorites(pageSize = 20, pageNumber = 1).collectAsLazyPagingItems()








    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp)) // ✅ Light border
            .background(Color.Black) // ✅ Dark background
            .padding(16.dp) // ✅ Inner padding
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp) // ✅ Space between items
        ) {
            items(movies.itemCount) { index -> // ✅ Show 3 items at a time
                MovieItem(movies[index]!!)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Favorite.FavoriteData.Item) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray, RoundedCornerShape(8.dp)) // ✅ Card-like look
            .padding(12.dp)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL +movie.images?.get(0)?.src,
            contentDescription = "Movie Thumbnail",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.name.toString(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
          //  Text(text = "قسمت ${movie.episode}", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.shortDescription.toString(),
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Play movie */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "پخش فیلم", color = Color.White)
            }
        }
    }
}





