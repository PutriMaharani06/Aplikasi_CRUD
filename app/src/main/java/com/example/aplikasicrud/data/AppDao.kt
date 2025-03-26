package com.example.aplikasicrud.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update


@Dao
interface AppDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Category>

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Insert
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM items WHERE categoryId = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Item>

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}
