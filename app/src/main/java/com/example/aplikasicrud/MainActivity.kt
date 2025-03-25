package com.example.aplikasicrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasicrud.viewmodel.AppViewModel
import com.example.aplikasicrud.viewmodel.AppViewModelFactory
import com.example.aplikasicrud.category.CategoryScreen
import com.example.aplikasicrud.ui.theme.AplikasiCRUDTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = AppViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AppViewModel::class.java]

        setContent {
            AplikasiCRUDTheme {
                CategoryScreen(viewModel)
            }
        }
    }
}
