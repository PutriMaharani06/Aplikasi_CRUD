package com.example.aplikasicrud.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasicrud.data.AppDatabase
import com.example.aplikasicrud.data.AppRepository
import com.example.aplikasicrud.data.Category
import com.example.aplikasicrud.data.Item
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = AppRepository(db.appDao())

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    init {
        try {
            loadCategories()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadCategories() {
        viewModelScope.launch { _categories.value = repository.getCategories() }
    }

    fun loadItems(categoryId: Int) {
        viewModelScope.launch { _items.value = repository.getItemsByCategory(categoryId) }
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            repository.insertCategory(Category(name = name))
            loadCategories()
        }
    }

    fun addItem(name: String, categoryId: Int) {
        viewModelScope.launch {
            repository.insertItem(Item(name = name, categoryId = categoryId))
            loadItems(categoryId)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            loadCategories()
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
            loadItems(item.categoryId)
        }
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
        loadItems(category.id)
    }
}
