package com.example.aplikasicrud.category

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase





@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "items",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["categoryId"])]
)
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val categoryId: Int
)

// DAO
@Dao
interface AppDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Insert
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Query("SELECT * FROM items WHERE categoryId = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Item>

    @Delete
    suspend fun deleteCategory(category: Category)

    @Delete
    suspend fun deleteItem(item: Item)
}

// Database
@Database(entities = [Category::class, Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


// Repository
class AppRepository(private val dao: AppDao) {
    suspend fun insertCategory(category: Category) = dao.insertCategory(category)
    suspend fun insertItem(item: Item) = dao.insertItem(item)
    suspend fun getCategories() = dao.getCategories()
    suspend fun getItemsByCategory(categoryId: Int) = dao.getItemsByCategory(categoryId)
    suspend fun deleteCategory(category: Category) = dao.deleteCategory(category)
    suspend fun deleteItem(item: Item) = dao.deleteItem(item)
}

// ViewModel
class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = AppRepository(db.appDao())

    var categories = mutableStateOf(listOf<Category>())
    var selectedCategory = mutableStateOf<Category?>(null)
    var items = mutableStateOf(listOf<Item>())

    init { loadCategories() }

    fun loadCategories() {
        viewModelScope.launch { categories.value = repository.getCategories() }
    }

    fun loadItems(categoryId: Int) {
        viewModelScope.launch { items.value = repository.getItemsByCategory(categoryId) }
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
}

// UI
@Composable
fun CategoryScreen(viewModel: AppViewModel) {
    val categories by remember { viewModel.categories }
    val selectedCategory by remember { viewModel.selectedCategory }
    val items by remember { viewModel.items }

    Column {
        Button(onClick = { viewModel.addCategory("Category ${viewModel.categories.value.size + 1}") }) {
            Text("Add Category")
        }
        LazyColumn {
            items(categories) { category ->
                Text(category.name, modifier = Modifier.fillMaxWidth().clickable {
                    viewModel.selectedCategory.value =category
                })
            }
        }

        selectedCategory?.let { category ->
            Text("Items in ${category.name}")
            Button (onClick = { viewModel.addItem("Item ${items.size + 1}", category.id) }) {
                Text("Add Item")
            }
            LazyColumn {
                items(items) { item ->
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
