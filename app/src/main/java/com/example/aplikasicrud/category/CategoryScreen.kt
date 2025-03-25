package com.example.aplikasicrud.category


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aplikasicrud.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun CategoryScreen(viewModel: AppViewModel) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val items by viewModel.items.collectAsState()

    Column {
        Button(onClick = { viewModel.addCategory("Category ${viewModel.categories.value.size + 1}") }) {
            Text("Add Category")
        }
        LazyColumn {
            items(categories) { category ->
                Text(
                    category.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectCategory (category)
                        }
                        .padding(8.dp)
                )
            }
        }
        selectedCategory?.let { category ->
            Text("Items in ${category.name}")
            Button(onClick = { viewModel.addItem("Item ${viewModel.items.value.size + 1}", category.id) }) {
                Text("Add Item")
            }
            LazyColumn {
                items(viewModel.items.value) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(item.name, modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.deleteItem(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}
