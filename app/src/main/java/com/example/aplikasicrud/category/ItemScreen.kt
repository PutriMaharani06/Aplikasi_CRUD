package com.example.aplikasicrud.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.dp
import com.example.aplikasicrud.data.Category
import com.example.aplikasicrud.data.Item
import com.example.aplikasicrud.viewmodel.AppViewModel


@Composable
fun ItemScreen(viewModel: AppViewModel, category: Category, onBack: () -> Unit) {
    val items by viewModel.items.collectAsState()
    var editItem by remember { mutableStateOf<Item?>(null) }
    var newName by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { viewModel.addItem("Catatan Baru", category.id) }) {
            Text("Tambah Catatan")
        }
        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.name, modifier = Modifier.weight(1f))
                Row {
                    Button(onClick = {
                        editItem = item
                        newName = item.name
                    }) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { viewModel.deleteItem(item) }) {
                        Text("Hapus")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Kembali")
        }
    }

    if (editItem != null) {
        AlertDialog(
            onDismissRequest = { editItem = null },
            title = { Text("Edit Catatan") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Nama Catatan") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    editItem?.let {
                        viewModel.updateItem(it.copy(name = newName))
                        editItem = null
                    }
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(onClick = { editItem = null }) {
                    Text("Batal")
                }
            }
        )
    }
}