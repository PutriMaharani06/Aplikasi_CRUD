package com.example.aplikasicrud.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Index
import com.example.aplikasicrud.data.Category
import com.example.aplikasicrud.data.Item


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
