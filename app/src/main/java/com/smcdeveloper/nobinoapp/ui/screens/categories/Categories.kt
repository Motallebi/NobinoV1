package com.smcdeveloper.nobinoapp.ui.screens.categories

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage

import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.viewmodel.ProductCategoriesViewModel


@Composable
fun Categories(navController: NavHostController) {

    Log.d(NOBINO_LOG_TAG,"we arte in Categories Page")
    CategoryScreen(onCategoryClick = {})


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: ProductCategoriesViewModel = hiltViewModel(),
    onCategoryClick: (ProductCategories.ProductCategoryData) -> Unit
)
{
    LaunchedEffect(Unit){
        viewModel.getProductCategories()

    }

    val categoriesState by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
   // var categories: List<ProductCategories.ProductCategoryData>

    // Scaffold with a loading indicator and the list of categories
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (categoriesState) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is NetworkResult.Success -> {
                    val categories = (categoriesState as NetworkResult.Success<List<ProductCategories.ProductCategoryData>>).data.orEmpty()
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), // 2 columns
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    )

                    {
                        items(categories)

                        { category->
                            CategoryItem(category = category, onClick = {
                                onCategoryClick(category)
                                    Log.d("category","categoryname is....."+category.name)


                            })


                        }



                    }
                }
                is NetworkResult.Error -> {
                    Text(
                        text = "Failed to load categories",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun CategoryItem(
    category: ProductCategories.ProductCategoryData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            AsyncImage(
                model = "https://vod.nobino.ir/vod/"+category.image, // Load image from URL
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Name
            category.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



