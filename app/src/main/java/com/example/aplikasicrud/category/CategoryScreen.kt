package com.example.aplikasicrud.category


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aplikasicrud.viewmodel.AppViewModel
import com.example.aplikasicrud.data.Category

@Composable
fun CategoryScreen(viewModel: AppViewModel, onCategoryClick: (Category) -> Unit) {
    val categories by viewModel.categories.collectAsState()
    var editCategory by remember { mutableStateOf<Category?>(null) }
    var newName by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { viewModel.addCategory("Judul Catatan Baru") }) {
            Text("Tambah Judul Catatan")
        }
        Spacer(modifier = Modifier.height(16.dp))

        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onCategoryClick(category) },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = category.name, modifier = Modifier.weight(1f))
                Row {
                    Button(onClick = {
                        editCategory = category
                        newName = category.name
                    }) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { viewModel.deleteCategory(category) }) {
                        Text("Hapus")
                    }
                }
            }
        }
    }

    if (editCategory != null) {
        AlertDialog(
            onDismissRequest = { editCategory = null },
            title = { Text("Edit Judul Catatan") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Judul Catatan") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    editCategory?.let {
                        viewModel.updateCategory(it.copy(name = newName))
                        editCategory = null
                    }
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(onClick = { editCategory = null }) {
                    Text("Batal")
                }
            }
        )
    }
}