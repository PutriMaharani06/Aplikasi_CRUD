package com.example.aplikasicrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasicrud.viewmodel.AppViewModel
import com.example.aplikasicrud.category.CategoryScreen
import com.example.aplikasicrud.category.ItemScreen
import com.example.aplikasicrud.data.Category

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppViewModel = viewModel()
            var selectedCategory by remember { mutableStateOf<Category?>(null) }

            if (selectedCategory == null) {
                CategoryScreen(viewModel) { category ->
                    selectedCategory = category
                    viewModel.loadItems(category.id)
                }
            } else {
                ItemScreen(viewModel, selectedCategory!!) { selectedCategory = null }
            }
        }
    }
}

