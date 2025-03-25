package com.example.aplikasicrud.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy



@Dao
interface AppDao {

    @Insert
    suspend fun insertItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Category>

    @Query("SELECT * FROM items WHERE categoryId = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Item>

    @Delete
    suspend fun deleteCategory(category: Category)

    @Delete
    suspend fun deleteItem(item: Item)
}
