package com.example.aplikasicrud.data

class AppRepository(private val appDao: AppDao) {
    suspend fun insertCategory(category: Category) = appDao.insertCategory(category)
    suspend fun getCategories(): List<Category> = appDao.getCategories()
    suspend fun updateCategory(category: Category) = appDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = appDao.deleteCategory(category)

    suspend fun insertItem(item: Item) = appDao.insertItem(item)
    suspend fun getItemsByCategory(categoryId: Int): List<Item> = appDao.getItemsByCategory(categoryId)
    suspend fun updateItem(item: Item) = appDao.updateItem(item)
    suspend fun deleteItem(item: Item) = appDao.deleteItem(item)
}
