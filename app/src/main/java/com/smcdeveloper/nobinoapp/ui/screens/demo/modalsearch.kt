package com.smcdeveloper.nobinoapp.ui.screens.demo


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel







@Composable
fun DemoSearchWithModal(viewModel: FilterViewModel= hiltViewModel(),
                        navHostController: NavHostController
)
{
    //SearchScreen(viewModel,navHostController)
    DemoModalSearch(viewModel, navHostController)
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoModalSearch(
    viewModel: FilterViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    var showMainModal by remember { mutableStateOf(false) }
    var showGenreModal by remember { mutableStateOf(false) } // Declare showGenreModal state
    var badgeCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = "",
            onQueryChange = { /* Handle search query change */ },
            onSearch = { /* Handle search */ },
            active = false,
            onActiveChange = { /* Handle active state */ },
            content = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Badge
        BadgedBox(
            badge = { Badge { Text(badgeCount.toString()) } },
            modifier = Modifier.clickable { showMainModal = true }
        ) {
            Text("Filters", style = MaterialTheme.typography.headlineSmall)
        }

        // Main Modal
        if (showMainModal) {
            MainModal(
                onDismiss = { showMainModal = false },
                onItemClick = { item ->
                    if (item == "دسته بندی") {
                        // Open the genre modal
                        showGenreModal = true
                    }
                }
            )
        }

        // Genre Modal
        if (showGenreModal) {
            GenreModal(
                onDismiss = { showGenreModal = false },
                onCheckboxSelected = { badgeCount++ }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainModal(onDismiss: () -> Unit, onItemClick: (String) -> Unit) {
    val items = listOf(
        "دسته بندی",
        "فیلتر",
        "زائر",
        "کشور سازنده",
        "سال ساخت",
        "بازیگران",
        "مرتب سازی",
        "صوت",
        "زیزویس"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("فیلترها", style = MaterialTheme.typography.headlineSmall)
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(items) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreModal(onDismiss: () -> Unit, onCheckboxSelected: () -> Unit) {
    val genres = listOf("عاشقانه", "کمدی", "محساب", "صفحه و فانتزی", "اجتماعی", "موجح")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ژانر", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(genres) { genre ->
                    CheckboxRow(
                        label = genre,
                        onCheckboxSelected = onCheckboxSelected
                    )
                }
            }
        }
    }
}

@Composable
fun CheckboxRow(label: String, onCheckboxSelected: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isChecked = !isChecked
                if (isChecked) onCheckboxSelected()
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                if (it) onCheckboxSelected()
            },
            colors = CheckboxDefaults.colors(checkedColor = Color.Red)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
