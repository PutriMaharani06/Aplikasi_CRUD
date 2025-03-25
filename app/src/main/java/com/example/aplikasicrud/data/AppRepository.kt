package com.example.aplikasicrud.data

class AppRepository(private val dao: AppDao) {
    suspend fun insertCategory(category: Category) = dao.insertCategory(category)
    suspend fun insertItem(item: Item) = dao.insertItem(item)
    suspend fun getCategories() = dao.getCategories()
    suspend fun getItemsByCategory(categoryId: Int) = dao.getItemsByCategory(categoryId)
    suspend fun deleteCategory(category: Category) = dao.deleteCategory(category)
    suspend fun deleteItem(item: Item) = dao.deleteItem(item)
}
